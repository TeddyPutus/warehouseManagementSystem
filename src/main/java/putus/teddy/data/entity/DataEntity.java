package putus.teddy.data.entity;

public interface DataEntity {
    static boolean compareDoubles(double a, double b) {
        return Math.abs(a - b) < 0.0001;
    }

    static String getTableHead(){return "";};
    String getTableRow();
}
