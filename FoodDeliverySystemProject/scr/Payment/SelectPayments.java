package Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DB.DatabaseConnection;

/**
 * This class demonstrates how to retrieve and display
 * all payment records from the database.
 */
public class SelectPayments {

    /**
     * Main method that connects to the database and retrieves
     * all payments from the payments table.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        // Database connection object
        Connection connection = null;

        // PreparedStatement for SQL execution
        PreparedStatement pstat = null;

        // ResultSet to store query results
        ResultSet rs = null;

        try {
            // Establish connection to database
            connection = DatabaseConnection.getConnection();

            // SQL SELECT query
            String sql = "SELECT * FROM payments";

            // Prepare statement
            pstat = connection.prepareStatement(sql);

            // Execute query
            rs = pstat.executeQuery();

            // Print header
            System.out.println("Payments:");

            // Iterate through results
            while (rs.next()) {
                System.out.println(
                        rs.getInt("payment_id") + " | " +   // Payment ID
                        rs.getInt("user_id") + " | €" +      // User ID
                        rs.getDouble("amount") + " | " +     // Amount
                        rs.getString("method") + " | " +     // Payment method
                        rs.getString("status")               // Status
                );
            }

        } catch (Exception e) {
            // Handle errors
            e.printStackTrace();
        } finally {
            // Close resources safely
            try {
                if (rs != null) rs.close();
                if (pstat != null) pstat.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}