package DbUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * TableColumn.java
 * This class defines the model of a table column
 * in the database
 *
 * @author Claude DE-TCHAMBILA
 * student number: 217035027
 */
public class TableColumn {

    private String columnName;
    public List<String> columnData;

    public TableColumn(String columnName) {
        this.columnName = columnName;

        columnData = new ArrayList<>();
    }

    public String getColumnName() {
        return columnName;
    }

    public List<String> getColumnData() {
        return columnData;
    }

    public void setColumnData(List<String> columnData) {
        this.columnData = columnData;
    }

}
