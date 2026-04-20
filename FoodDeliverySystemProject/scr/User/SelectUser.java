package User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DB.DatabaseConnection;

/**
 * Simple console-based utility class for reading all users
 * from the database (debug / testing purpose).
 */
public class SelectUser {

    public static void main(String[] args) {

        Connection connection = null;        // DB connection object
        PreparedStatement pstat = null;      // SQL statement handler
        ResultSet rs = null;                 // stores query results (rows)

        try {
            // 🔷 Establish connection to database
            connection = DatabaseConnection.getConnection();

            // 🔷 SQL query to fetch all users
            String sql = "SELECT * FROM users";

            // 🔷 Prepare SQL statement
            pstat = connection.prepareStatement(sql);

            // 🔷 Execute query and store results in ResultSet
            rs = pstat.executeQuery();

            System.out.println("Users in database:");

            // 🔷 Iterate through each row in ResultSet
            // rs.next() moves pointer to next record
            while (rs.next()) {

                // Extract column values from current row
                int id = rs.getInt("user_id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");

                // Print formatted output for debugging
                System.out.println(id + " | " + name + " | " + email + " | " + phone);
            }

        } catch (SQLException e) {
            // 🔴 Handle database-related errors
            e.printStackTrace();

        } finally {
            // 🔷 Clean up resources to prevent memory leaks
            try {
                if (rs != null) rs.close();
                if (pstat != null) pstat.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}