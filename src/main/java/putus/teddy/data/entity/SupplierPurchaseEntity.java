package putus.teddy.data.entity;

import java.util.UUID;

/**
 * SupplierPurchaseEntity represents a purchase made from a supplier.
 * It contains information about the supplier, the item purchased, quantity, price per unit, total price, and purchase date.
 */
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

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public String getItemName() {
        return itemName;
    }

    public Integer getQuantity() {
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
