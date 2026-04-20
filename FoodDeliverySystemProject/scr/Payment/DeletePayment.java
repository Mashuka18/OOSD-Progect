package Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DB.DatabaseConnection;

/**
 * This class demonstrates how to delete a payment record
 * from the database using JDBC.
 */
public class DeletePayment {

    /**
     * Main method that connects to the database and deletes
     * a payment based on its ID.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        // Database connection object
        Connection connection = null;

        // PreparedStatement for executing SQL query
        PreparedStatement pstat = null;

        // ID of the payment to be deleted (example value)
        int paymentId = 1;

        try {
            // Establish connection to the database
            connection = DatabaseConnection.getConnection();

            // SQL DELETE query with placeholder
            String sql = "DELETE FROM payments WHERE payment_id = ?";

            // Prepare SQL statement
            pstat = connection.prepareStatement(sql);

            // Set payment ID parameter
            pstat.setInt(1, paymentId);

            // Execute DELETE operation
            int i = pstat.executeUpdate();

            // Output result (number of rows affected)
            System.out.println(i + " payment deleted.");

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