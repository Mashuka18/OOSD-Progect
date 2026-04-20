package User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import Restaurant.Restaurant;
import Restaurant.RestaurantDAO;

/**
 * Dashboard GUI displaying all restaurants in card format.
 * Allows user to browse restaurants and open their menus.
 */
public class Dash extends JFrame {

    private JPanel mainPanel;

    /**
     * Constructs the Dashboard window.
     */
    public Dash() {

        setTitle("Dashboard");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Sidebar navigation
        add(new Sidebar(this), BorderLayout.WEST);

        // TOP BAR (welcome + title)
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel welcome = new JLabel("Welcome, " + Session.userEmail);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel title = new JLabel("Browse Restaurants");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        topBar.add(welcome, BorderLayout.WEST);
        topBar.add(title, BorderLayout.CENTER);

        add(topBar, BorderLayout.NORTH);

        // MAIN GRID PANEL
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 3, 20, 20));
        mainPanel.setBackground(new Color(250, 250, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scroll = new JScrollPane(mainPanel);
        scroll.setBorder(null);

        add(scroll, BorderLayout.CENTER);

        // Load restaurants from DB
        loadRestaurants();
    }

    /**
     * Loads all restaurants from database and displays them as cards.
     */
    private void loadRestaurants() {

        mainPanel.removeAll();

        RestaurantDAO dao = new RestaurantDAO();
        List<Restaurant> restaurants = dao.getAllRestaurants();

        for (Restaurant r : restaurants) {
            mainPanel.add(createRestaurantCard(r));
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    /**
     * Creates a visual card for a single restaurant.
     *
     * @param restaurant restaurant object
     * @return JPanel card component
     */
    private JPanel createRestaurantCard(Restaurant restaurant) {

        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
        card.setPreferredSize(new Dimension(200, 180));

        // IMAGE section
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(200, 100));

        try {
            ImageIcon icon = new ImageIcon("scr/assets/" + restaurant.getImage());
            Image img = icon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            imageLabel.setText("🍔"); // fallback if image missing
        }

        // NAME label
        JLabel nameLabel = new JLabel(restaurant.getName(), SwingConstants.CENTER);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // RATING label
        JLabel rating = new JLabel("⭐ " + restaurant.getRating(), SwingConstants.CENTER);

        // VIEW MENU button
        JButton viewBtn = new JButton("View Menu");
        viewBtn.setBackground(new Color(255, 140, 0));
        viewBtn.setForeground(Color.WHITE);
        viewBtn.setFocusPainted(false);

        viewBtn.addActionListener(e -> openMenu(restaurant.getName()));

        // Bottom panel layout
        JPanel bottom = new JPanel(new GridLayout(3, 1));
        bottom.setBackground(Color.WHITE);
        bottom.add(nameLabel);
        bottom.add(rating);
        bottom.add(viewBtn);

        card.add(imageLabel, BorderLayout.NORTH);
        card.add(bottom, BorderLayout.CENTER);

        return card;
    }

    /**
     * Opens menu screen for selected restaurant.
     *
     * @param restaurantName name of restaurant
     */
    private void openMenu(String restaurantName) {
        new MenuGUI(restaurantName).setVisible(true);
        dispose();
    }
}