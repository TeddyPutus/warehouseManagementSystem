package putus.teddy.data.entity;

import java.util.Map;

public class CustomerPurchaseEntity extends DataEntity{
    private static int idCounter=0;
    private String customerName;
    private String itemName;
    private int quantity;
    private Double totalPrice;
    private String purchaseDate;
    private static final String columnWidth = "| %-15s | %-20s | %11s | %11s | %-20s |";
    private static final String tableHead = String.format(columnWidth, "CUSTOMER NAME", "PURCHASED ITEM", "QUANTITY", "TOTAL PRICE", "PURCHASE DATE");

    public CustomerPurchaseEntity(String customerName, String itemName, int quantity, String purchaseDate) {
        this.customerName = customerName;
        this.itemName = itemName;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
        this.totalPrice = 0.0;
        this.id = CustomerPurchaseEntity.getIdCounter();

        CustomerPurchaseEntity.incrementIdCounter();
    }

    public void update(Map<String, Object> query) {
        if (query.containsKey("customerName")) {
            this.customerName = (String) query.get("customerName");
        }
        if (query.containsKey("itemName")) {
            this.itemName = (String) query.get("itemName");
        }
        if (query.containsKey("totalPrice")) {
            this.totalPrice = (Double) query.get("totalPrice");
        }
        if (query.containsKey("purchaseDate")) {
            this.purchaseDate = (String) query.get("purchaseDate");
        }
        if (query.containsKey("quantity")) {
            this.quantity = (int) query.get("quantity");
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
                        case "customerName" -> {
                            if (!queryMap.get(key).equals(customerName)) {
                                return false;
                            }
                        }
                        case "itemName" -> {
                            if (!queryMap.get(key).equals(itemName)) {
                                return false;
                            }
                        }
                        case "totalPrice" -> {
                            if (!(queryMap.get(key) instanceof Double) || !compareDoubles((double) queryMap.get(key), totalPrice)){
                                return false;
                            }
                        }
                        case "purchaseDate" -> {
                            if (!queryMap.get(key).equals(purchaseDate)) {
                                return false;
                            }
                        }
                        case "quantity" -> {
                            if (!queryMap.get(key).equals(quantity)) {
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

    public static int getIdCounter() {
        return idCounter;
    }

    public static void incrementIdCounter() {
        idCounter++;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public static void printTableHead() {
        System.out.println("-".repeat(tableHead.length()));
        System.out.println(tableHead);
        System.out.println("-".repeat(tableHead.length()));
    }

    @Override
    public void printTableRow() {
        System.out.printf(columnWidth + "\n", customerName, itemName, quantity, totalPrice, purchaseDate);
    }
}
