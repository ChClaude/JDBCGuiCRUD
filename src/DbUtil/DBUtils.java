package DbUtil;

import com.sun.istack.internal.NotNull;

import java.sql.*;
import java.util.ArrayList;
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
     * This method returns the list of tables in the database
     * @return list of tables in the database
     * @throws SQLException
     */
    public static List<String> retrieveDbTables() throws SQLException {
        List<String> tablesList = new ArrayList<>();

        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet resultSet= metaData.getTables(null, null, "%", null);


        while (resultSet.next())
            tablesList.add(resultSet.getString(3));

        return tablesList;
    }

    public static List<TableColumn> readRecords(String tableName) throws SQLException {
        Statement statement = conn.createStatement();
        String query = "SELECT * FROM " + tableName;
        ResultSet resultSet = statement.executeQuery(query);

        List<TableColumn> tableColumns= new ArrayList<>();

        for (String colName : DBUtils.retrieveDbTableColumns(tableName)) {
            tableColumns.add(new TableColumn(colName));
        }

        while (resultSet.next()) {
            for (TableColumn column : tableColumns) {
                column.getColumnData().add(resultSet.getString(column.getColumnName()));;
            }
        }
            return tableColumns;
    }

    /**
     * This method retrieves the columns of the specified table
     * from the database
     * @param tableName name of the table to retrieve the columns from
     * @return list of the columns in the table
     * @throws SQLException
     */
    public static List<String> retrieveDbTableColumns(String tableName) throws SQLException {
        List<String> columns = new ArrayList<>();
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet set = metaData.getColumns(null, null, tableName, "%");

        while (set.next())
            columns.add(set.getString(4));

        return columns;
    }


    public static void insert(String tableName, List<String> values) throws SQLException {
        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO " + tableName + " VALUES ( ");

        for (String value : values) {
            sqlBuilder.append(value).append(", ");
        }

        sqlBuilder = sqlBuilder.delete(sqlBuilder.lastIndexOf(", "), sqlBuilder.lastIndexOf(", ") + 1);
        sqlBuilder.append(");");

        String sql = sqlBuilder.toString();

        PreparedStatement statement = conn.prepareStatement(sql);

        statement.executeQuery();
    }

    public void update(String tableName, String id, String idValue, List<String> values) throws SQLException {
        StringBuilder sqlBuilder = new StringBuilder("UPDATE " + tableName + " SET ");

        for (String value : values) {
            sqlBuilder.append(value).append(", ");
        }

        sqlBuilder = sqlBuilder.delete(sqlBuilder.lastIndexOf(", "), sqlBuilder.lastIndexOf(", ") + 1);
        sqlBuilder.append("WHERE ").append(id).append(" = ").append(idValue).append(" );");

        String sql = sqlBuilder.toString();

        PreparedStatement statement = conn.prepareStatement(sql);

        statement.executeQuery();
    }

    /**
     * This method deletes records in the database
     *
     * @param tableName name of the table to delete records from
     * @param id key to identify row of record to be deleted
     * @param idValue value of key to identify
     * @throws SQLException
     */
    public static void delete(String tableName, String id, String idValue) throws SQLException {
        String sql = "DELETE FROM " + tableName + " WHERE " + id + " = " + idValue;

        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);
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
