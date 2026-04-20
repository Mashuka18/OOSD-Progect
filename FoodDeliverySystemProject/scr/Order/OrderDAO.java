package Order;

import java.sql.*;

import DB.DatabaseConnection;

/**
 * Data Access Object (DAO) class for handling database operations
 * related to orders.
 */
public class OrderDAO {

    /**
     * Creates a new order in the database with status "PENDING".
     *
     * @param userId the ID of the user placing the order
     * @param totalAmount the total amount of the order
     * @return the generated order ID, or -1 if creation failed
     */
    public int createOrder(int userId, double totalAmount) {

        // SQL INSERT query with default status
        String sql = "INSERT INTO orders (user_id, total_amount, status) VALUES (?, ?, 'PENDING')";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Set parameters
            ps.setInt(1, userId);
            ps.setDouble(2, totalAmount);

            // Execute insert
            ps.executeUpdate();

            // Get generated order ID
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1); // order_id
            }

        } catch (Exception e) {
            // Handle database errors
            e.printStackTrace();
        }

        return -1; // return -1 if order creation failed
    }

    /**
     * Updates the status of an existing order.
     *
     * @param orderId the ID of the order to update
     * @param status the new status (e.g., PENDING, COMPLETED, CANCELLED)
     */
    public void updateStatus(int orderId, String status) {

        // SQL UPDATE query
        String sql = "UPDATE orders SET status=? WHERE order_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set parameters
            ps.setString(1, status);
            ps.setInt(2, orderId);

            // Execute update
            ps.executeUpdate();

        } catch (Exception e) {
            // Handle database errors
            e.printStackTrace();
        }
    }
}