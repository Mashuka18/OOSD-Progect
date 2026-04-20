package User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Reusable Sidebar component used across multiple GUI screens.
 *
 * Provides navigation (Dashboard, Cart, Account) and Logout functionality.
 * Automatically disposes the parent window when switching screens.
 */
public class Sidebar extends JPanel {

    // HEME COLORS (consistent navigation styling)
    private final Color PRIMARY = new Color(212, 175, 55);
    private final Color TEXT = Color.WHITE;
    private final Color SIDEBAR = new Color(15, 15, 18);
    private final Color SIDEBAR_HOVER = new Color(40, 40, 45);

    /**
     * Constructor builds sidebar UI and binds navigation actions.
     *
     * @param parent the current JFrame to be closed when navigating
     */
    public Sidebar(JFrame parent) {

        // Sidebar size and layout configuration
        setPreferredSize(new Dimension(180, 0));
        setLayout(new BorderLayout()); // 🔥 Allows top/bottom separation
        setBackground(SIDEBAR);

        // TOP SECTION (Logo + navigation buttons)
        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBackground(SIDEBAR);
        top.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // App logo/title
        JLabel logo = new JLabel("FoodApp");
        logo.setForeground(PRIMARY);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Navigation buttons
        JButton dashboardBtn = createButton("Dashboard");
        JButton cartBtn = createButton("Cart");
        JButton accountBtn = createButton("Account");

        // Navigation actions (open new screen + close current)
        dashboardBtn.addActionListener(e -> {
            new DashboardGUI().setVisible(true);
            parent.dispose(); // close current window
        });

        cartBtn.addActionListener(e -> {
            new CartGUI().setVisible(true);
            parent.dispose();
        });

        accountBtn.addActionListener(e -> {
            new AccountGUI().setVisible(true);
            parent.dispose();
        });

        // Add components to top section
        top.add(logo);
        top.add(Box.createRigidArea(new Dimension(0, 30)));
        top.add(dashboardBtn);
        top.add(Box.createRigidArea(new Dimension(0, 10)));
        top.add(cartBtn);
        top.add(Box.createRigidArea(new Dimension(0, 10)));
        top.add(accountBtn);

        // BOTTOM SECTION (Logout always at bottom)
        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.setBackground(SIDEBAR);
        bottom.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

        JButton logoutBtn = createButton("Logout");

        // Logout clears session and returns to login screen
        logoutBtn.addActionListener(e -> {
            Session.userId = -1;
            Session.userEmail = null;
            new LoginUserGUI().setVisible(true);
            parent.dispose();
        });

        bottom.add(logoutBtn);

        // Layout placement (top + bottom sections)
        add(top, BorderLayout.NORTH);
        add(bottom, BorderLayout.SOUTH);
    }

    /**
     * Creates a styled navigation button with hover effect.
     *
     * @param text button label
     * @return configured JButton
     */
    private JButton createButton(String text) {

        JButton btn = new JButton(text);

        // Basic styling
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setBackground(SIDEBAR);
        btn.setForeground(TEXT);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Hover effect for better UX feedback
        btn.addMouseListener(new MouseAdapter() {

            // Mouse enters button area → highlight
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(SIDEBAR_HOVER);
            }

            // Mouse leaves button area → restore default color
            public void mouseExited(MouseEvent e) {
                btn.setBackground(SIDEBAR);
            }
        });

        return btn;
    }
}