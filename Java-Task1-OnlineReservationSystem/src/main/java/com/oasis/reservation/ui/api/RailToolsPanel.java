package com.oasis.reservation.ui.api;

import com.google.gson.*;
import com.oasis.reservation.api.RailApiService;
import com.oasis.reservation.util.Theme;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.util.function.Supplier;

/**
 * Railway information workspace.
 * Each tool has its own compact form and result area so Swing BorderLayout
 * constraints cannot accidentally hide the controls.
 */
public class RailToolsPanel extends JPanel {
    private final RailApiService api = new RailApiService();
    private final JTabbedPane tabs = new JTabbedPane();

    public RailToolsPanel() {
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(18, 24, 24, 24));
        setLayout(new BorderLayout(0, 12));

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(Theme.heading("Railway information", 27));
        header.add(Box.createVerticalStrut(4));
        JLabel status = Theme.body(api.enabled()
                ? "● Live Indian Rail API connected"
                : "● Demo mode • add your API key in config/api.properties for live data");
        status.setForeground(api.enabled() ? Theme.SUCCESS : Theme.SLATE);
        header.add(status);
        add(header, BorderLayout.NORTH);

        tabs.setOpaque(false);
        tabs.addTab("Train Details", trainInfo());
        tabs.addTab("Schedule", schedule());
        tabs.addTab("Fare", fare());
        tabs.addTab("Live Status", live());
        tabs.addTab("Station Tools", station());
        add(tabs, BorderLayout.CENTER);
    }

    /**
     * Creates a card with a title and a dedicated body area.
     * The body is returned separately so the header is never overwritten.
     */
    private JPanel base(String title, String hint, JPanel body) {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setOpaque(false);

        JPanel card = Theme.card();
        card.setLayout(new BorderLayout(0, 14));

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(Theme.heading(title, 20));
        header.add(Box.createVerticalStrut(4));
        header.add(Theme.body(hint));
        card.add(header, BorderLayout.NORTH);

        card.add(body, BorderLayout.CENTER);
        outer.add(card, BorderLayout.CENTER);
        return outer;
    }

    private JPanel bodyWithResult(JPanel form, JTextArea output) {
        JPanel body = new JPanel(new BorderLayout(0, 12));
        body.setOpaque(false);
        body.add(form, BorderLayout.NORTH);
        JScrollPane scroll = new JScrollPane(output);
        scroll.setBorder(BorderFactory.createLineBorder(Theme.HAIRLINE));
        body.add(scroll, BorderLayout.CENTER);
        return body;
    }

    private JPanel trainInfo() {
        JTextField number = new JTextField();
        Theme.styleField(number);
        number.putClientProperty("JTextField.placeholderText", "e.g. 12723");

        JTextArea out = output();
        JButton fetch = Theme.primaryButton("Fetch Train");
        fetch.addActionListener(e -> run(fetch, out, () -> api.trainInformation(number.getText().trim())));

        JPanel form = inlineForm(new JLabel("Train number"), number, fetch);
        return base("Train details",
                "Enter a train number to retrieve its official name, source and destination.",
                bodyWithResult(form, out));
    }

    private JPanel schedule() {
        JTextField number = new JTextField();
        Theme.styleField(number);
        number.putClientProperty("JTextField.placeholderText", "e.g. 12723");

        JTextArea out = output();
        JButton load = Theme.primaryButton("Load Schedule");
        load.addActionListener(e -> run(load, out, () -> api.trainSchedule(number.getText().trim())));

        JPanel form = inlineForm(new JLabel("Train number"), number, load);
        return base("Train schedule",
                "View the complete route, stations, arrival/departure times and distance.",
                bodyWithResult(form, out));
    }

    private JPanel fare() {
        JTextField number = new JTextField();
        JTextField from = new JTextField();
        JTextField to = new JTextField();
        for (JTextField f : new JTextField[]{number, from, to}) Theme.styleField(f);

        number.putClientProperty("JTextField.placeholderText", "Train no.");
        from.putClientProperty("JTextField.placeholderText", "From code");
        to.putClientProperty("JTextField.placeholderText", "To code");

        JComboBox<String> quota = new JComboBox<>(new String[]{"GN", "CK"});
        Theme.styleCombo(quota);

        JTextArea out = output();
        JButton find = Theme.primaryButton("Find Fare");
        find.addActionListener(e -> run(find, out, () ->
                api.trainFare(number.getText().trim(),
                        from.getText().trim().toUpperCase(),
                        to.getText().trim().toUpperCase(),
                        quota.getSelectedItem().toString())));

        JPanel fields = new JPanel(new GridLayout(1, 4, 8, 0));
        fields.setOpaque(false);
        fields.add(number);
        fields.add(from);
        fields.add(to);
        fields.add(quota);

        JPanel form = new JPanel(new BorderLayout(10, 0));
        form.setOpaque(false);
        form.add(fields, BorderLayout.CENTER);
        form.add(find, BorderLayout.EAST);

        return base("Fare estimator",
                "Compare railway fares for a train, route and quota.",
                bodyWithResult(form, out));
    }

    private JPanel live() {
        JTextField number = new JTextField();
        JTextField date = new JTextField(java.time.LocalDate.now().format(
                java.time.format.DateTimeFormatter.BASIC_ISO_DATE));
        Theme.styleField(number);
        Theme.styleField(date);
        number.putClientProperty("JTextField.placeholderText", "Train number");
        date.putClientProperty("JTextField.placeholderText", "YYYYMMDD");

        JTextArea out = output();
        JButton check = Theme.primaryButton("Check Status");
        check.addActionListener(e -> run(check, out, () ->
                api.liveTrainStatus(number.getText().trim(), date.getText().trim())));

        JPanel fields = new JPanel(new GridLayout(1, 2, 8, 0));
        fields.setOpaque(false);
        fields.add(number);
        fields.add(date);

        JPanel form = new JPanel(new BorderLayout(10, 0));
        form.setOpaque(false);
        form.add(fields, BorderLayout.CENTER);
        form.add(check, BorderLayout.EAST);

        return base("Live train status",
                "Check the current position, station and delay information for a journey date.",
                bodyWithResult(form, out));
    }

    private JPanel station() {
        JTextField query = new JTextField();
        Theme.styleField(query);
        query.putClientProperty("JTextField.placeholderText", "Station name or code");

        JTextArea out = output();

        JButton autocomplete = Theme.primaryButton("Search");
        autocomplete.addActionListener(e -> run(autocomplete, out,
                () -> api.autocompleteStation(query.getText().trim())));

        JButton nameToCode = Theme.secondaryButton("Name → Code");
        nameToCode.addActionListener(e -> run(nameToCode, out,
                () -> api.stationNameToCode(query.getText().trim())));

        JButton codeToName = Theme.secondaryButton("Code → Name");
        codeToName.addActionListener(e -> run(codeToName, out,
                () -> api.stationCodeToName(query.getText().trim().toUpperCase())));

        JButton map = Theme.secondaryButton("Open Map");
        map.addActionListener(e -> openMap(query.getText().trim().toUpperCase()));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 7, 0));
        buttons.setOpaque(false);
        buttons.add(autocomplete);
        buttons.add(nameToCode);
        buttons.add(codeToName);
        buttons.add(map);

        JPanel form = new JPanel(new BorderLayout(10, 0));
        form.setOpaque(false);
        form.add(query, BorderLayout.CENTER);
        form.add(buttons, BorderLayout.EAST);

        return base("Station tools",
                "Search stations, convert station names/codes and open a station location.",
                bodyWithResult(form, out));
    }

    private JPanel inlineForm(JLabel label, JTextField field, JButton button) {
        label.setForeground(Theme.SLATE);
        label.setFont(Theme.font(13, Font.BOLD));

        JPanel left = new JPanel(new BorderLayout(0, 5));
        left.setOpaque(false);
        left.add(label, BorderLayout.NORTH);
        left.add(field, BorderLayout.CENTER);

        JPanel form = new JPanel(new BorderLayout(10, 0));
        form.setOpaque(false);
        form.add(left, BorderLayout.CENTER);
        form.add(button, BorderLayout.EAST);
        return form;
    }

    private JTextArea output() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        area.setForeground(Theme.NAVY);
        area.setBackground(Theme.PEBBLE);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        area.setText("Ready.\n\nChoose an option above to load railway information.");
        return area;
    }

    private void openMap(String stationCode) {
        if (stationCode.isBlank()) {
            JOptionPane.showMessageDialog(this,
                    "Enter a station code first, for example HYB or NDLS.",
                    "Station map", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            JsonObject o = api.stationLocation(stationCode);
            String url = null;
            if (o.has("URL") && !o.get("URL").isJsonNull()) url = o.get("URL").getAsString();
            if (url == null && o.has("MapURL") && !o.get("MapURL").isJsonNull()) url = o.get("MapURL").getAsString();
            if (url == null) throw new IllegalStateException("The API did not return a map URL for " + stationCode + ".");
            Desktop.getDesktop().browse(URI.create(url));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    api.enabled() ? ex.getMessage() : "Configure your Indian Rail API key to use station maps.",
                    "Station map", JOptionPane.WARNING_MESSAGE);
        }
    }

    private interface Call {
        JsonObject go() throws Exception;
    }

    private void run(JButton button, JTextArea out, Call call) {
        button.setEnabled(false);
        out.setText("Loading…\n\nConnecting to Indian Rail API.");
        new SwingWorker<JsonObject, Void>() {
            protected JsonObject doInBackground() throws Exception {
                return call.go();
            }

            protected void done() {
                button.setEnabled(true);
                try {
                    out.setText(new GsonBuilder().setPrettyPrinting().create().toJson(get()));
                    out.setCaretPosition(0);
                } catch (Exception e) {
                    out.setText("Unable to load railway information.\n\n" +
                            (api.enabled()
                                    ? e.getMessage()
                                    : "API is not configured. Add your key to config/api.properties."));
                }
            }
        }.execute();
    }
}
