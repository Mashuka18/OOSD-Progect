import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SelectRestaurants {

    public static void main(String[] args) {

        Connection connection = null;//(Connection)
        PreparedStatement pstat = null; //(Dispatch SQL)
        ResultSet rs = null; //(READ)

        try {
            // establish connection
            connection = DatabaseConnection.getConnection();
            // SQL SELECT
            pstat = connection.prepareStatement("SELECT * FROM restaurants");
            // execute query
            rs = pstat.executeQuery();

            //rs.next(): Moves the cursor to the next row
            //Returns true as long as there are rows
            while (rs.next()) {
                System.out.println(
                    rs.getInt("restaurant_id") + " | " +
                    rs.getString("name") + " | " +
                    rs.getString("address")
                );
            }

        } catch (Exception e) {
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
