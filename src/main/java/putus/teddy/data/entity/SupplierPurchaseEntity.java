package putus.teddy.data.entity;

import java.util.Map;

public class SupplierPurchaseEntity extends DataEntity{
    private static int idCounter=0;
    private String supplierName;
    private String itemName;
    private int quantity;
    private Double pricePerUnit;
    private String status;
    private Double totalPrice;
    private String purchaseDate;

    private static final String columnWidth = "| %-11s | %-11s | %-20s | %8s | %10s | %11s |";
    private static final String tableHead = String.format(columnWidth, "SUPPLIER", "DATE", "ITEM NAME", "QUANTITY", "PRICE/UNIT", "TOTAL PRICE");

    public SupplierPurchaseEntity(String supplierName, String purchaseDate, String itemName, int quantity, Double pricePerUnit) {
        this.supplierName = supplierName;
        this.purchaseDate = purchaseDate;
        this.itemName = itemName;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.totalPrice = quantity * pricePerUnit;
        this.status = "Pending";
        this.id = SupplierPurchaseEntity.getIdCounter();

        SupplierPurchaseEntity.incrementIdCounter();
    }

    public void update(Map<String, Object> query) {
        if (query.containsKey("supplierName")) {
            this.supplierName = (String) query.get("supplierName");
        }
        if (query.containsKey("purchaseDate")) {
            this.purchaseDate = (String) query.get("purchaseDate");
        }
        if (query.containsKey("itemName")) {
            this.itemName = (String) query.get("itemName");
        }
        if (query.containsKey("quantity")) {
            this.quantity = (int) query.get("quantity");
        }
        if (query.containsKey("pricePerUnit")) {
            this.pricePerUnit = (Double) query.get("pricePerUnit");
        }
        if (query.containsKey("status")) {
            this.status = (String) query.get("status");
        }
        totalPrice = quantity * pricePerUnit;
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
                        case "supplierName" -> {
                            if (!queryMap.get(key).equals(supplierName)) {
                                return false;
                            }
                        }
                        case "purchaseDate" -> {
                            if (!queryMap.get(key).equals(purchaseDate)) {
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
                        case "totalPrice" -> {
                            if (!compareDoubles((double) queryMap.get(key), totalPrice)) {
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
        idCounter++;
    }

    public static void printTableHead() {
        System.out.println("-".repeat(tableHead.length()));
        System.out.println(tableHead);
        System.out.println("-".repeat(tableHead.length()));
    }

    @Override
    public void printTableRow() {
        System.out.printf(columnWidth + "\n", supplierName, purchaseDate, itemName, quantity, pricePerUnit, totalPrice);
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
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
