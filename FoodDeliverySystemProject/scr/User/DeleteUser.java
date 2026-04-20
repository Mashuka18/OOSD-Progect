package User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DB.DatabaseConnection;

/**
 * Utility class to delete a user from the database.
 * Used for testing or administrative user removal.
 */
public class DeleteUser {

    /**
     * Main method used to execute delete operation.
     */
    public static void main(String[] args) {

        Connection connection = null;
        PreparedStatement pstat = null;

        // ID of user to delete (example)
        int userId = 1;

        try {
            // Establish connection to database
            connection = DatabaseConnection.getConnection();

            // SQL DELETE query with placeholder
            String sql = "DELETE FROM users WHERE user_id = ?";

            // Prepare statement
            pstat = connection.prepareStatement(sql);

            // Set user ID parameter
            pstat.setInt(1, userId);

            // Execute DELETE operation
            int i = pstat.executeUpdate();

            // Output result
            System.out.println(i + " user record successfully removed.");

        } catch (SQLException e) {
            // Handle SQL errors
            e.printStackTrace();

        } finally {
            // Close resources to prevent memory leaks
            try {
                if (pstat != null) pstat.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}