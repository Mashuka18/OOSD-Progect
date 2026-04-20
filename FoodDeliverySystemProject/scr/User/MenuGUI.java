package User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import Restaurant.RestaurantDAO;
import MenuItem.MenuItemDAO;
import MenuItem.MenuItem;

/**
 * Menu screen that displays restaurant menu items.
 * Supports searching, filtering by category, and adding items to cart.
 */
public class MenuGUI extends JFrame {

    private JPanel mainPanel;
    private JTextField searchField;

    // 🎨 UI THEME COLORS
    private final Color BG_COLOR = new Color(235, 236, 240);
    private final Color CARD_COLOR = new Color(28, 28, 32);
    private final Color PRIMARY = new Color(212, 175, 55);
    private final Color TEXT_MAIN = Color.WHITE;
    private final Color TEXT_SECOND = new Color(170, 170, 170);
    private final Color SIDEBAR = new Color(15, 15, 18);

    private String image;

    /**
     * Returns image name (if needed for future extensions).
     */
    public String getImage() {
        return image;
    }

    /**
     * Constructor - builds menu UI for selected restaurant.
     */
    public MenuGUI(String restaurantName) {

        setTitle(restaurantName + " Menu");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(BG_COLOR);

        add(createTopBar(restaurantName), BorderLayout.NORTH);
        add(new Sidebar(this), BorderLayout.WEST);

        // Main product grid
        mainPanel = new JPanel(new GridLayout(0, 2, 15, 15));
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scroll = new JScrollPane(mainPanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(20);

        add(scroll, BorderLayout.CENTER);

        loadMenuItems(restaurantName);
    }

    /**
     * Creates top bar with search and category filters.
     */
    private JPanel createTopBar(String restaurantName) {

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(BG_COLOR);
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // SEARCH SECTION
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setBackground(BG_COLOR);

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(220, 32));

        JButton searchBtn = new JButton("Search");
        searchBtn.setBackground(PRIMARY);
        searchBtn.setForeground(Color.BLACK);

        searchBtn.addActionListener(e -> applySearch(restaurantName));

        left.add(searchField);
        left.add(searchBtn);

        // CATEGORY FILTERS
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setBackground(BG_COLOR);

        String[] categories = {
                "All", "PIZZA", "BURGERS", "DRINKS", "DESSERTS", "COFFEE", "MEALS"
        };

        for (String cat : categories) {

            JButton btn = new JButton(cat);
            btn.setBackground(SIDEBAR);
            btn.setForeground(TEXT_MAIN);

            btn.addActionListener(e -> filterByCategory(restaurantName, cat));

            right.add(btn);
        }

        topBar.add(left, BorderLayout.WEST);
        topBar.add(right, BorderLayout.CENTER);

        return topBar;
    }

    /**
     * Search items by name.
     */
    private void applySearch(String restaurantName) {

        String query = searchField.getText().toLowerCase();

        mainPanel.removeAll();

        for (MenuItem item : getItems(restaurantName)) {
            if (item.getName().toLowerCase().contains(query)) {
                mainPanel.add(createCard(item));
            }
        }

        refresh();
    }

    /**
     * Filter menu items by category.
     */
    private void filterByCategory(String restaurantName, String category) {

        mainPanel.removeAll();

        for (MenuItem item : getItems(restaurantName)) {

            if (category.equals("All") ||
                item.getCategory().equalsIgnoreCase(category)) {

                mainPanel.add(createCard(item));
            }
        }

        refresh();
    }

    /**
     * Fetch menu items from database.
     */
    private List<MenuItem> getItems(String restaurantName) {

        RestaurantDAO rDao = new RestaurantDAO();
        int restaurantId = rDao.getRestaurantIdByName(restaurantName);

        MenuItemDAO mDao = new MenuItemDAO();
        return mDao.getMenuByRestaurantId(restaurantId);
    }

    /**
     * Creates UI card for a single menu item.
     */
    private JPanel createCard(MenuItem item) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(50,50,55)),
                BorderFactory.createEmptyBorder(10,10,10,10)
        ));

        // IMAGE
        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(120, 100));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        try {
            String path = "/assets/" + item.getImage();
            java.net.URL imgURL = getClass().getResource(path);

            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image img = icon.getImage().getScaledInstance(120, 100, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(img));
            } else {
                imageLabel.setText("No Image");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // TEXT INFO
        JLabel name = new JLabel(item.getName());
        name.setForeground(TEXT_MAIN);
        name.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel category = new JLabel(item.getCategory());
        category.setForeground(TEXT_SECOND);

        JLabel price = new JLabel("€" + item.getPrice());
        price.setForeground(PRIMARY);
        price.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JPanel text = new JPanel();
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.setBackground(CARD_COLOR);

        text.add(name);
        text.add(category);
        text.add(Box.createVerticalStrut(5));
        text.add(price);

        // ADD BUTTON
        JButton addBtn = new JButton("Add");
        addBtn.setBackground(PRIMARY);
        addBtn.setForeground(Color.BLACK);
        addBtn.setFocusPainted(false);

        addBtn.addActionListener(e -> {
            Cart.addItem(item.getName(), item.getPrice());
            JOptionPane.showMessageDialog(this, item.getName() + " added!");
        });

        card.add(imageLabel, BorderLayout.WEST);
        card.add(text, BorderLayout.CENTER);
        card.add(addBtn, BorderLayout.EAST);

        // hover effect
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBackground(new Color(40,40,45));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBackground(CARD_COLOR);
            }
        });

        return card;
    }

    /**
     * Loads all menu items for restaurant.
     */
    private void loadMenuItems(String restaurantName) {

        mainPanel.removeAll();

        for (MenuItem item : getItems(restaurantName)) {
            mainPanel.add(createCard(item));
        }

        refresh();
    }

    /**
     * Refresh UI after updates.
     */
    private void refresh() {
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}