package MenuItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DB.DatabaseConnection;

/**
 * Data Access Object (DAO) class for handling database operations
 * related to MenuItem objects.
 * 
 * Provides methods to create, read, update, and delete menu items.
 */
public class MenuItemDAO {

    /**
     * Inserts a new menu item into the database.
     *
     * @param restaurantId the ID of the restaurant
     * @param categoryId the ID of the category
     * @param name the name of the menu item
     * @param price the price of the menu item
     * @return true if insertion was successful, false otherwise
     */
    public boolean insertMenuItem(int restaurantId, int categoryId, String name, double price) {

        // SQL INSERT query
        String sql = "INSERT INTO menu_items (restaurant_id, category_id, name, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set parameters
            ps.setInt(1, restaurantId);
            ps.setInt(2, categoryId);
            ps.setString(3, name);
            ps.setDouble(4, price);

            // Execute and return success status
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            // Handle database errors
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Retrieves all menu items for a given restaurant.
     *
     * @param restaurantId the ID of the restaurant
     * @return a list of MenuItem objects
     */
    public List<MenuItem> getMenuByRestaurantId(int restaurantId) {

        // List to store retrieved menu items
        List<MenuItem> list = new ArrayList<>();

        try {
            // Establish database connection
            Connection conn = DatabaseConnection.getConnection();

            // SQL SELECT query with JOIN to get category name
            String sql = "SELECT m.*, c.name AS category_name " +
                         "FROM menu_items m " +
                         "JOIN categories c ON m.category_id = c.category_id " +
                         "WHERE m.restaurant_id = ?";

            // Prepare statement
            PreparedStatement ps = conn.prepareStatement(sql);

            // Set restaurant ID parameter
            ps.setInt(1, restaurantId);

            // Execute query
            ResultSet rs = ps.executeQuery();

            // Process result set
            while (rs.next()) {

                list.add(new MenuItem(
                    rs.getInt("item_id"),       // Menu item ID
                    rs.getString("name"),       // Item name
                    rs.getDouble("price"),      // Item price
                    rs.getString("category_name"), // Category name
                    rs.getString("image")       // Image path
                ));
            }

        } catch (Exception e) {
            // Handle errors
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Updates the price of a menu item.
     *
     * @param itemId the ID of the menu item
     * @param price the new price
     * @return true if update was successful, false otherwise
     */
    public boolean updateMenuItem(int itemId, double price) {

        // SQL UPDATE query
        String sql = "UPDATE menu_items SET price=? WHERE item_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set parameters
            ps.setDouble(1, price);
            ps.setInt(2, itemId);

            // Execute update
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            // Handle errors
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Deletes a menu item from the database.
     *
     * @param itemId the ID of the menu item
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteMenuItem(int itemId) {

        // SQL DELETE query
        String sql = "DELETE FROM menu_items WHERE item_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set item ID
            ps.setInt(1, itemId);

            // Execute delete
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            // Handle errors
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Retrieves menu items by restaurant and category name.
     *
     * @param restaurantId the ID of the restaurant
     * @param categoryName the name of the category
     * @return a list of formatted menu item strings
     */
    public List<String> getMenuByRestaurantAndCategory(int restaurantId, String categoryName) {

        // List to store formatted menu items
        List<String> items = new ArrayList<>();

        // SQL query with filtering by category name
        String sql =
                "SELECT m.name, m.price " +
                "FROM menu_items m " +
                "JOIN categories c ON m.category_id = c.category_id " +
                "WHERE m.restaurant_id = ? AND c.name = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set parameters
            ps.setInt(1, restaurantId);
            ps.setString(2, categoryName);

            // Execute query
            ResultSet rs = ps.executeQuery();

            // Process results
            while (rs.next()) {
                items.add(rs.getString("name") + " - €" + rs.getDouble("price"));
            }

        } catch (Exception e) {
            // Handle errors
            e.printStackTrace();
        }

        return items;
    }
}