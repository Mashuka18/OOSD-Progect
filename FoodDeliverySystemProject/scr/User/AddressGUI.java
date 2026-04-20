package User;

import javax.swing.*;
import java.awt.*;

/**
 * GUI class for displaying and managing the user's delivery address.
 * Shows the saved address and allows navigation to edit it.
 */
public class AddressGUI extends JFrame {

    private JPanel mainPanel;

    // Theme colors (consistent UI styling)
    private final Color BG_COLOR = new Color(235, 236, 240);
    private final Color CARD_COLOR = new Color(28, 28, 32);
    private final Color PRIMARY = new Color(212, 175, 55);
    private final Color TEXT_MAIN = Color.WHITE;
    private final Color TEXT_SECOND = new Color(170, 170, 170);
    private final Color BORDER = new Color(50, 50, 55);

    /**
     * Constructs the Address GUI window.
     */
    public AddressGUI() {

        setTitle("Delivery Address");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set background theme
        getContentPane().setBackground(BG_COLOR);

        // HEADER section
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_COLOR);
        header.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));

        JLabel title = new JLabel("Delivery Address", SwingConstants.CENTER);
        title.setForeground(PRIMARY);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));

        header.add(title, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        // SIDEBAR navigation
        add(new Sidebar(this), BorderLayout.WEST);

        // CENTER panel (main content area)
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(BG_COLOR);

        add(mainPanel, BorderLayout.CENTER);

        // Load user address data
        loadAddress();
    }

    /**
     * Loads and displays the user's saved delivery address.
     */
    private void loadAddress() {

        UserDAO dao = new UserDAO();

        // Get address from database using session user ID
        String address = dao.getUserAddress(Session.userId);

        // Card container
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        card.setPreferredSize(new Dimension(420, 260));

        // Title label
        JLabel title = new JLabel("Your Delivery Address");
        title.setForeground(TEXT_MAIN);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));

        // Address display
        JLabel addressLabel = new JLabel(
                address != null && !address.isEmpty()
                        ? formatAddress(address)
                        : "No address saved yet"
        );
        addressLabel.setForeground(TEXT_SECOND);
        addressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Status label
        JLabel status = new JLabel(
                address != null && !address.isEmpty()
                        ? "Saved ✓"
                        : "Not set"
        );
        status.setForeground(PRIMARY);
        status.setFont(new Font("Segoe UI", Font.BOLD, 12));

        // Edit button
        JButton editBtn = new JButton("Edit Address");
        editBtn.setBackground(PRIMARY);
        editBtn.setForeground(Color.BLACK);
        editBtn.setFocusPainted(false);
        editBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        editBtn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Button action (navigate to checkout screen)
        editBtn.addActionListener(e -> {
            new CheckoutGUI().setVisible(true);
            dispose();
        });

        // Hover effect
        editBtn.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                editBtn.setBackground(new Color(230, 200, 90));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                editBtn.setBackground(PRIMARY);
            }
        });

        // Layout spacing
        card.add(title);
        card.add(Box.createVerticalStrut(15));
        card.add(addressLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(status);
        card.add(Box.createVerticalGlue());
        card.add(editBtn);

        mainPanel.add(card);
    }

    /**
     * Formats the address into HTML for better UI display.
     *
     * @param address raw address string
     * @return formatted HTML string
     */
    private String formatAddress(String address) {
        return "<html><div style='width:250px;'>" + address + "</div></html>";
    }
}