package User;

import javax.swing.*;
import java.awt.*;

/**
 * GUI class representing the user account dashboard.
 * Provides navigation to personal details, address, and payment history.
 */
public class AccountGUI extends JFrame {

    // Light theme background color
    private final Color BG_COLOR = new Color(235, 236, 240);

    // Primary accent color (gold)
    private final Color PRIMARY = new Color(212, 175, 55);

    /**
     * Constructs the Account GUI window.
     */
    public AccountGUI() {

        setTitle("Account");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set background color
        getContentPane().setBackground(BG_COLOR);

        // Add sidebar navigation
        add(new Sidebar(this), BorderLayout.WEST);

        // Create header panel
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_COLOR);

        // Title label
        JLabel title = new JLabel("Your Account");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(PRIMARY);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));

        header.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        header.add(title, BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);

        // Main center panel
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(BG_COLOR);

        // Container for buttons (account options)
        JPanel container = new JPanel();
        container.setLayout(new GridLayout(3, 1, 15, 15));
        container.setBackground(BG_COLOR);
        container.setPreferredSize(new Dimension(420, 320));

        // Button: Personal Details
        container.add(createBigButton(
                "👤 Personal Details",
                () -> new PersonalDetailsGUI().setVisible(true)
        ));

        // Button: Delivery Address
        container.add(createBigButton(
                "📍 Delivery Address",
                () -> new AddressGUI().setVisible(true)
        ));

        // Button: Payment History / Credit
        container.add(createBigButton(
                "💳 Account Credit",
                () -> new PaymentHistoryGUI().setVisible(true)
        ));

        center.add(container);
        add(center, BorderLayout.CENTER);
    }

    /**
     * Creates a styled large button with hover effect and action.
     *
     * @param text button label text
     * @param action action to execute when clicked
     * @return configured JButton
     */
    private JButton createBigButton(String text, Runnable action) {

        JButton btn = new JButton(text);

        // Default button styling
        btn.setBackground(new Color(28, 28, 32));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(25, 20, 25, 20));
        btn.setHorizontalAlignment(SwingConstants.LEFT);

        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(40, 40, 45));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(28, 28, 32));
            }
        });

        // Click action
        btn.addActionListener(e -> action.run());

        return btn;
    }
}