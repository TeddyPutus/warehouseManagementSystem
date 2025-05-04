package putus.teddy.data.entity;

import java.util.Map;

public class InventoryEntity extends DataEntity{
    private static int idCounter=0;
    private String itemName;
    private int quantity;
    private Double pricePerUnit;
    private static final String columnWidth = "| %-10s | %8s | %10s |";
    private static final String tableHead = String.format(columnWidth, "ITEM NAME", "QUANTITY", "PRICE/UNIT");

    public InventoryEntity(String itemName, int quantity, Double pricePerUnit) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.id = InventoryEntity.getIdCounter();

        InventoryEntity.incrementIdCounter();
    }

    public void update(Map<String, Object> query) {
        if (query.containsKey("itemName")) {
            this.itemName = (String) query.get("itemName");
        }
        if (query.containsKey("quantity")) {
            this.quantity = (int) query.get("quantity");
        }
        if (query.containsKey("pricePerUnit")) {
            this.pricePerUnit = (Double) query.get("pricePerUnit");
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
                        case "itemName" -> {
                            if (!queryMap.get(key).equals(itemName)) {
                                return false;
                            }
                        }
                        case "quantity" -> {
                            if (!queryMap.get(key).equals(quantity)) {
                                return false;
                            }
                        }
                        case "pricePerUnit" -> {
                            if (!compareDoubles((double) queryMap.get(key), pricePerUnit)) {
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
    public static int getIdCounter() {
        return idCounter;
    }
    public static void incrementIdCounter() {
        InventoryEntity.idCounter++;
    }

    public static void printTableHead() {
        System.out.println("-".repeat(tableHead.length()));
        System.out.println(tableHead);
        System.out.println("-".repeat(tableHead.length()));
    }

    @Override
    public void printTableRow() {
        System.out.printf(columnWidth + "\n", itemName, quantity, pricePerUnit);
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPricePerUnit() {
        return pricePerUnit;
    }
}
