import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertPayment {

    public static void main(String[] args) {

        Connection connection = null;
        PreparedStatement pstat = null;

        // Test payment data
        int orderId = 1;
        double amount = 18.99;
        String paymentMethod = "Card";
        String paymentStatus = "Paid";

        try {
            // Connect to database
            connection = DatabaseConnection.getConnection();

            // SQL INSERT statement
            String sql = "INSERT INTO payments (order_id, amount, payment_method, payment_status) " +
                         "VALUES (?, ?, ?, ?)";

            // Prepare SQL
            pstat = connection.prepareStatement(sql);

            // Set values
            pstat.setInt(1, orderId);
            pstat.setDouble(2, amount);
            pstat.setString(3, paymentMethod);
            pstat.setString(4, paymentStatus);

            // Execute INSERT
            int i = pstat.executeUpdate();
            System.out.println(i + " payment recorded.");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (pstat != null) pstat.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
