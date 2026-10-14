package com.oasis.reservation.ui;

import com.oasis.reservation.api.RailApiService;
import com.oasis.reservation.model.Reservation;
import com.oasis.reservation.model.Train;
import com.oasis.reservation.service.ReservationService;
import com.oasis.reservation.service.TrainService;
import com.oasis.reservation.util.Theme;
import com.oasis.reservation.util.ValidationUtil;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/** Polished reservation form. The train selector intentionally opens with the available catalogue. */
public class ReservationPanel extends JPanel {
    private final JTextField passenger = new JTextField();
    private final JComboBox<Train> trainSelector = new JComboBox<>();
    private final JTextField trainName = new JTextField();
    private final JComboBox<String> classType = new JComboBox<>(new String[]{"First AC", "Second AC", "Third AC", "Sleeper", "General"});
    private final JTextField date = new JTextField();
    private final JTextField source = new JTextField();
    private final JTextField destination = new JTextField();
    private final TrainService trains = new TrainService();
    private final ReservationService reservations = new ReservationService();
    private final RailApiService api = new RailApiService();
    private Train selected;

    public ReservationPanel() {
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(20, 24, 24, 24));
        build();
    }

    private void build() {
        JPanel outer = new JPanel(new BorderLayout(0, 16));
        outer.setOpaque(false);

        JPanel title = new JPanel();
        title.setOpaque(false);
        title.setLayout(new BoxLayout(title, BoxLayout.Y_AXIS));
        title.add(Theme.heading("Book your journey", 27));
        title.add(Box.createVerticalStrut(5));
        title.add(Theme.body("Choose a train, enter passenger details, and create a secure PNR.") );
        outer.add(title, BorderLayout.NORTH);

        JPanel card = Theme.card();
        card.setLayout(new BorderLayout(0, 18));

        JPanel routeBanner = new JPanel(new BorderLayout(12, 0));
        routeBanner.setBackground(Theme.NAVY);
        routeBanner.setBorder(BorderFactory.createEmptyBorder(14, 18, 14, 18));
        JLabel routeTitle = Theme.heading("JOURNEY", 12);
        routeTitle.setForeground(Color.WHITE);
        JLabel route = new JLabel("Select a train to see its route");
        route.setForeground(Color.WHITE);
        route.setFont(Theme.font(16, Font.BOLD));
        routeBanner.add(routeTitle, BorderLayout.WEST);
        routeBanner.add(route, BorderLayout.CENTER);
        card.add(routeBanner, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        add(form, 0, 0, "Passenger name", passenger);
        add(form, 1, 0, "Train number • click to view available trains", trainSelector);
        add(form, 0, 1, "Train name", trainName);
        add(form, 1, 1, "Class type", classType);
        add(form, 0, 2, "Journey date (YYYY-MM-DD)", date);
        add(form, 1, 2, "Source station", source);
        add(form, 0, 3, "Destination station", destination);

        trainName.setEditable(false);
        source.setEditable(false);
        destination.setEditable(false);
        for (JTextField f : new JTextField[]{passenger, trainName, date, source, destination}) Theme.styleField(f);
        date.setText(LocalDate.now().plusDays(1).toString());
        date.putClientProperty("JTextField.placeholderText", "YYYY-MM-DD");
        Theme.styleCombo(trainSelector);
        Theme.styleCombo(classType);

        trainSelector.addActionListener(e -> selectTrain());
        installAutocomplete(source);
        installAutocomplete(destination);

        card.add(form, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout(12, 0));
        bottom.setOpaque(false);
        JLabel hint = Theme.body(api.enabled()
                ? "● Live railway API connected"
                : "● Offline catalogue ready • API can be enabled from config/api.properties");
        hint.setForeground(api.enabled() ? Theme.SUCCESS : Theme.SLATE);
        bottom.add(hint, BorderLayout.WEST);
        JButton book = Theme.primaryButton("Confirm & Book Ticket");
        book.addActionListener(e -> book());
        bottom.add(book, BorderLayout.EAST);
        card.add(bottom, BorderLayout.SOUTH);

        outer.add(card, BorderLayout.CENTER);
        setLayout(new BorderLayout());
        add(outer);
        loadTrains();
    }

    private void loadTrains() {
        trainSelector.removeAllItems();
        trainSelector.addItem(null);
        try {
            for (Train t : trains.findAll()) trainSelector.addItem(t);
        } catch (Exception ignored) {
            // Database initialization already seeds the catalogue; keep the selector usable if a transient DB error occurs.
        }
        trainSelector.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                           boolean selected, boolean focus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, selected, focus);
                label.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
                if (value == null) {
                    label.setText("Select a train number…");
                    label.setForeground(Theme.SLATE);
                } else {
                    Train t = (Train) value;
                    label.setText(String.format("%05d   •   %s   (%s → %s)",
                            t.trainNumber(), t.trainName(), t.source(), t.destination()));
                    label.setForeground(Theme.NAVY);
                }
                return label;
            }
        });
        trainSelector.setSelectedIndex(0);
    }

    private void add(JPanel p, int col, int row, String label, JComponent field) {
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = col;
        g.gridy = row;
        g.weightx = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(7, 8, 7, 8);
        JPanel x = new JPanel(new BorderLayout(0, 6));
        x.setOpaque(false);
        JLabel l = Theme.body(label);
        x.add(l, BorderLayout.NORTH);
        x.add(field, BorderLayout.CENTER);
        p.add(x, g);
    }

    private void selectTrain() {
        Object value = trainSelector.getSelectedItem();
        if (!(value instanceof Train t)) {
            selected = null;
            trainName.setText("");
            source.setText("");
            destination.setText("");
            return;
        }
        selected = t;
        trainName.setText(t.trainName());
        source.setText(t.source());
        destination.setText(t.destination());
    }

    private void installAutocomplete(JTextField f) {
        // Station suggestions are only requested when the live API is enabled.
        f.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                String q = f.getText().trim();
                if (q.length() < 2 || !api.enabled()) return;
                new SwingWorker<List<String>, Void>() {
                    List<String> result = List.of();
                    protected List<String> doInBackground() throws Exception {
                        result = api.stationSuggestions(q);
                        return result;
                    }
                    protected void done() {
                        try {
                            if (f.getText().trim().length() < 2) return;
                            JPopupMenu pop = new JPopupMenu();
                            for (String s : result) {
                                JMenuItem it = new JMenuItem(s);
                                it.addActionListener(ev -> {
                                    int open = s.lastIndexOf('(');
                                    int close = s.lastIndexOf(')');
                                    f.setText(open >= 0 && close > open ? s.substring(open + 1, close) : s);
                                });
                                pop.add(it);
                            }
                            if (pop.getComponentCount() > 0) pop.show(f, 0, f.getHeight());
                        } catch (Exception ignored) { }
                    }
                }.execute();
            }
        });
    }

    private void book() {
        try {
            if (selected == null) throw new IllegalArgumentException("Please select a train number from the available train list.");
            Reservation r = reservations.book(passenger.getText(), selected,
                    (String) classType.getSelectedItem(), ValidationUtil.parseFutureDate(date.getText()));
            JOptionPane.showMessageDialog(this,
                    "PNR  " + r.pnr() + "\n\nPassenger  " + r.passengerName() +
                            "\nTrain  " + r.trainNumber() + " • " + r.trainName() +
                            "\nClass  " + r.classType() + "\nJourney  " + r.journeyDate() +
                            "\n\n" + r.source() + "  →  " + r.destination(),
                    "Booking confirmed", JOptionPane.INFORMATION_MESSAGE);
            clear();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Unable to book ticket", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clear() {
        passenger.setText("");
        trainSelector.setSelectedIndex(0);
        trainName.setText("");
        date.setText(LocalDate.now().plusDays(1).toString());
        source.setText("");
        destination.setText("");
        selected = null;
        classType.setSelectedIndex(0);
    }
}
