package Restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DB.DatabaseConnection;

/**
 * This class demonstrates how to retrieve and display
 * all restaurant records from the database.
 */
public class SelectRestaurants {

    /**
     * Main method that connects to the database and retrieves
     * all restaurants from the restaurants table.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        // Database connection object
        Connection connection = null;

        // PreparedStatement for SQL execution
        PreparedStatement pstat = null;

        // ResultSet to store query results
        ResultSet rs = null;

        try {
            // Establish connection to database
            connection = DatabaseConnection.getConnection();

            // SQL SELECT query
            pstat = connection.prepareStatement("SELECT * FROM restaurants");

            // Execute query
            rs = pstat.executeQuery();

            // Iterate through all results
            while (rs.next()) {
                System.out.println(
                    rs.getInt("restaurant_id") + " | " +  // Restaurant ID
                    rs.getString("name") + " | " +        // Restaurant name
                    rs.getString("address")               // Address
                );
            }

        } catch (Exception e) {
            // Handle errors
            e.printStackTrace();

        } finally {
            // Close resources to prevent memory leaks
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