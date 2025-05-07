package putus.teddy.data.entity;

import java.util.Map;
import java.util.UUID;

public class SupplierEntity implements DataEntity {
    private final String id = UUID.randomUUID().toString();
    private String name;
    private String phoneNumber;
    private String email;
    private static final String columnWidth = "| %-36s | %-15s | %12s | %-20s |";
    private static final String tableHead = String.format(columnWidth, "ID", "NAME", "PHONE", "EMAIL");

    public SupplierEntity(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public static String getTableHead() {
        return tableHead;
    }
    public String getTableRow() {
        return String.format(columnWidth + "\n", id, name, phoneNumber, email);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public void setEmail(String email) {
        this.email = email;
    }
}
