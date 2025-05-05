package putus.teddy.data.entity;

import java.util.Map;
import java.util.UUID;

public class CustomerPurchaseEntity extends DataEntity{
    private final String id = UUID.randomUUID().toString();
    private String customerName;
    private String itemName;
    private Integer quantity;
    private Double totalPrice;
    private String purchaseDate;
    private static final String columnWidth = "| %-36s | %-15s | %-20s | %11s | %11s | %-20s |";
    private static final String tableHead = String.format(columnWidth, "ID", "CUSTOMER NAME", "PURCHASED ITEM", "QUANTITY", "TOTAL PRICE", "PURCHASE DATE");

    public CustomerPurchaseEntity(String customerName, String itemName, int quantity, String purchaseDate) {
        this.customerName = customerName;
        this.itemName = itemName;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
        this.totalPrice = 0.0;
    }

    public void update(Map<String, Object> query) {
        query.forEach((key, value) -> {
            switch (key) {
                case "customerName" -> this.customerName = (String) value;
                case "itemName" -> this.itemName = (String) value;
                case "totalPrice" -> this.totalPrice = (Double) value;
                case "purchaseDate" -> this.purchaseDate = (String) value;
                case "quantity" -> this.quantity = (Integer) value;
            }
        });
    }

    public boolean matches(Map<String, Object> queryMap) {
        return queryMap.entrySet().stream()
                .allMatch(entry -> {
                    String key = entry.getKey();
                    Object value = entry.getValue();

                    return switch (key) {
                        case "customerName" -> customerName.equals(value);
                        case "itemName" -> itemName.equals(value);
                        case "totalPrice" -> value instanceof Double && DataEntity.compareDoubles(totalPrice, (Double) value);
                        case "purchaseDate" -> purchaseDate.equals(value);
                        case "quantity" -> quantity.equals(value);
                        default -> false;
                    };
                });
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getItemName() {
        return itemName;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getId() {
        return id;
    }

    public static void printTableHead() {
        DataEntity.printTableHead(tableHead);
    }

    public void printTableRow() {
        System.out.printf(columnWidth + "\n", id, customerName, itemName, quantity, totalPrice, purchaseDate);
    }
}
