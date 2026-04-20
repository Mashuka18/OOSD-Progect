package Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;

import DB.DatabaseConnection;

/**
 * This class demonstrates how to update a payment status
 * in the database using JDBC.
 */
public class UpdatePayment {

    /**
     * Main method that connects to the database and updates
     * the status of a payment.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        // Database connection object
        Connection connection = null;

        // PreparedStatement for SQL execution
        PreparedStatement pstat = null;

        // Payment ID to update
        int paymentId = 1;

        // New status value
        String newStatus = "PAID";

        try {
            // Connect to database
            connection = DatabaseConnection.getConnection();

            // SQL UPDATE query
            String sql = "UPDATE payments SET status=? WHERE payment_id=?";

            // Prepare statement
            pstat = connection.prepareStatement(sql);

            // Set parameters
            pstat.setString(1, newStatus);
            pstat.setInt(2, paymentId);

            // Execute update
            int i = pstat.executeUpdate();

            // Output result
            System.out.println(i + " payment updated.");

        } catch (Exception e) {
            // Handle errors
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