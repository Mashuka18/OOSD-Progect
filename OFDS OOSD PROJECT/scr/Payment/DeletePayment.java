import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeletePayment {

    public static void main(String[] args) {

        // Object to store database connection
        Connection connection = null;

        // PreparedStatement for SQL DELETE
        PreparedStatement pstat = null;

        // ID of the payment to be deleted (example)
        int paymentId = 1;

        try {
            // Connect to the database
            connection = DatabaseConnection.getConnection();

            // SQL DELETE statement with placeholder
            String sql = "DELETE FROM payments WHERE payment_id = ?";

            // Prepare the SQL statement
            pstat = connection.prepareStatement(sql);

            // Set the payment ID value
            pstat.setInt(1, paymentId);

            // Execute DELETE operation
            int i = pstat.executeUpdate();

            // Output result
            System.out.println(i + " payment deleted.");

        } catch (SQLException e) {
            // Handle SQL errors
            e.printStackTrace();
        } finally {
            // Close database resources
            try {
                if (pstat != null) pstat.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
