package Restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DB.DatabaseConnection;

/**
 * This class demonstrates how to update an existing restaurant
 * record in the database using JDBC.
 */
public class UpdateRestaurant {

    /**
     * Main method that connects to the database and updates
     * restaurant address and phone number.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        // Database connection object
        Connection connection = null;

        // PreparedStatement for SQL execution
        PreparedStatement pstat = null;

        // ID of the restaurant to update (example: Dominos Pizza)
        int restaurantId = 1;

        // New values to update
        String newAddress = "Dublin City Centre";
        String newPhone = "011122233";

        try {
            // Establish connection to database
            connection = DatabaseConnection.getConnection();

            // SQL UPDATE query with placeholders
            String sql = "UPDATE restaurants SET address = ?, phone = ? WHERE restaurant_id = ?";

            // Prepare SQL statement
            pstat = connection.prepareStatement(sql);

            // Set new values
            pstat.setString(1, newAddress);
            pstat.setString(2, newPhone);
            pstat.setInt(3, restaurantId);

            // Execute update
            int i = pstat.executeUpdate();

            // Output result
            System.out.println(i + " restaurant updated.");

        } catch (SQLException e) {
            // Handle SQL errors
            e.printStackTrace();

        } finally {
            // Close database resources to prevent memory leaks
            try {
                if (pstat != null) pstat.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}