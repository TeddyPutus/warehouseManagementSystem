package putus.teddy.data.entity;

import java.util.Map;
import java.util.UUID;

public class CustomerPurchaseEntity implements DataEntity{
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

    public String getCustomerName() {
        return customerName;
    }

    public String getItemName() {
        return itemName;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public Integer getQuantity() {
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

    public static String getTableHead() {
        return tableHead;
    }

    public String getTableRow() {
        return String.format(columnWidth + "\n", id, customerName, itemName, quantity, totalPrice, purchaseDate);
    }
}
