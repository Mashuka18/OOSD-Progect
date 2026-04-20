package User;

import javax.swing.*;
import java.awt.*;

/**
 * Login screen for user authentication.
 * Allows users to log in using email and password.
 * On success, redirects to Dashboard.
 */
public class LoginUserGUI extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    // 🎨 UI THEME COLORS
    private final Color BG_COLOR = new Color(235, 236, 240);
    private final Color CARD_COLOR = new Color(28, 28, 32);
    private final Color PRIMARY = new Color(212, 175, 55);
    private final Color TEXT_SECOND = new Color(170, 170, 170);

    /**
     * Constructor - builds login UI.
     */
    public LoginUserGUI() {

        setTitle("Login");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(BG_COLOR);

        initUI();

        setVisible(true);
    }

    /**
     * Initializes all UI components.
     */
    private void initUI() {

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(BG_COLOR);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        card.setPreferredSize(new Dimension(350, 350));

        // Title
        JLabel title = new JLabel("Welcome Back");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(PRIMARY);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        // Input fields
        emailField = createField("Email");
        passwordField = new JPasswordField();
        styleField(passwordField, "Password");

        // Buttons
        JButton loginBtn = new JButton("Login");
        styleButton(loginBtn);

        JButton registerBtn = new JButton("Create account");
        registerBtn.setForeground(TEXT_SECOND);
        registerBtn.setContentAreaFilled(false);
        registerBtn.setBorderPainted(false);
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Actions
        loginBtn.addActionListener(e -> loginUser());

        registerBtn.addActionListener(e -> {
            new RegisterUserGUI().setVisible(true);
            dispose();
        });

        // Add components
        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0, 20)));
        card.add(emailField);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(passwordField);
        card.add(Box.createRigidArea(new Dimension(0, 20)));
        card.add(loginBtn);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(registerBtn);

        center.add(card);
        add(center, BorderLayout.CENTER);
    }

    /**
     * Creates a text field with a title border.
     */
    private JTextField createField(String title) {
        JTextField field = new JTextField();
        styleField(field, title);
        return field;
    }

    /**
     * Styles input fields.
     */
    private void styleField(JTextField field, String title) {
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setBorder(BorderFactory.createTitledBorder(title));
        field.setBackground(Color.WHITE);
    }

    /**
     * Styles buttons.
     */
    private void styleButton(JButton btn) {
        btn.setBackground(PRIMARY);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    /**
     * Handles user login validation.
     */
    private void loginUser() {

        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        // validation
        if (email.isEmpty() || password.isEmpty()) {
            CustomDialog.showError(this, "Fill all fields!");
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            CustomDialog.showError(this, "Invalid email!");
            return;
        }

        UserDAO dao = new UserDAO();

        // check credentials
        if (dao.loginUser(email, password)) {

            Session.userEmail = email;
            Session.userId = dao.getUserIdByEmail(email);

            CustomDialog.showInfo(this, "Login successful!");

            new DashboardGUI().setVisible(true);
            dispose();

        } else {
            CustomDialog.showError(this, "Invalid credentials!");
        }
    }

    /**
     * Entry point for testing login screen.
     */
    public static void main(String[] args) {
        new LoginUserGUI();
    }
}