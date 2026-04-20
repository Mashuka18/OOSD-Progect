package User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DB.DatabaseConnection;

/**
 * Utility class to insert a new user into the database.
 * This is mainly used for testing or manual user creation.
 */
public class InsertUser {

    /**
     * Main method that executes the INSERT operation.
     */
    public static void main(String[] args) {

        Connection connection = null;
        PreparedStatement pstat = null;

        // Test data for inserting a new user
        String name = "Anna Test";
        String email = "anna@test.com";
        String phone = "0871111111";
        String address = "Dublin";
        String password = "pass123";

        try {
            // Establish connection to database
            connection = DatabaseConnection.getConnection();

            // SQL INSERT query with placeholders
            // Using placeholders prevents SQL Injection
            String sql = "INSERT INTO users (name, email, phone, address, password) " +
                         "VALUES (?, ?, ?, ?, ?)";

            // Prepare SQL statement
            pstat = connection.prepareStatement(sql);

            // Bind values to placeholders
            pstat.setString(1, name);
            pstat.setString(2, email);
            pstat.setString(3, phone);
            pstat.setString(4, address);
            pstat.setString(5, password);

            // Execute INSERT query
            int i = pstat.executeUpdate();

            // Print result
            System.out.println(i + " user record successfully added.");

        } catch (SQLException e) {
            // Handle SQL errors (e.g. duplicate email, connection issues)
            e.printStackTrace();

        } finally {
            // Close resources to prevent memory leaks
            try {
                if (pstat != null) pstat.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}