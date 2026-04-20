package Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DB.DatabaseConnection;

/**
 * Data Access Object (DAO) class for handling payment-related
 * database operations.
 */
public class PaymentDAO {

    /**
     * Inserts a new payment record into the payment history table.
     *
     * @param orderId the ID of the order
     * @param userId the ID of the user making the payment
     * @param amount the payment amount
     * @param method the payment method (e.g., Card, Cash)
     * @return true if insertion was successful, false otherwise
     */
    public boolean insertPayment(int orderId, int userId, double amount, String method) {

        // SQL INSERT query
        String sql = "INSERT INTO payment_history (order_id, user_id, amount, method, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set parameters
            ps.setInt(1, orderId);
            ps.setInt(2, userId);
            ps.setDouble(3, amount);
            ps.setString(4, method);
            ps.setString(5, "PAID");

            // Execute insert
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            // Handle database errors
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Retrieves all payments for a specific user.
     *
     * @param userId the ID of the user
     * @return a list of formatted payment strings
     */
    public List<String> getPaymentsByUser(int userId) {

        // List to store payment history
        List<String> list = new ArrayList<>();

        // SQL SELECT query
        String sql = "SELECT * FROM payment_history WHERE user_id=? ORDER BY payment_id DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set user ID
            ps.setInt(1, userId);

            // Execute query
            ResultSet rs = ps.executeQuery();

            // Process results
            while (rs.next()) {

                String row =
                        "Order #" + rs.getInt("order_id") +
                        " | €" + rs.getDouble("amount") +
                        " | " + rs.getString("method") +
                        " | " + rs.getString("status");

                list.add(row);
            }

        } catch (Exception e) {
            // Handle errors
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Updates the status of a payment.
     *
     * @param paymentId the ID of the payment
     * @param status the new status (e.g., PAID, FAILED, REFUNDED)
     * @return true if update was successful, false otherwise
     */
    public boolean updatePayment(int paymentId, String status) {

        // SQL UPDATE query
        String sql = "UPDATE payment_history SET status=? WHERE payment_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set parameters
            ps.setString(1, status);
            ps.setInt(2, paymentId);

            // Execute update
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            // Handle errors
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Deletes a payment record from the database.
     *
     * @param paymentId the ID of the payment to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean deletePayment(int paymentId) {

        // SQL DELETE query
        String sql = "DELETE FROM payment_history WHERE payment_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set payment ID
            ps.setInt(1, paymentId);

            // Execute delete
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            // Handle errors
            e.printStackTrace();
        }

        return false;
    }
}