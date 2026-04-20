package MenuItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DB.DatabaseConnection;

/**
 * This class demonstrates how to insert a new menu item
 * into the database using JDBC.
 */
public class InsertMenuItem {

    /**
     * Main method that connects to the database and inserts
     * a new menu item using predefined test data.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        Connection connection = null;
        PreparedStatement pstat = null;

        // Test data for new menu item
        int restaurantId = 1; // Example: Dominos restaurant ID
        int categoryId = 1;   // Example: Food category ID
        String name = "Pepperoni Pizza"; // Name of the menu item
        double price = 12.99; // Price of the item

        try {
            // Establish connection to the database
            connection = DatabaseConnection.getConnection();

            // SQL INSERT query with placeholders
            String sql = "INSERT INTO menu_items (restaurant_id, category_id, name, price) " +
                         "VALUES (?, ?, ?, ?)";

            // Prepare SQL statement
            pstat = connection.prepareStatement(sql);

            // Set values for each placeholder
            pstat.setInt(1, restaurantId);
            pstat.setInt(2, categoryId);
            pstat.setString(3, name);
            pstat.setDouble(4, price);

            // Execute INSERT operation
            int i = pstat.executeUpdate();

            // Output result (number of rows inserted)
            System.out.println(i + " menu item added.");

        } catch (SQLException e) {
            // Handle SQL errors
            e.printStackTrace();
        } finally {
            // Close database resources to prevent leaks
            try {
                if (pstat != null) pstat.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                // Handle closing errors
                e.printStackTrace();
            }
        }
    }
}