import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteUser {

    public static void main(String[] args) {

        Connection connection = null;
        PreparedStatement pstat = null;

        int userId = 1;   // user's ID we want to delete

        try {
            // establish connection
            connection = DatabaseConnection.getConnection();

            // SQL delete statement
            String sql = "DELETE FROM users WHERE user_id = ?";

            // prepare statement
            pstat = connection.prepareStatement(sql);

            // set value
            pstat.setInt(1, userId);

            // execute delete
            int i = pstat.executeUpdate();

            System.out.println(i + " record successfully removed from the table.");

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
