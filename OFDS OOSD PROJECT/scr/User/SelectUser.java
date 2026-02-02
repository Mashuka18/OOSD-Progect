import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectUser {

    public static void main(String[] args) {

        Connection connection = null; //(Connection)
        PreparedStatement pstat = null; //(Dispatch SQL)
        ResultSet rs = null; //rs contains all rows from the users table (Read)

        try {
            // establish connection
            connection = DatabaseConnection.getConnection();

            // SQL SELECT
            String sql = "SELECT * FROM users";

            // prepare statement
            pstat = connection.prepareStatement(sql);

            // execute query
            rs = pstat.executeQuery();

            System.out.println("Users in database:");
            //It reads each row from the database result.
            while (rs.next()) {
                int id = rs.getInt("user_id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");

                System.out.println(id + " | " + name + " | " + email + " | " + phone);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
