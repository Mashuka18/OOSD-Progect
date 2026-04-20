package MenuItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DB.DatabaseConnection;

/**
 * This class demonstrates how to delete a menu item
 * from the database using its ID.
 */
public class DeleteMenuItem {

    /**
     * Main method that connects to the database and deletes
     * a menu item based on a specified ID.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        Connection connection = null;
        PreparedStatement pstat = null;

        // ID of the menu item to delete (example value)
        int itemId = 6;

        try {
            // Establish connection to the database
            connection = DatabaseConnection.getConnection();

            // SQL DELETE query with parameter placeholder
            String sql = "DELETE FROM menu_items WHERE item_id = ?";

            // Prepare the SQL statement
            pstat = connection.prepareStatement(sql);

            // Set the ID parameter in the query
            pstat.setInt(1, itemId);

            // Execute the DELETE operation
            int i = pstat.executeUpdate();

            // Output result (number of rows affected)
            System.out.println(i + " menu item deleted.");

        } catch (SQLException e) {
            // Handle SQL errors
            e.printStackTrace();
        } finally {
            // Close resources to prevent memory leaks
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