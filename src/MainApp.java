import AppLayout.AppLayout;
import DbUtil.DBUtils;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * MainApp.java
 * This is the main app of the application
 *
 * @author Claude C DE-TCHAMBILA
 * student number: 217035027
 */
public class MainApp {

    public static void main(String[] args) throws SQLException {
        AppLayout.createLayout("Manage Database");
    }

}
