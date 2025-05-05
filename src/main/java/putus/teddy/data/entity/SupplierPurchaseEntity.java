package putus.teddy.data.entity;

import java.util.Map;
import java.util.UUID;

public class SupplierPurchaseEntity implements DataEntity {
    public enum Status {
        PENDING,
        DELIVERED,
    }

    private final String id = UUID.randomUUID().toString();
    private String supplierName;
    private String itemName;
    private Integer quantity;
    private Double pricePerUnit;
    private Status status;
    private Double totalPrice;
    private String purchaseDate;

    private static final String columnWidth = "| %-36s | %-11s | %-11s | %-20s | %8s | %10s | %11s |";
    private static final String tableHead = String.format(columnWidth, "ID", "SUPPLIER", "DATE", "ITEM NAME", "QUANTITY", "PRICE/UNIT", "TOTAL PRICE");

    public SupplierPurchaseEntity(String supplierName, String purchaseDate, String itemName, Integer quantity, Double pricePerUnit) {
        this.supplierName = supplierName;
        this.purchaseDate = purchaseDate;
        this.itemName = itemName;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.totalPrice = quantity * pricePerUnit;
        this.status = Status.PENDING;
    }

    public void update(Map<String, Object> query) {
        query.forEach((key, value) -> {
            switch (key) {
                case "supplierName" -> this.supplierName = (String) value;
                case "purchaseDate" -> this.purchaseDate = (String) value;
                case "itemName" -> this.itemName = (String) value;
                case "quantity" -> this.quantity = (Integer) value;
                case "pricePerUnit" -> this.pricePerUnit = (Double) value;
                case "status" -> this.status = (Status) value;
            }
        });

        totalPrice = quantity * pricePerUnit;
    }

    public boolean matches(Map<String, Object> queryMap) {
        return queryMap.entrySet().stream()
                .allMatch(entry -> {
                    String key = entry.getKey();
                    Object value = entry.getValue();

                    return switch (key) {
                        case "id" -> id.equals(value);
                        case "supplierName" -> supplierName.equals(value);
                        case "purchaseDate" -> purchaseDate.equals(value);
                        case "itemName" -> itemName.equals(value);
                        case "quantity" -> quantity.equals(value);
                        case "pricePerUnit" -> value instanceof Double && DataEntity.compareDoubles(pricePerUnit, (Double) value);
                        case "totalPrice" -> value instanceof Double && DataEntity.compareDoubles(totalPrice, (Double) value);
                        default -> false;
                    };
                });
    }

    public static String getTableHead() {
        return tableHead;
    }

    public String getTableRow() {
        return String.format(columnWidth + "\n", id, supplierName, purchaseDate, itemName, quantity, pricePerUnit, totalPrice);
    }

    public String getId() {
        return id;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
