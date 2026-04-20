package MenuItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DB.DatabaseConnection;

/**
 * This class demonstrates how to update an existing
 * menu item in the database.
 */
public class UpdateMenuItem {

    /**
     * Main method that connects to the database and updates
     * the price of a menu item using its ID.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        Connection connection = null;
        PreparedStatement pstat = null;

        // ID of the menu item to update (example: Pepperoni Pizza)
        int itemId = 1;

        // New price to be set
        double newPrice = 13.49;

        try {
            // Establish connection to the database
            connection = DatabaseConnection.getConnection();

            // SQL UPDATE query with placeholders
            String sql = "UPDATE menu_items SET price = ? WHERE item_id = ?";

            // Prepare SQL statement
            pstat = connection.prepareStatement(sql);

            // Set parameters (new price and item ID)
            pstat.setDouble(1, newPrice);
            pstat.setInt(2, itemId);

            // Execute UPDATE operation
            int i = pstat.executeUpdate();

            // Output result (number of rows affected)
            System.out.println(i + " menu item updated.");

        } catch (SQLException e) {
            // Handle SQL errors
            e.printStackTrace();
        } finally {
            // Close database resources to prevent memory leaks
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