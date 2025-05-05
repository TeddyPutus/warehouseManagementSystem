package putus.teddy.data.entity;

import java.util.Map;

public class InventoryEntity implements DataEntity{
    private String itemName;
    private Integer quantity;
    private Double pricePerUnit;
    private static final String columnWidth = "| %-10s | %8s | %10s |";
    private static final String tableHead = String.format(columnWidth, "ITEM NAME", "QUANTITY", "PRICE/UNIT");

    public InventoryEntity(String itemName, Integer quantity, Double pricePerUnit) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
    }

    public void update(Map<String, Object> query) {
        query.forEach((key, value) -> {
            switch (key) {
                case "itemName" -> this.itemName = (String) value;
                case "quantity" -> this.quantity = (Integer) value;
                case "pricePerUnit" -> this.pricePerUnit = (Double) value;
            }
        });
    }

    public boolean matches(Map<String, Object> queryMap) {
        return queryMap.entrySet().stream()
                .allMatch(entry -> {
                    String key = entry.getKey();
                    Object value = entry.getValue();

                    return switch (key) {
                        case "itemName" -> itemName.equals(value);
                        case "quantity" -> quantity.equals(value);
                        case "pricePerUnit" -> value instanceof Double && DataEntity.compareDoubles(pricePerUnit, (Double) value);
                        default -> false;
                    };
                });
    }

    public static String getTableHead() {
        return tableHead;
    }

    public String getTableRow() {
        return String.format(columnWidth + "\n", itemName, quantity, pricePerUnit);
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
