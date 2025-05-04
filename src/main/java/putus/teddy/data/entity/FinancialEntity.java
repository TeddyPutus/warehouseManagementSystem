package putus.teddy.data.entity;

import java.util.Map;

public class FinancialEntity extends DataEntity {
    private String itemName;
    private int quantityPurchased;
    private int quantitySold;
    private double totalRevenue;
    private double totalCost;
    private double profit;
    private static int idCounter = 0;

    private static final String columnWidth = "| %-15s | %-20s | %11s | %11s | %11s | %11s |";
    private static final String tableHead = String.format(columnWidth, "ITEM NAME", "PURCHASED", "SOLD", "OUTGOINGS", "REVENUE", "PROFIT");

    public FinancialEntity(String itemName, int quantityPurchased, int quantitySold, double totalCost, double totalRevenue) {
        this.itemName = itemName;
        this.quantityPurchased = quantityPurchased;
        this.quantitySold = quantitySold;
        this.id = FinancialEntity.getIdCounter();
        this.totalCost = totalCost;
        this.totalRevenue = totalRevenue;
        calculateProfit();
        FinancialEntity.incrementIdCounter();
    }

    public static int getIdCounter() {
        return idCounter;
    }

    public static void incrementIdCounter() {
        FinancialEntity.idCounter++;
    }

    public boolean matches(Map<String, Object> queryMap) {
        for (Map.Entry<String, Object> entry : queryMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            switch (key) {
                case "id":
                    if (this.id != (int) value) return false;
                    break;
                case "itemName":
                    if (!this.itemName.equals(value)) return false;
                    break;
                case "quantityPurchased":
                    if (this.quantityPurchased != (int) value) return false;
                    break;
                case "quantitySold":
                    if (this.quantitySold != (int) value) return false;
                    break;
                case "totalRevenue":
                    if (!this.compareDoubles(this.totalRevenue, (double) value)) return false;
                    break;
                case "totalCost":
                    if (!this.compareDoubles(this.totalCost, (double) value)) return false;
                    break;
                case "profit":
                    if (!this.compareDoubles(this.profit, (double) value)) return false;
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    private void calculateProfit() {
        this.profit = this.totalRevenue - this.totalCost;
    }

    public void update(Map<String, Object> query) {
        if (query.containsKey("itemName")) {
            this.itemName = (String) query.get("itemName");
        }
        if (query.containsKey("quantityPurchased")) {
            this.quantityPurchased = (int) query.get("quantityPurchased");
        }
        if (query.containsKey("quantitySold")) {
            this.quantitySold = (int) query.get("quantitySold");
        }
        if (query.containsKey("totalRevenue")) {
            this.totalRevenue = (double) query.get("totalRevenue");
        }
        if (query.containsKey("totalCost")) {
            this.totalCost = (double) query.get("totalCost");
        }
        calculateProfit();
    }

    public static void printTableHead() {
        System.out.println("-".repeat(tableHead.length()));
        System.out.println(tableHead);
        System.out.println("-".repeat(tableHead.length()));
    }

    public void printTableRow() {
        System.out.printf(columnWidth + "\n", itemName, quantityPurchased, quantitySold, totalCost, totalRevenue, profit);
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setQuantityPurchased(int quantityPurchased) {
        this.quantityPurchased = quantityPurchased;
        calculateProfit();
    }
    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
        calculateProfit();
    }

    public int getQuantityPurchased() {
        return quantityPurchased;
    }
    public int getQuantitySold() {
        return quantitySold;
    }
}
