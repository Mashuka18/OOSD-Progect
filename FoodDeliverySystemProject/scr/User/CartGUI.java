package User;

import javax.swing.*;
import java.awt.*;

/**
 * GUI class representing the shopping cart screen.
 * Displays items added to cart, allows quantity updates,
 * and calculates subtotal and total price.
 */
public class CartGUI extends JFrame {

    private JPanel itemsPanel;
    private JLabel subtotalLabel, totalLabel;

    // Fixed fees
    private final double SERVICE_FEE = 0.99;
    private final double DELIVERY_FEE = 2.99;

    // UI Theme colors
    private final Color BG = new Color(235, 236, 240);
    private final Color CARD = new Color(28, 28, 32);
    private final Color PRIMARY = new Color(212, 175, 55);
    private final Color TEXT = Color.WHITE;
    private final Color TEXT_SECOND = new Color(170, 170, 170);
    private final Color SIDEBAR = new Color(15, 15, 18);
    private final Color SIDEBAR_HOVER = new Color(40, 40, 45);

    /**
     * Constructs the Cart GUI window.
     */
    public CartGUI() {

        setTitle("Cart");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set background theme
        getContentPane().setBackground(BG);

        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG);

        JLabel title = new JLabel("Cart", SwingConstants.CENTER);
        title.setForeground(PRIMARY);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        header.add(title, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        // SIDEBAR navigation
        add(createSidebar(), BorderLayout.WEST);

        // MAIN panel
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BG);
        main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Items list
        itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        itemsPanel.setBackground(BG);

        loadItems();

        JScrollPane scroll = new JScrollPane(itemsPanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        main.add(scroll, BorderLayout.CENTER);

        // BOTTOM summary section
        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.setBackground(CARD);
        bottom.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        subtotalLabel = new JLabel();
        subtotalLabel.setForeground(TEXT);

        JLabel service = new JLabel("Service fee: €" + SERVICE_FEE);
        service.setForeground(TEXT_SECOND);

        JLabel delivery = new JLabel("Delivery fee: €" + DELIVERY_FEE);
        delivery.setForeground(TEXT_SECOND);

        totalLabel = new JLabel();
        totalLabel.setForeground(PRIMARY);
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        updateTotals();

        // Checkout button
        JButton checkout = new JButton("Checkout");
        checkout.setBackground(PRIMARY);
        checkout.setForeground(Color.BLACK);
        checkout.setFocusPainted(false);

        checkout.addActionListener(e -> {
            if (Cart.quantities.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Cart is empty!");
                return;
            }
            new CheckoutGUI().setVisible(true);
            dispose();
        });

        bottom.add(subtotalLabel);
        bottom.add(service);
        bottom.add(delivery);
        bottom.add(Box.createVerticalStrut(10));
        bottom.add(totalLabel);
        bottom.add(Box.createVerticalStrut(15));
        bottom.add(checkout);

        main.add(bottom, BorderLayout.SOUTH);

        add(main, BorderLayout.CENTER);
    }

    /**
     * Loads all items from the cart and displays them.
     */
    private void loadItems() {

        itemsPanel.removeAll();

        for (String name : Cart.quantities.keySet()) {
            itemsPanel.add(createItemCard(name));
            itemsPanel.add(Box.createVerticalStrut(10));
        }

        itemsPanel.revalidate();
        itemsPanel.repaint();
    }

    /**
     * Creates a UI card for a single cart item.
     *
     * @param name item name
     * @return JPanel representing item
     */
    private JPanel createItemCard(String name) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        double price = Cart.prices.get(name);
        int qty = Cart.quantities.get(name);

        // LEFT section (item info)
        JLabel title = new JLabel(name);
        title.setForeground(TEXT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel priceLabel = new JLabel("€" + price);
        priceLabel.setForeground(TEXT_SECOND);

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(CARD);
        left.add(title);
        left.add(priceLabel);

        // RIGHT section (controls)
        JLabel qtyLabel = new JLabel(String.valueOf(qty));
        qtyLabel.setForeground(TEXT);

        JButton plus = new JButton("+");
        JButton minus = new JButton("-");

        styleSmallButton(plus);
        styleSmallButton(minus);

        plus.addActionListener(e -> {
            Cart.addItem(name, price);
            refresh();
        });

        minus.addActionListener(e -> {
            Cart.removeItem(name);
            refresh();
        });

        JPanel right = new JPanel();
        right.setBackground(CARD);
        right.add(minus);
        right.add(qtyLabel);
        right.add(plus);

        card.add(left, BorderLayout.WEST);
        card.add(right, BorderLayout.EAST);

        return card;
    }

    /**
     * Applies consistent style to small buttons.
     *
     * @param btn button to style
     */
    private void styleSmallButton(JButton btn) {
        btn.setBackground(PRIMARY);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(45, 30));
    }

    /**
     * Creates sidebar navigation panel.
     *
     * @return sidebar JPanel
     */
    private JPanel createSidebar() {

        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(180, getHeight()));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(SIDEBAR);
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JLabel logo = new JLabel("FoodApp");
        logo.setForeground(PRIMARY);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton dashboardBtn = createSidebarButton("Dashboard");
        JButton cartBtn = createSidebarButton("Cart");
        JButton logoutBtn = createSidebarButton("Logout");

        dashboardBtn.addActionListener(e -> {
            new DashboardGUI().setVisible(true);
            dispose();
        });

        cartBtn.addActionListener(e -> {
            new CartGUI().setVisible(true);
            dispose();
        });

        logoutBtn.addActionListener(e -> {
            Session.userId = -1;
            Session.userEmail = null;
            new LoginUserGUI().setVisible(true);
            dispose();
        });

        sidebar.add(logo);
        sidebar.add(Box.createRigidArea(new Dimension(0, 30)));
        sidebar.add(dashboardBtn);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(cartBtn);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(logoutBtn);

        return sidebar;
    }

    /**
     * Creates styled sidebar button with hover effect.
     *
     * @param text button text
     * @return JButton
     */
    private JButton createSidebarButton(String text) {

        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setBackground(SIDEBAR);
        btn.setForeground(TEXT);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(SIDEBAR_HOVER);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(SIDEBAR);
            }
        });

        return btn;
    }

    /**
     * Updates subtotal and total values.
     */
    private void updateTotals() {

        double subtotal = Cart.getSubtotal();
        double total = subtotal + SERVICE_FEE + DELIVERY_FEE;

        subtotalLabel.setText("Subtotal: €" + String.format("%.2f", subtotal));
        totalLabel.setText("Total: €" + String.format("%.2f", total));
    }

    /**
     * Refreshes cart UI after changes.
     */
    private void refresh() {
        loadItems();
        updateTotals();
    }
}