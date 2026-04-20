package User;

import javax.swing.*;
import java.awt.*;

public class PersonalDetailsGUI extends JFrame {

    private JTextField nameField, emailField, phoneField;

    // 🎨 GLOBAL UI THEME COLORS (consistent app styling)
    private final Color BG_COLOR = new Color(235, 236, 240);
    private final Color CARD_COLOR = new Color(28, 28, 32);
    private final Color PRIMARY = new Color(212, 175, 55);

    /**
     * Constructor - builds the Personal Details UI screen
     * Displays user information and allows update or delete actions
     */
    public PersonalDetailsGUI() {

        setTitle("Personal Details");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set main background color
        getContentPane().setBackground(BG_COLOR);

        // 🔷 HEADER SECTION (top bar title)
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_COLOR);

        JLabel title = new JLabel("Your Account");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(PRIMARY);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        header.add(title, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        // 🔷 SIDEBAR (navigation menu)
        add(new Sidebar(this), BorderLayout.WEST);

        // 🔷 MAIN CONTENT AREA
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(BG_COLOR);
        add(mainPanel, BorderLayout.CENTER);

        // 🔷 LOAD USER DATA FROM DATABASE
        UserDAO dao = new UserDAO();
        String[] user = dao.getUserDetails(Session.userId);

        // 🔷 PROFILE CARD CONTAINER
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        card.setPreferredSize(new Dimension(400, 350));

        // Create input fields with pre-filled user data
        nameField = createField("Name", user[0]);
        emailField = createField("Email", user[1]);
        phoneField = createField("Phone", user[2]);

        // 🔷 ACTION BUTTONS
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete Account");

        // Apply button styling
        styleBtn(updateBtn);
        styleDangerBtn(deleteBtn);

        // Attach actions
        updateBtn.addActionListener(e -> updateUser());
        deleteBtn.addActionListener(e -> deleteUser());

        // Add components to card with spacing
        card.add(nameField);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(emailField);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(phoneField);
        card.add(Box.createRigidArea(new Dimension(0, 20)));
        card.add(updateBtn);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(deleteBtn);

        mainPanel.add(card);
    }

    /**
     * Creates a styled text field with title border and default value
     */
    private JTextField createField(String title, String value) {
        JTextField field = new JTextField(value);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setBorder(BorderFactory.createTitledBorder(title));
        return field;
    }

    /**
     * Applies primary button styling (gold theme)
     */
    private void styleBtn(JButton btn) {
        btn.setBackground(PRIMARY);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
    }

    /**
     * Applies danger button styling (red delete button)
     */
    private void styleDangerBtn(JButton btn) {
        btn.setBackground(new Color(180, 50, 50));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
    }

    /**
     * Updates user information in the database after validation
     */
    private void updateUser() {

        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();

        // Validate required fields
        if (name.isEmpty() || email.isEmpty()) {
            CustomDialog.showError(this, "Name and Email required!");
            return;
        }

        // Validate email format
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            CustomDialog.showError(this, "Invalid email!");
            return;
        }

        // Validate Irish phone format
        if (!phone.matches("(\\+353|0)8\\d{8}")) {
            CustomDialog.showError(this, "Invalid phone!");
            return;
        }

        // Update database
        UserDAO dao = new UserDAO();
        boolean ok = dao.updateUserFull(Session.userId, name, email, phone);

        if (ok) {
            CustomDialog.showInfo(this, "Updated successfully!");
        } else {
            CustomDialog.showError(this, "Error updating user");
        }
    }

    /**
     * Deletes user account after confirmation dialog
     */
    private void deleteUser() {

        // Confirmation dialog before deletion
        boolean confirm = CustomDialog.confirm(this, "Delete your account?");
        if (!confirm) return;

        UserDAO dao = new UserDAO();
        boolean ok = dao.deleteUser(Session.userId);

        if (ok) {
            CustomDialog.showInfo(this, "Account deleted");

            // Clear session after deletion
            Session.userId = -1;
            Session.userEmail = null;

            // Redirect to login screen
            new LoginUserGUI().setVisible(true);
            dispose();

        } else {
            CustomDialog.showError(this, "Error deleting user");
        }
    }
}