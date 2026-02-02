import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteMenuItem {

    public static void main(String[] args) {

        Connection connection = null;
        PreparedStatement pstat = null;
        // ID of menu item to delete
        int itemId = 6; // as example

        try {
            // Connect to database
            connection = DatabaseConnection.getConnection();
            // SQL DELETE statement
            String sql = "DELETE FROM menu_items WHERE item_id = ?";
            // Prepare SQL
            pstat = connection.prepareStatement(sql);
            // Set item ID
            pstat.setInt(1, itemId);
            // Execute DELETE
            int i = pstat.executeUpdate();
            System.out.println(i + " menu item deleted.");

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
