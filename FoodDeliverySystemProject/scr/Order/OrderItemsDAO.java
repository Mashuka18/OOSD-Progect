package Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;

import DB.DatabaseConnection;

/**
 * Data Access Object (DAO) for handling order items.
 * Responsible for saving items belonging to an order.
 */
public class OrderItemsDAO {

    /**
     * Saves multiple items for a given order into the database.
     *
     * @param orderId the ID of the order
     * @param items a map of item IDs (as String) and their quantities
     * @param prices a map of item IDs (as String) and their prices
     */
    public void saveItems(int orderId, Map<String, Integer> items, Map<String, Double> prices) {

        // SQL INSERT query for order items
        String sql = "INSERT INTO order_items (order_id, item_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Loop through all items in the order
            for (String item : items.keySet()) {

                // Set order ID
                ps.setInt(1, orderId);

                // Convert item ID from String to int (assumes key is numeric)
                ps.setInt(2, Integer.parseInt(item));

                // Set quantity for this item
                ps.setInt(3, items.get(item));

                // Set price for this item
                ps.setDouble(4, prices.get(item));

                // Add to batch for efficient execution
                ps.addBatch();
            }

            // Execute all inserts in batch
            ps.executeBatch();

        } catch (Exception e) {
            // Handle database errors
            e.printStackTrace();
        }
    }
}