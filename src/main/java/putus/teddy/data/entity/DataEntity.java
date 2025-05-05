package putus.teddy.data.entity;

import java.util.Map;

public abstract class DataEntity {

    public abstract void update(Map<String,Object> query);
    public abstract boolean matches(Map<String, Object> queryMap);


    public abstract void printTableRow();

    public static boolean compareDoubles(double a, double b) {
        return Math.abs(a - b) < 0.0001;
    }

    public static void printTableHead(String tableHead) {
        System.out.println("-".repeat(tableHead.length()));
        System.out.println(tableHead);
        System.out.println("-".repeat(tableHead.length()));
    }
}
