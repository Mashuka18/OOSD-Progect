package Restaurant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DB.DatabaseConnection;

/**
 * Data Access Object (DAO) class for handling database operations
 * related to Restaurant entities.
 */
public class RestaurantDAO {

    /**
     * Inserts a new restaurant into the database.
     *
     * @param name the restaurant name
     * @param address the restaurant address
     * @param phone the restaurant phone number
     * @return true if insertion was successful, false otherwise
     */
    public boolean insertRestaurant(String name, String address, String phone) {

        // SQL INSERT query
        String sql = "INSERT INTO restaurants (name, address, phone) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set parameters
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, phone);

            // Execute insert
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            // Handle database errors
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Retrieves all restaurants from the database.
     *
     * @return a list of Restaurant objects
     */
    public List<Restaurant> getAllRestaurants() {

        // List to store restaurants
        List<Restaurant> list = new ArrayList<>();

        try {
            // Connect to database
            Connection conn = DatabaseConnection.getConnection();

            // SQL SELECT query
            String sql = "SELECT * FROM restaurants";

            // Prepare statement
            PreparedStatement ps = conn.prepareStatement(sql);

            // Execute query
            ResultSet rs = ps.executeQuery();

            // Process results
            while (rs.next()) {
                list.add(new Restaurant(
                    rs.getInt("restaurant_id"),   // ID
                    rs.getString("name"),         // Name
                    rs.getString("description"),  // Description
                    rs.getDouble("rating"),       // Rating
                    rs.getString("image")         // Image
                ));
            }

        } catch (Exception e) {
            // Handle errors
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Retrieves restaurant ID based on its name.
     * Useful for linking restaurants with menu items.
     *
     * @param name the restaurant name
     * @return restaurant ID if found, otherwise -1
     */
    public int getRestaurantIdByName(String name) {

        // SQL query to find restaurant by name
        String sql = "SELECT restaurant_id FROM restaurants WHERE name=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set parameter
            ps.setString(1, name);

            // Execute query
            ResultSet rs = ps.executeQuery();

            // Return ID if found
            if (rs.next()) {
                return rs.getInt("restaurant_id");
            }

        } catch (SQLException e) {
            // Handle errors
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * Updates restaurant address and phone number.
     *
     * @param id the restaurant ID
     * @param address the new address
     * @param phone the new phone number
     * @return true if update was successful, false otherwise
     */
    public boolean updateRestaurant(int id, String address, String phone) {

        // SQL UPDATE query
        String sql = "UPDATE restaurants SET address=?, phone=? WHERE restaurant_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set parameters
            ps.setString(1, address);
            ps.setString(2, phone);
            ps.setInt(3, id);

            // Execute update
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            // Handle errors
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Deletes a restaurant from the database.
     *
     * @param id the restaurant ID
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteRestaurant(int id) {

        // SQL DELETE query
        String sql = "DELETE FROM restaurants WHERE restaurant_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set ID parameter
            ps.setInt(1, id);

            // Execute delete
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            // Handle errors
            e.printStackTrace();
        }

        return false;
    }
}