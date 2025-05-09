package putus.teddy.data.entity;

/*
 * FinancialEntity represents a financial record of an item.
 * It contains information about the item name, quantity purchased, quantity sold,
 * total revenue, total cost, and profit.
 */
public class FinancialEntity implements DataEntity {
    private String itemName;
    private Integer quantityPurchased;
    private Integer quantitySold;
    private Double totalRevenue;
    private Double totalCost;
    private Double profit;

    private static final String columnWidth = "| %-15s | %-20s | %11s | %11s | %11s | %11s |";
    private static final String tableHead = String.format(columnWidth, "ITEM NAME", "PURCHASED", "SOLD", "OUTGOINGS", "REVENUE", "PROFIT");

    /*
     * Constructor for FinancialEntity. Will calculate profit automatically.
     */
    public FinancialEntity(String itemName, Integer quantityPurchased, Integer quantitySold, Double totalCost, Double totalRevenue) {
        this.itemName = itemName;
        this.quantityPurchased = quantityPurchased;
        this.quantitySold = quantitySold;
        this.totalCost = totalCost;
        this.totalRevenue = totalRevenue;

        calculateProfit();
    }

    /*
     * Calculates the profit based on total revenue and total cost.
     */
    private void calculateProfit() {
        this.profit = this.totalRevenue - this.totalCost;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public static String getTableHead() {
        return tableHead;
    }

    public String getTableRow() {
        return String.format(columnWidth + "\n", itemName, quantityPurchased, quantitySold, totalCost, totalRevenue, profit);
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

    /*
     * Setter for total revenue. Will automatically recalculate profit.
     */
    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
        calculateProfit();
    }

    /*
     * Setter for total cost. Will automatically recalculate profit.
     */
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
        calculateProfit();
    }

    public void setQuantityPurchased(int quantityPurchased) {
        this.quantityPurchased = quantityPurchased;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }
}
