package User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import Restaurant.Restaurant;
import Restaurant.RestaurantDAO;

/**
 * Main dashboard screen displaying all restaurants.
 * Allows navigation to account, cart, and restaurant menus.
 */
public class DashboardGUI extends JFrame {

    private JPanel mainPanel;

    // UI theme colors
    private final Color BG_COLOR = new Color(235, 236, 240);
    private final Color CARD_COLOR = new Color(28, 28, 32);
    private final Color PRIMARY = new Color(212, 175, 55);
    private final Color TEXT_MAIN = Color.WHITE;
    private final Color TEXT_SECOND = new Color(170, 170, 170);
    private final Color SIDEBAR = new Color(15, 15, 18);
    private final Color SIDEBAR_HOVER = new Color(40, 40, 45);

    /**
     * Constructs the Dashboard GUI.
     */
    public DashboardGUI() {

        setTitle("Restaurants");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(BG_COLOR);

        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_COLOR);

        JLabel title = new JLabel("Restaurants");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(PRIMARY);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        header.add(title, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        // SIDEBAR
        add(createSidebar(), BorderLayout.WEST);

        // MAIN GRID
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 3, 20, 20));
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scroll = new JScrollPane(mainPanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(20);

        add(scroll, BorderLayout.CENTER);

        loadRestaurants();
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

        JButton accountBtn = createSidebarButton("Account");
        JButton cartBtn = createSidebarButton("Cart");
        JButton logoutBtn = createSidebarButton("Logout");

        accountBtn.addActionListener(e -> {
            new AccountGUI().setVisible(true);
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
        sidebar.add(accountBtn);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(cartBtn);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(logoutBtn);

        return sidebar;
    }

    /**
     * Creates styled sidebar button with hover effect.
     */
    private JButton createSidebarButton(String text) {

        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setBackground(SIDEBAR);
        btn.setForeground(TEXT_MAIN);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        btn.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(SIDEBAR_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(SIDEBAR);
            }
        });

        return btn;
    }

    /**
     * Loads all restaurants from database.
     */
    private void loadRestaurants() {

        mainPanel.removeAll();

        RestaurantDAO dao = new RestaurantDAO();
        List<Restaurant> list = dao.getAllRestaurants();

        for (Restaurant r : list) {
            mainPanel.add(createCard(r));
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    /**
     * Creates restaurant card UI component.
     *
     * @param restaurant restaurant object
     * @return JPanel card
     */
    private JPanel createCard(Restaurant restaurant) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(50, 50, 55)),
                BorderFactory.createEmptyBorder(0, 0, 10, 0)
        ));

        // IMAGE
        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(200, 120));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        try {
            ImageIcon icon = new ImageIcon(
                    getClass().getResource("/assets/" + restaurant.getImage())
            );
            Image img = icon.getImage().getScaledInstance(200, 120, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            imageLabel.setText("No Image");
            imageLabel.setForeground(TEXT_SECOND);
        }

        // INFO panel
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(CARD_COLOR);
        info.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel name = new JLabel(restaurant.getName());
        name.setForeground(TEXT_MAIN);
        name.setFont(new Font("Segoe UI", Font.BOLD, 15));

        JLabel desc = new JLabel(restaurant.getDescription());
        desc.setForeground(TEXT_SECOND);
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JLabel rating = new JLabel("⭐ " + restaurant.getRating());
        rating.setForeground(PRIMARY);

        JButton btn = new JButton("View Menu");
        btn.setBackground(PRIMARY);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);

        btn.addActionListener(e -> openMenu(restaurant.getName()));

        info.add(name);
        info.add(desc);
        info.add(Box.createRigidArea(new Dimension(0, 5)));
        info.add(rating);
        info.add(Box.createRigidArea(new Dimension(0, 10)));
        info.add(btn);

        card.add(imageLabel, BorderLayout.NORTH);
        card.add(info, BorderLayout.CENTER);

        // hover effect
        card.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(35, 35, 40));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(CARD_COLOR);
            }
        });

        return card;
    }

    /**
     * Opens menu screen for selected restaurant.
     */
    private void openMenu(String restaurantName) {
        new MenuGUI(restaurantName).setVisible(true);
        dispose();
    }
}