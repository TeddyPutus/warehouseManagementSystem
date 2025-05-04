package putus.teddy.data.entity;

import java.util.Map;

public abstract class DataEntity {
    public abstract void update(Map<String,Object> query);
    public abstract boolean matches(Map<String, Object> queryMap);

    public static void printTableHead(){};
    public abstract void printTableRow();

    int id;

    public int getId() {
        return id;
    }

}
