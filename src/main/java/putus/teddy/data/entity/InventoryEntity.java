package putus.teddy.data.entity;

/**
 * InventoryEntity represents an item in the inventory.
 * It contains information about the item name, quantity, and price per unit.
 */
public class InventoryEntity implements DataEntity {
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

    public static String getTableHead() {
        return tableHead;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getTableRow() {
        return String.format(columnWidth + "\n", itemName, quantity, pricePerUnit);
    }

    public String getItemName() {
        return itemName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
