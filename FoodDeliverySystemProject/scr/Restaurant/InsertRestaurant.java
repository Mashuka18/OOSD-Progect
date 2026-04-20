package Restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DB.DatabaseConnection;

/**
 * This class demonstrates how to insert a new restaurant
 * record into the database using JDBC.
 */
public class InsertRestaurant {

    /**
     * Main method that connects to the database and inserts
     * a new restaurant using test data.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        // Database connection object
        Connection connection = null;

        // PreparedStatement for SQL execution
        PreparedStatement pstat = null;

        // Test data for new restaurant
        String name = "Dominos Pizza";     // Restaurant name
        String address = "Dublin City";    // Restaurant address
        String phone = "012345678";        // Restaurant phone number

        try {
            // Establish connection to database
            connection = DatabaseConnection.getConnection();

            // SQL INSERT query with placeholders
            String sql = "INSERT INTO restaurants (name, address, phone) VALUES (?, ?, ?)";

            // Prepare SQL statement
            pstat = connection.prepareStatement(sql);

            // Set parameter values
            pstat.setString(1, name);
            pstat.setString(2, address);
            pstat.setString(3, phone);

            // Execute INSERT operation
            int i = pstat.executeUpdate();

            // Output result (number of rows inserted)
            System.out.println(i + " restaurant added.");

        } catch (SQLException e) {
            // Handle SQL errors
            e.printStackTrace();

        } finally {
            // Close database resources to prevent memory leaks
            try {
                if (pstat != null) pstat.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                // Handle closing errors
                e.printStackTrace();
            }
        }
    }
}