package com.oasis.reservation.ui;

import com.oasis.reservation.model.Reservation;
import com.oasis.reservation.service.ReservationService;
import com.oasis.reservation.util.Theme;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/** Displays all currently saved reservations from the SQLite database. */
public class SavedBookingsPanel extends JPanel {
    private final ReservationService service = new ReservationService();
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"PNR", "Passenger", "Train", "Class", "Journey Date", "From", "To"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable table = new JTable(model);
    private final JLabel status = Theme.body("Bookings are stored in the local SQLite database.");

    public SavedBookingsPanel() {
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(10, 18, 24, 18));
        build();
        refresh();
    }

    private void build() {
        JPanel card = Theme.card();
        card.setLayout(new BorderLayout(0, 16));

        JPanel title = new JPanel(new BorderLayout(10, 4));
        title.setOpaque(false);
        JPanel text = new JPanel(new GridLayout(2, 1, 0, 4));
        text.setOpaque(false);
        text.add(Theme.heading("Saved bookings", 28));
        text.add(Theme.body("View reservations currently saved in the database."));
        title.add(text, BorderLayout.WEST);

        JButton refresh = Theme.primaryButton("Refresh");
        refresh.addActionListener(e -> refresh());
        title.add(refresh, BorderLayout.EAST);
        card.add(title, BorderLayout.NORTH);

        table.setRowHeight(32);
        table.setFont(Theme.font(13, Font.PLAIN));
        table.setForeground(Theme.NAVY);
        table.setGridColor(Theme.HAIRLINE);
        table.setShowVerticalLines(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setFont(Theme.font(13, Font.BOLD));
        table.getTableHeader().setForeground(Theme.NAVY);
        table.getTableHeader().setBackground(Theme.PEBBLE);
        table.getTableHeader().setPreferredSize(new Dimension(0, 36));
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.LEFT);
        renderer.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
        table.setDefaultRenderer(Object.class, renderer);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(Theme.HAIRLINE));
        card.add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        bottom.add(status, BorderLayout.WEST);
        card.add(bottom, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(card, BorderLayout.CENTER);
    }

    public void refresh() {
        SwingUtilities.invokeLater(() -> {
            model.setRowCount(0);
            try {
                List<Reservation> reservations = service.findAll();
                for (Reservation r : reservations) {
                    model.addRow(new Object[]{r.pnr(), r.passengerName(),
                            r.trainNumber() + " - " + r.trainName(), r.classType(),
                            r.journeyDate(), r.source(), r.destination()});
                }
                status.setText(reservations.size() + (reservations.size() == 1
                        ? " saved booking" : " saved bookings") + " • Stored in SQLite");
            } catch (Exception e) {
                status.setText("Could not load saved bookings.");
                JOptionPane.showMessageDialog(this, e.getMessage(),
                        "Unable to load bookings", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
