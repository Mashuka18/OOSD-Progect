import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertMenuItem {

    public static void main(String[] args) {

        Connection connection = null;
        PreparedStatement pstat = null;

        // Test data for new menu item
        int restaurantId = 1; // Dominos
        int categoryId = 1;   // Food
        String name = "Pepperoni Pizza";
        double price = 12.99;

        try {
            // Connect to database
            connection = DatabaseConnection.getConnection();
            // SQL INSERT with placeholders
            String sql = "INSERT INTO menu_items (restaurant_id, category_id, name, price) " +
                         "VALUES (?, ?, ?, ?)";
            // Prepare SQL statement
            pstat = connection.prepareStatement(sql);
            // Set values for placeholders
            pstat.setInt(1, restaurantId);
            pstat.setInt(2, categoryId);
            pstat.setString(3, name);
            pstat.setDouble(4, price);
            // Execute INSERT
            int i = pstat.executeUpdate();
            System.out.println(i + " menu item added.");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally { // Close database resources
            try {
                if (pstat != null) pstat.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
