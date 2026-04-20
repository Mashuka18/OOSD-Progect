package User;

import javax.swing.*;
import java.awt.*;

/**
 * Registration screen for new users.
 * Handles input validation and user creation process.
 */
public class RegisterUserGUI extends JFrame {

    // 🔷 INPUT FIELDS
    private JTextField nameField, emailField, phoneField;
    private JPasswordField passwordField;

    // 🎨 UI THEME COLORS (consistent application design system)
    private final Color BG_COLOR = new Color(235, 236, 240);
    private final Color CARD_COLOR = new Color(28, 28, 32);
    private final Color PRIMARY = new Color(212, 175, 55);
    private final Color TEXT_MAIN = Color.WHITE;

    /**
     * Constructor - builds the registration UI
     */
    public RegisterUserGUI() {

        setTitle("Register");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set background theme
        getContentPane().setBackground(BG_COLOR);

        // 🔷 HEADER SECTION
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_COLOR);

        JLabel title = new JLabel("Create Account");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(PRIMARY);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        header.add(title, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        // 🔷 MAIN CENTER PANEL
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(BG_COLOR);
        add(center, BorderLayout.CENTER);

        // 🔷 REGISTRATION CARD
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        card.setPreferredSize(new Dimension(400, 400));

        // Create input fields
        nameField = createField("Full Name");
        emailField = createField("Email");
        phoneField = createField("Phone");
        passwordField = new JPasswordField();

        stylePassword(passwordField);

        // 🔷 ACTION BUTTONS
        JButton registerBtn = new JButton("Register");
        registerBtn.setBackground(PRIMARY);
        registerBtn.setForeground(Color.BLACK);
        registerBtn.setFocusPainted(false);

        JButton loginBtn = new JButton("Back to Login");
        loginBtn.setBackground(new Color(60, 60, 60));
        loginBtn.setForeground(TEXT_MAIN);
        loginBtn.setFocusPainted(false);

        // Button actions
        registerBtn.addActionListener(e -> registerUser());
        loginBtn.addActionListener(e -> {
            new LoginUserGUI().setVisible(true);
            dispose();
        });

        // Add components with spacing
        card.add(nameField);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(emailField);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(phoneField);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(passwordField);
        card.add(Box.createRigidArea(new Dimension(0, 20)));
        card.add(registerBtn);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(loginBtn);

        center.add(card);
    }

    /**
     * Creates a styled text field with titled border
     */
    private JTextField createField(String title) {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setBorder(BorderFactory.createTitledBorder(title));
        return field;
    }

    /**
     * Applies styling to password field
     */
    private void stylePassword(JPasswordField field) {
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setBorder(BorderFactory.createTitledBorder("Password"));
    }

    /**
     * Handles user registration logic:
     * validation + database insertion
     */
    private void registerUser() {

        // Reset UI state before validation
        resetColors();

        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String password = new String(passwordField.getPassword());

        // Required field validation
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            CustomDialog.showError(this, "Fill all required fields!");
            return;
        }

        // Name validation (letters only)
        if (!name.matches("[a-zA-Z\\s]+")) {
            CustomDialog.showError(this, "Name must contain only letters!");
            return;
        }

        // Email validation
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            CustomDialog.showError(this, "Enter valid email!");
            return;
        }

        // Optional phone validation (Irish format)
        if (!phone.isEmpty() && !phone.matches("(\\+353|0)8\\d{8}")) {
            CustomDialog.showError(this, "Invalid phone!");
            return;
        }

        // Password strength check
        if (password.length() < 6 || !password.matches(".*\\d.*")) {
            CustomDialog.showError(this, "Weak password!");
            return;
        }

        // Insert into database
        UserDAO dao = new UserDAO();

        if (dao.insertUser(name, email, phone, "", password)) {
            CustomDialog.showInfo(this, "Registration successful!");
            new LoginUserGUI().setVisible(true);
            dispose();
        } else {
            CustomDialog.showError(this, "Error registering user");
        }
    }

    /**
     * Resets input field colors (UI cleanup before validation)
     */
    private void resetColors() {
        nameField.setBackground(Color.WHITE);
        emailField.setBackground(Color.WHITE);
        phoneField.setBackground(Color.WHITE);
        passwordField.setBackground(Color.WHITE);
    }
}