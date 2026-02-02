import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertRestaurant {

    public static void main(String[] args) {

        Connection connection = null;
        PreparedStatement pstat = null;

        // Test data for new restaurant
        String name = "Dominos Pizza";
        String address = "Dublin City";
        String phone = "012345678";

        try {
            // Connect to database
            connection = DatabaseConnection.getConnection();
            // SQL INSERT statement
            String sql = "INSERT INTO restaurants (name, address, phone) VALUES (?, ?, ?)";
            // Prepare SQL statement
            pstat = connection.prepareStatement(sql);
            // Set values for placeholders
            pstat.setString(1, name);
            pstat.setString(2, address);
            pstat.setString(3, phone);

            // Execute INSERT
            int i = pstat.executeUpdate();
            System.out.println(i + " restaurant added.");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {// Close resources
            try {
                if (pstat != null) pstat.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
