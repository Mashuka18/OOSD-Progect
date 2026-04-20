package User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DB.DatabaseConnection;

/**
 * Simple utility class for updating a user's email in the database.
 *
 * Intended for testing / learning purposes (console-based execution).
 */
public class UpdateUser {

    public static void main(String[] args) {

        Connection connection = null;     // DB connection object
        PreparedStatement pstat = null;   // SQL statement executor

        // Test data (hardcoded for demonstration)
        String newEmail = "anna_updated@test.com";
        int userId = 1;   // ID of the user to update

        try {
            // Establish database connection
            connection = DatabaseConnection.getConnection();

            // SQL query to update user email
            String sql = "UPDATE users SET email = ? WHERE user_id = ?";

            // Prepare statement with placeholders
            pstat = connection.prepareStatement(sql);

            // Bind parameters to query
            pstat.setString(1, newEmail); // new email value
            pstat.setInt(2, userId);      // target user ID

            // Execute update (returns number of affected rows)
            int i = pstat.executeUpdate();

            // Output result to console
            System.out.println(i + " record successfully updated in the table.");

        } catch (SQLException e) {
            // Handle SQL-related errors (connection/query issues)
            e.printStackTrace();

        } finally {
            // Always close resources to prevent memory leaks
            try {
                if (pstat != null) pstat.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}