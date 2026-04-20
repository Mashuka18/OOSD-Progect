package Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DB.DatabaseConnection;

/**
 * This class demonstrates how to insert a payment record
 * into the database using JDBC.
 */
public class InsertPayment {

    /**
     * Main method that connects to the database and inserts
     * a new payment record using test data.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        // Database connection object
        Connection connection = null;

        // PreparedStatement for SQL execution
        PreparedStatement pstat = null;

        // Test payment data
        int orderId = 1; // Related order ID
        double amount = 18.99; // Payment amount
        String paymentMethod = "Card"; // Payment method (Card/Cash/etc.)
        String paymentStatus = "Paid"; // Status of payment

        try {
            // Establish connection to database
            connection = DatabaseConnection.getConnection();

            // SQL INSERT query
            String sql = "INSERT INTO payments (order_id, amount, payment_method, payment_status) " +
                         "VALUES (?, ?, ?, ?)";

            // Prepare SQL statement
            pstat = connection.prepareStatement(sql);

            // Set parameters
            pstat.setInt(1, orderId);
            pstat.setDouble(2, amount);
            pstat.setString(3, paymentMethod);
            pstat.setString(4, paymentStatus);

            // Execute INSERT operation
            int i = pstat.executeUpdate();

            // Output result
            System.out.println(i + " payment recorded.");

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