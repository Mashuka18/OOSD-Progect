import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteRestaurant {

    public static void main(String[] args) {
        // Object for database connection
        Connection connection = null;
        // PreparedStatement is used to send SQL commands safely
        PreparedStatement pstat = null;
        // ID of the restaurant we want to delete
        int restaurantId = 2; // McDonalds (example)

        try {
            // Get connection to MySQL database
            connection = DatabaseConnection.getConnection();
            // SQL query with placeholder (?) to avoid SQL injection
            String sql = "DELETE FROM restaurants WHERE restaurant_id = ?";
            // Prepare SQL statement
            pstat = connection.prepareStatement(sql);
            // Replace (?) with restaurantId value
            pstat.setInt(1, restaurantId);
            // Execute DELETE command
            int i = pstat.executeUpdate();
            // Print deleted records
            System.out.println(i + " restaurant deleted.");

        } catch (SQLException e) {
            // Handle SQL errors
            e.printStackTrace();
        /*The finally block ensures that all database resources such as connections and prepared statements
        are properly closed to avoid memory leaks.*/
        } finally {
            try {
                //Checking for NullPointerException error
                if (pstat != null) pstat.close();
                //Closes the connection to the MySQL database & frees up server resources
                if (connection != null) connection.close();
                //If something went wrong when closing (a system error occurred) 
                // the program won't crash, but will redirect here
            } catch (Exception e) {
                //type of error
                e.printStackTrace();
            }
        }
    }
}
