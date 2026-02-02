import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertUser {

    public static void main(String[] args) {

        Connection connection = null;  
        PreparedStatement pstat = null;

        // Test data that will be inserted into the users table
        String name = "Anna Test";
        String email = "anna@test.com";
        String phone = "0871111111";
        String address = "Dublin";
        String password = "pass123";

        try {
            // get connection from your class
            connection = DatabaseConnection.getConnection();

            // SQL INSERT query with placeholders (?)
            // Placeholders protect against SQL Injection
            String sql = "INSERT INTO users (name, email, phone, address, password) " +
                         "VALUES (?, ?, ?, ?, ?)";

            // Create PreparedStatement object using SQL query
            pstat = connection.prepareStatement(sql);

            // set values
            pstat.setString(1, name);
            pstat.setString(2, email);
            pstat.setString(3, phone);
            pstat.setString(4, address);
            pstat.setString(5, password);

            // execute
            // Returns number of rows affected
            int i = pstat.executeUpdate();
            // Print confirmation message
            System.out.println(i + " record successfully added to the table.");

        } catch (SQLException e) {
            // Catch and print SQL errors (e.g. duplicate email)
            e.printStackTrace();
        } finally {
             // Close resources to avoid memory leaks
            try {
                if (pstat != null) pstat.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
