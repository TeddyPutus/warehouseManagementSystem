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

    public void update(Map<String, Object> query) {
        query.forEach((key, value) -> {
            switch (key) {
                case "name" -> this.name = (String) value;
                case "phoneNumber" -> this.phoneNumber = (String) value;
                case "email" -> this.email = (String) value;
            }
        });
    }

    public boolean matches(Map<String, Object> queryMap) {
        return queryMap.entrySet().stream()
                .allMatch(entry -> {
                    String key = entry.getKey();
                    Object value = entry.getValue();

                    return switch (key) {
                        case "id" -> id.equals(value);
                        case "name" -> name.equals(value);
                        case "phoneNumber" -> phoneNumber.equals(value);
                        case "email" -> email.equals(value);
                        default -> false;
                    };
                });
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
}
