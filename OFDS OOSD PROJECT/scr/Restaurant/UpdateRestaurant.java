import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateRestaurant {

    public static void main(String[] args) {

        Connection connection = null;
        PreparedStatement pstat = null;
        // ID of restaurant to update
        int restaurantId = 1; // Dominos Pizza
        String newAddress = "Dublin City Centre";
        String newPhone = "011122233";

        try {
            // Connect to database
            connection = DatabaseConnection.getConnection();
            // SQL UPDATE query
            String sql = "UPDATE restaurants SET address = ?, phone = ? WHERE restaurant_id = ?";
            // Prepare SQL
            pstat = connection.prepareStatement(sql);
            // Set new values
            pstat.setString(1, newAddress);
            pstat.setString(2, newPhone);
            pstat.setInt(3, restaurantId);
            // Execute UPDATE
            int i = pstat.executeUpdate();
            System.out.println(i + " restaurant updated.");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (pstat != null) pstat.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
