package putus.teddy.data.entity;

/**
 * DataEntity interface represents a generic data entity.
 * It provides methods to compare double values and get table headers and rows.
 */
public interface DataEntity {
    static boolean compareDoubles(double a, double b) {
        return Math.abs(a - b) < 0.0001;
    }

    static String getTableHead(){return "";};
    String getTableRow();
}
