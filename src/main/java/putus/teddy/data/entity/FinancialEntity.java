package putus.teddy.data.entity;

import java.util.Map;

public class FinancialEntity extends DataEntity {
    private String itemName;
    private Integer quantityPurchased;
    private Integer quantitySold;
    private Double totalRevenue;
    private Double totalCost;
    private Double profit;

    private static final String columnWidth = "| %-15s | %-20s | %11s | %11s | %11s | %11s |";
    private static final String tableHead = String.format(columnWidth, "ITEM NAME", "PURCHASED", "SOLD", "OUTGOINGS", "REVENUE", "PROFIT");

    public FinancialEntity(String itemName, Integer quantityPurchased, Integer quantitySold, Double totalCost, Double totalRevenue) {
        this.itemName = itemName;
        this.quantityPurchased = quantityPurchased;
        this.quantitySold = quantitySold;
        this.totalCost = totalCost;
        this.totalRevenue = totalRevenue;

        calculateProfit();
    }

    public boolean matches(Map<String, Object> queryMap) {
        return queryMap.entrySet().stream()
                .allMatch(entry -> {
                    String key = entry.getKey();
                    Object value = entry.getValue();

                    return switch (key) {
                        case "itemName" -> itemName.equals(value);
                        case "quantityPurchased" -> quantityPurchased.equals(value);
                        case "quantitySold" -> quantitySold.equals(value);
                        case "totalRevenue" ->  value instanceof Double && DataEntity.compareDoubles(totalRevenue, (Double) value);
                        case "totalCost" ->  value instanceof Double && DataEntity.compareDoubles(totalCost, (Double) value);
                        case "profit" ->  value instanceof Double && DataEntity.compareDoubles(profit, (Double) value);
                        default -> false;
                    };
                });
    }

    private void calculateProfit() {
        this.profit = this.totalRevenue - this.totalCost;
    }

    public void update(Map<String, Object> query) {
        query.forEach((key, value) -> {
            switch (key) {
                case "quantityPurchased" -> this.quantityPurchased = (Integer) value;
                case "itemName" -> this.itemName = (String) value;
                case "quantitySold" -> this.quantitySold = (Integer) value;
                case "totalRevenue" -> this.totalRevenue = (Double) value;
                case "totalCost" -> this.totalCost = (Double) value;
            }
        });
        calculateProfit();
    }

    public static void printTableHead() {
        DataEntity.printTableHead(tableHead);
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
    public int getQuantityPurchased() {
        return quantityPurchased;
    }
    public int getQuantitySold() {
        return quantitySold;
    }
    public double getProfit() {
        return profit;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
    public void setQuantityPurchased(int quantityPurchased) {
        this.quantityPurchased = quantityPurchased;
    }
    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }
}
