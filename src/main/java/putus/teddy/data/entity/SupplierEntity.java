package putus.teddy.data.entity;

import java.util.Map;

public class SupplierEntity extends DataEntity {
    private static int idCounter=0;
    private String name;
    private String phoneNumber;
    private String email;
    private static final String columnWidth = "| %-10s | %12s | %-20s |";
    private static final String tableHead = String.format(columnWidth, "NAME", "PHONE", "EMAIL");

    public SupplierEntity(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.id = SupplierEntity.getIdCounter();

        SupplierEntity.incrementIdCounter();
    }

    public void update(Map<String, Object> query) {
        if (query.containsKey("name")) {
            this.name = (String) query.get("name");
        }
        if (query.containsKey("phoneNumber")) {
            this.phoneNumber = (String) query.get("phoneNumber");
        }
        if (query.containsKey("email")) {
            this.email = (String) query.get("email");
        }
    }

    public boolean matches(Map<String, Object> queryMap) {
        return queryMap.keySet().stream()
                .allMatch(key -> {
                    switch (key) {
                        case "id" -> {
                            if (!queryMap.get(key).equals(id)) {
                                return false;
                            }
                        }
                        case "name" -> {
                            if (!queryMap.get(key).equals(name)) {
                                return false;
                            }
                        }
                        case "phoneNumber" -> {
                            if (!queryMap.get(key).equals(phoneNumber)) {
                                return false;
                            }
                        }
                        case "email" -> {
                            if (!queryMap.get(key).equals(email)) {
                                return false;
                            }
                        }
                        default -> {
                            return false;
                        }
                    }
                    return true;
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

    public static void incrementIdCounter() {
        idCounter++;
    }

    public static int getIdCounter() {
        return idCounter;
    }

    public static void printTableHead() {
        System.out.println("-".repeat(tableHead.length()));
        System.out.println(tableHead);
        System.out.println("-".repeat(tableHead.length()));
    }

    @Override
    public void printTableRow() {
        System.out.printf(columnWidth + "\n", name, phoneNumber, email);
    }
}
