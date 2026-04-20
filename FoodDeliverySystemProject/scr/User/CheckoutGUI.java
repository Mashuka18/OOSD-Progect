package User;

import javax.swing.*;
import java.awt.*;

/**
 * GUI class for checkout process.
 * Allows user to enter or confirm delivery address before payment.
 */
public class CheckoutGUI extends JFrame {

    private JTextField eircodeField, streetField, houseField;

    // UI theme colors
    private final Color BG = new Color(235, 236, 240);
    private final Color CARD = new Color(28, 28, 32);
    private final Color PRIMARY = new Color(212, 175, 55);
    private final Color TEXT = Color.WHITE;

    /**
     * Constructs the Checkout GUI window.
     */
    public CheckoutGUI() {

        setTitle("Checkout");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set background theme
        getContentPane().setBackground(BG);

        // Build UI
        initUI();

        // Check saved address after UI is ready
        SwingUtilities.invokeLater(this::checkSavedAddress);

        setVisible(true);
    }

    /**
     * Initializes all UI components.
     */
    private void initUI() {

        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG);

        JLabel title = new JLabel("Checkout", SwingConstants.CENTER);
        title.setForeground(PRIMARY);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        header.add(title, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        // SIDEBAR navigation
        add(new Sidebar(this), BorderLayout.WEST);

        // CENTER panel
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(BG);

        // FORM CARD
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        card.setPreferredSize(new Dimension(400, 300));

        // Title
        JLabel formTitle = new JLabel("Delivery Address");
        formTitle.setForeground(TEXT);
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));

        // Input fields
        eircodeField = createField("Eircode");
        streetField = createField("Street");
        houseField = createField("House Number");

        // Confirm button
        JButton confirm = new JButton("Confirm");
        stylePrimaryButton(confirm);

        confirm.addActionListener(e -> saveAddress());

        // Layout
        card.add(formTitle);
        card.add(Box.createVerticalStrut(15));
        card.add(eircodeField);
        card.add(Box.createVerticalStrut(10));
        card.add(streetField);
        card.add(Box.createVerticalStrut(10));
        card.add(houseField);
        card.add(Box.createVerticalStrut(20));
        card.add(confirm);

        center.add(card);
        add(center, BorderLayout.CENTER);
    }

    /**
     * Validation of user input fields.
     */
    private boolean validateInput(String eircode, String street, String house) {

        // Empty check
        if (eircode.isEmpty() || street.isEmpty() || house.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill all fields!");
            return false;
        }

        // Street validation
        if (street.length() < 3) {
            JOptionPane.showMessageDialog(this, "Street name is too short!");
            return false;
        }

        // House validation
        if (!house.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "House number must contain only digits!");
            return false;
        }

        // Eircode format validation (basic 7-char check)
        String normalized = eircode.replaceAll("\\s+", "").toUpperCase();

        if (!normalized.matches("[A-Z0-9]{7}")) {
            JOptionPane.showMessageDialog(this, "Invalid Eircode format (example: D02 X285)");
            return false;
        }

        return true;
    }

    /**
     * Saves user address to database and proceeds to payment.
     */
    private void saveAddress() {

        String eircode = eircodeField.getText().trim();
        String street = streetField.getText().trim();
        String house = houseField.getText().trim();

        // Validation check
        if (!validateInput(eircode, street, house)) {
            return;
        }

        UserDAO dao = new UserDAO();

        boolean ok = dao.updateUserAddress(
                Session.userId,
                eircode,
                street,
                house
        );

        if (ok) {
            JOptionPane.showMessageDialog(this, "Address saved!");
            dispose();
            new PaymentGUI().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Error saving address");
        }
    }

    /**
     * Checks if user already has a saved address
     * and prompts to reuse it.
     */
    private void checkSavedAddress() {

        UserDAO dao = new UserDAO();
        String address = dao.getUserAddress(Session.userId);

        if (address == null || address.trim().isEmpty()) return;

        JDialog dialog = new JDialog(this, "Saved Address", true);
        dialog.setSize(350, 220);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setBackground(new Color(28, 28, 32));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Use saved address?");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 15));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel addr = new JLabel("<html><center>" + address + "</center></html>");
        addr.setForeground(new Color(170, 170, 170));
        addr.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton useBtn = new JButton("Use this");
        JButton editBtn = new JButton("Edit");

        useBtn.setBackground(PRIMARY);
        useBtn.setForeground(Color.BLACK);
        useBtn.setFocusPainted(false);

        editBtn.setBackground(new Color(50, 50, 55));
        editBtn.setForeground(TEXT);
        editBtn.setFocusPainted(false);

        useBtn.addActionListener(e -> {
            dialog.dispose();
            dispose();
            new PaymentGUI().setVisible(true);
        });

        editBtn.addActionListener(e -> dialog.dispose());

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(28, 28, 32));
        btnPanel.add(useBtn);
        btnPanel.add(editBtn);

        panel.add(title);
        panel.add(Box.createVerticalStrut(15));
        panel.add(addr);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnPanel);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    /**
     * Creates a styled input field with title border.
     */
    private JTextField createField(String title) {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setBorder(BorderFactory.createTitledBorder(title));
        return field;
    }

    /**
     * Styles primary action button.
     */
    private void stylePrimaryButton(JButton btn) {
        btn.setBackground(PRIMARY);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
    }
}