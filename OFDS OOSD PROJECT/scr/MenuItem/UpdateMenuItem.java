import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateMenuItem {

    public static void main(String[] args) {

        Connection connection = null;
        PreparedStatement pstat = null;

        // ID of menu item to update
        int itemId = 1; // Pepperoni Pizza
        double newPrice = 13.49;

        try {
            // Connect to database
            connection = DatabaseConnection.getConnection();
            // SQL UPDATE statement
            String sql = "UPDATE menu_items SET price = ? WHERE item_id = ?";
            // Prepare SQL
            pstat = connection.prepareStatement(sql);
            // Set new price and item ID
            pstat.setDouble(1, newPrice);
            pstat.setInt(2, itemId);
            // Execute UPDATE
            int i = pstat.executeUpdate();
            System.out.println(i + " menu item updated.");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally { // Close resources
            try {
                if (pstat != null) pstat.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
