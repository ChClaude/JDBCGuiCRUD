package DbUtil;

import com.sun.istack.internal.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class DBUtils {

    private static String dbUrl = "jdbc:ucanaccess://C:\\Users\\Christ\\IdeaProjects\\JDBCGuiCRUD\\src\\AccessDB\\userDb.accdb";
    private static Connection conn = null;

    /**
     * This method creates a connection to the database
     *
     * @throws SQLException
     */
    public static void connectToDb() throws SQLException {
        conn = DriverManager.getConnection(dbUrl);
        System.out.println("Connection successful");
    }

    /**
     * This method creates a table in the database
     *
     * @param name       the name of the table to be created in the database
     * @param attributes this is a collection of key value pairs of attribute's names
     *                   and types of the table to be created.
     * @throws SQLException
     */
    public static void createTable(String name, @NotNull List<TableAttribute> attributes) throws SQLException {
        if (conn != null) {
            StringBuilder formatQuery = new StringBuilder("CREATE TABLE " + name + " ( ");

            for (TableAttribute attribute : attributes) {
                formatQuery.append(attribute.getName()).append(" ").append(attribute.getType()).append(", ");
            }

            // replacing the last occurrence of , to an empty string ""
            formatQuery = formatQuery.delete(formatQuery.lastIndexOf(", "), formatQuery.lastIndexOf(", ") + 1);
            formatQuery = formatQuery.append(");");

            Statement statement = conn.createStatement();

            String sql = formatQuery.toString();
            statement.executeUpdate(sql);
            System.out.println("query executed");
        }
    }

    /**
     * This method closes the connection to the database
     *
     * @throws SQLException
     */
    public static void closeConnection() throws SQLException {
        if (conn != null)
            conn.close();
        System.out.println("Connection closed");
    }

}
