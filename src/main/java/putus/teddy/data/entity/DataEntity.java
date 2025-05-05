package putus.teddy.data.entity;

import java.util.Map;

public interface DataEntity {

    void update(Map<String,Object> query);
    boolean matches(Map<String, Object> queryMap);

    static boolean compareDoubles(double a, double b) {
        return Math.abs(a - b) < 0.0001;
    }

    static String getTableHead(){return "";};
    String getTableRow();
}
