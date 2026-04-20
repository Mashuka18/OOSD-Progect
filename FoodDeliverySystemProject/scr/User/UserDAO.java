package User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.DatabaseConnection;

/**
 * UserDAO handles all database operations related to the users table.
 * Includes CRUD operations such as insert, login, update, delete, and read methods.
 */
public class UserDAO {

    // CREATE (REGISTER USER)

    /**
     * Inserts a new user into the database.
     *
     * @param name     full name of user
     * @param email    user email (unique)
     * @param phone    phone number
     * @param address  address (optional)
     * @param password user password
     * @return true if insertion is successful, false otherwise
     */
    public boolean insertUser(String name, String email, String phone, String address, String password) {
        String sql = "INSERT INTO users (name, email, phone, address, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // set query parameters
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, address);
            ps.setString(5, password);

            // execute insert query
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // LOGIN (AUTH CHECK)

    /**
     * Validates user login credentials.
     *
     * @param email    user email
     * @param password user password
     * @return true if credentials are valid
     */
    public boolean loginUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE email=? AND password=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // bind login parameters
            ps.setString(1, email);
            ps.setString(2, password);

            // execute query
            ResultSet rs = ps.executeQuery();

            // returns true if user exists
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // READ (GET USER ID)

    /**
     * Retrieves user ID based on email.
     *
     * @param email user email
     * @return user ID or -1 if not found
     */
    public int getUserIdByEmail(String email) {
        String sql = "SELECT user_id FROM users WHERE email=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("user_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // READ (ALL USERS)

    /**
     * Retrieves list of all user names.
     *
     * @return list of user names
     */
    public List<String> getAllUsers() {
        List<String> users = new ArrayList<>();
        String sql = "SELECT name FROM users";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    // UPDATE (EMAIL ONLY)
    /**
     * Updates user's email.
     *
     * @param userId   user ID
     * @param newEmail new email address
     * @return true if update successful
     */
    public boolean updateUserEmail(int userId, String newEmail) {
        String sql = "UPDATE users SET email=? WHERE user_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newEmail);
            ps.setInt(2, userId);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // DELETE USER

    /**
     * Deletes a user from database.
     *
     * @param userId user ID to delete
     * @return true if deletion successful
     */
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // UPDATE ADDRESS

    /**
     * Updates full user address information.
     */
    public boolean updateUserAddress(int userId, String eircode, String street, String house) {
        String sql = "UPDATE users SET eircode=?, street=?, house_number=? WHERE user_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, eircode);
            ps.setString(2, street);
            ps.setString(3, house);
            ps.setInt(4, userId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // READ ADDRESS

    /**
     * Returns formatted user address.
     */
    public String getUserAddress(int userId) {
        String sql = "SELECT eircode, street, house_number FROM users WHERE user_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("eircode") + ", " +
                        rs.getString("street") + ", " +
                        rs.getString("house_number");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //  READ USER DETAILS
    /**
     * Retrieves basic user details.
     *
     * @return array containing name, email, phone
     */
    public String[] getUserDetails(int userId) {
        String[] data = new String[3]; // [0]=name, [1]=email, [2]=phone

        try {
            Connection conn = DatabaseConnection.getConnection();

            String sql = "SELECT name, email, phone FROM users WHERE user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                data[0] = rs.getString("name");
                data[1] = rs.getString("email");
                data[2] = rs.getString("phone");
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    // UPDATE FULL USER INFO

    /**
     * Updates name, email, and phone of user.
     */
    public boolean updateUserFull(int id, String name, String email, String phone) {
        try {
            Connection conn = DatabaseConnection.getConnection();

            String sql = "UPDATE users SET name=?, email=?, phone=? WHERE user_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setInt(4, id);

            int i = ps.executeUpdate();

            ps.close();
            conn.close();

            return i > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}