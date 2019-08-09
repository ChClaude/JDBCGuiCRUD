package DbUtil;

/**
 * TableAttribute.java
 *
 * This class defines the attribute of a relational database table
 *
 * @author Claude DE-TCHAMBILA
 * student number: 217035027
 */
public class TableAttribute {

    private String name;
    private String type;

    public TableAttribute(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "TableAttribute {" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
