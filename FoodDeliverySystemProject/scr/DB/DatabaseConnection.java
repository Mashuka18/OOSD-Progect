package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class responsible for establishing a connection
 * to the MySQL database for the online food delivery system.
 */
public class DatabaseConnection {

    // Database connection URL (points to local MySQL database)
    private static final String URL =
        "jdbc:mysql://localhost:3306/online_food_delivery?useSSL=false&serverTimezone=UTC";

    // Database username
    private static final String USER = "root";

    // Database password
    private static final String PASSWORD = "V@na-gu49947@7";

    /**
     * Establishes and returns a connection to the database.
     *
     * @return a {@link Connection} object if successful; null otherwise
     */
    public static Connection getConnection() {
        try {
            // Attempt to create a connection using provided credentials
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            // Handle connection failure
            System.out.println("Database connection failed.");
            e.printStackTrace();
            return null; // Return null if connection could not be established
        }
    }
}