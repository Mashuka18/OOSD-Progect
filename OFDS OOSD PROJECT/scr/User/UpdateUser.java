import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateUser {

    public static void main(String[] args) {

        Connection connection = null;
        PreparedStatement pstat = null;

        // test data
        String newEmail = "anna_updated@test.com";
        int userId = 1;   // user's ID we want to update

        try {
            // establish connection
            connection = DatabaseConnection.getConnection();

            // SQL update statement
            String sql = "UPDATE users SET email = ? WHERE user_id = ?";

            // prepare statement
            pstat = connection.prepareStatement(sql);

            // set values
            pstat.setString(1, newEmail);
            pstat.setInt(2, userId);

            // execute update (1 or 0)
            int i = pstat.executeUpdate();

            System.out.println(i + " record successfully updated in the table.");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstat != null) pstat.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
