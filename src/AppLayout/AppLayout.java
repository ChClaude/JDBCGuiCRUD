package AppLayout;

import DbUtil.DBUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * AppLayout.java
 * This is the class that creates the layout
 * of the application
 *
 * @author Claude C DE-TCHAMBILA
 * student number: 217035027
 */
public class AppLayout {

    private static int index = 0;
    Map<String, String> tableAttributesAndTypes;
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JPanel createTable;
    private JTextField tableNameTextField;
    private JTextField tableAttributeName;
    private JLabel promptTableNameLabel;
    private JLabel tableAttributesNameLabel;
    private JComboBox attributesValueCapacity;
    private JList recordsList;
    private JTextField tableAttributeName1;
    private JTextField tableAttributeName2;
    private JTextField tableAttributeName3;
    private JTextField tableAttributeName4;
    private JTextField tableAttributeName5;
    private JTextField tableAttributeName6;
    private JTextField tableAttributeName7;
    private JTextField tableAttributeName8;
    private JComboBox attributesValueCapacity1;
    private JComboBox attributesValueCapacity2;
    private JComboBox attributesValueCapacity3;
    private JComboBox attributesValueCapacity4;
    private JComboBox attributesValueCapacity5;
    private JComboBox attributesValueCapacity6;
    private JComboBox attributesValueCapacity7;
    JComboBox[] attributesValueCapacities = {attributesValueCapacity1, attributesValueCapacity2, attributesValueCapacity3,
            attributesValueCapacity4, attributesValueCapacity5, attributesValueCapacity6, attributesValueCapacity7
    };
    private JComboBox attributesValueCapacity8;
    private JButton addMoreFieldsBtn;
    private JButton createTableButton;
    private JButton cancelButton;
    private JTextField[] tableAttributeNames = {tableAttributeName1, tableAttributeName2, tableAttributeName3,
            tableAttributeName4, tableAttributeName5, tableAttributeName6, tableAttributeName7
    };

    // constructor
    public AppLayout() throws SQLException {
        DBUtils.connectToDb();

        tableAttributesAndTypes = new HashMap<>();

        addMoreFieldsBtn.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {
                if (index < attributesValueCapacities.length) {
                    tableAttributeNames[index].setVisible(true);
                    attributesValueCapacities[index++].setVisible(true);
                }
            }
        });
        createTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableAttributesAndTypes.clear();

                HashMap<String, String> tempMapTableAttributesAndTypes = new HashMap<>();

                if (!tableNameTextField.getText().isEmpty() && !tableAttributeName.getText().isEmpty() && !tableAttributeName8.getText().isEmpty()) {
                    tempMapTableAttributesAndTypes.put(tableAttributeName.getText(), attributesValueCapacity.getSelectedItem().toString());
                    tempMapTableAttributesAndTypes.put(tableAttributeName8.getText(), attributesValueCapacity8.getSelectedItem().toString());
                } else {
                    JOptionPane.showMessageDialog(null, "Some required fields are empty", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int length = tableAttributeNames.length;
                for (int i = 0; i < length; i++) {
                    if (!tableAttributeNames[i].getText().isEmpty()) {
                        tempMapTableAttributesAndTypes.put(tableAttributeNames[i].getText(), attributesValueCapacities[i].getSelectedItem().toString());
                    }
                }

                // reversing the map to suit the entries order in the db table
                for (String attributeName : tempMapTableAttributesAndTypes.keySet())
                    tableAttributesAndTypes.put(attributeName, tempMapTableAttributesAndTypes.get(attributeName));


                System.out.println(tableAttributesAndTypes);

                try {
                    DBUtils.createTable(getTableName(), getTableAttributesAndTypes());
                } catch (SQLException ex) {
                    System.err.println("Error: " + ex.getMessage());
                    System.err.println("Error code: " + ex.getErrorCode());
                }
            }
        });
    }

    /**
     * This method creates the layout for the application
     *
     * @param title this is the title of the frame
     * @throws SQLException
     */
    public static void createLayout(String title) throws SQLException {
        JFrame frame = new JFrame(title);
        frame.setContentPane(new AppLayout().mainPanel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    DBUtils.closeConnection();
                    System.exit(0);
                } catch (SQLException ex) {
                    System.err.println("Error: " + ex.getMessage());
                    System.err.println("Error code: " + ex.getErrorCode());
                }
            }
        });
    }

    /**
     * This method returns the attributes and types of attributes of the table
     * to be created
     *
     * @return Map Collection the attributes and their types in key pair values
     */
    public Map<String, String> getTableAttributesAndTypes() {
        return tableAttributesAndTypes;
    }

    /**
     * This method returns the value of the table name that the user inputs
     *
     * @return the table name from the value the user input, otherwise returns null.
     */
    public String getTableName() {
        String tableNameValue = tableNameTextField.getText().toString();

        if (tableNameValue.isEmpty())
            return null;

        return tableNameValue;
    }
}
