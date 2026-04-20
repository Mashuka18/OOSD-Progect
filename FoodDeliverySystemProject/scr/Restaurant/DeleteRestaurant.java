package Restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DB.DatabaseConnection;

/**
 * This class demonstrates how to delete a restaurant
 * record from the database using JDBC.
 */
public class DeleteRestaurant {

    /**
     * Main method that connects to the database and deletes
     * a restaurant based on its ID.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        // Database connection object
        Connection connection = null;

        // PreparedStatement for SQL execution
        PreparedStatement pstat = null;

        // ID of the restaurant to delete (example: McDonalds)
        int restaurantId = 2;

        try {
            // Establish connection to MySQL database
            connection = DatabaseConnection.getConnection();

            // SQL DELETE query with placeholder to prevent SQL injection
            String sql = "DELETE FROM restaurants WHERE restaurant_id = ?";

            // Prepare SQL statement
            pstat = connection.prepareStatement(sql);

            // Set restaurant ID parameter
            pstat.setInt(1, restaurantId);

            // Execute DELETE operation
            int i = pstat.executeUpdate();

            // Output result (number of rows affected)
            System.out.println(i + " restaurant deleted.");

        } catch (SQLException e) {
            // Handle SQL errors
            e.printStackTrace();

        } finally {
            // Ensure all database resources are closed properly
            try {
                if (pstat != null) pstat.close(); // Close statement
                if (connection != null) connection.close(); // Close DB connection
            } catch (Exception e) {
                // Handle errors while closing resources
                e.printStackTrace();
            }
        }
    }
}