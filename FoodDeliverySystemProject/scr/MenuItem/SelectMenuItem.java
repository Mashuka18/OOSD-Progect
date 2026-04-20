package MenuItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DB.DatabaseConnection;

/**
 * This class demonstrates how to retrieve (SELECT)
 * menu items from the database for a specific restaurant.
 */
public class SelectMenuItem {

    /**
     * Main method that connects to the database and retrieves
     * all menu items for a given restaurant ID.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        Connection connection = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;

        // ID of the restaurant (change for testing)
        int restaurantId = 1;

        try {
            // Establish connection to the database
            connection = DatabaseConnection.getConnection();

            // SQL SELECT query with parameter
            String sql = "SELECT * FROM menu_items WHERE restaurant_id = ?";

            // Prepare statement
            pstat = connection.prepareStatement(sql);

            // Set restaurant ID parameter
            pstat.setInt(1, restaurantId);

            // Execute query
            rs = pstat.executeQuery();

            // Output header
            System.out.println("Menu items:");

            // Iterate through results
            while (rs.next()) {
                System.out.println(
                        rs.getInt("item_id") + " | " +     // Item ID
                        rs.getString("name") + " | €" +    // Item name
                        rs.getDouble("price")              // Item price
                );
            }

        } catch (Exception e) {
            // Handle errors
            e.printStackTrace();
        } finally {
            // Close all resources to prevent memory leaks
            try {
                if (rs != null) rs.close();
                if (pstat != null) pstat.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                // Handle closing errors
                e.printStackTrace();
            }
        }
    }
}