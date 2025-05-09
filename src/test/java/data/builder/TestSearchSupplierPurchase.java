package data.builder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.SupplierPurchaseEntity;

import java.util.List;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestSearchSupplierPurchase {
    private final String supplierName;
    private final String itemName;
    private final Integer quantity;
    private final Double price;
    private final String date;

    private final SupplierPurchaseEntity entity = new SupplierPurchaseEntity("Test supplier", "2025-01-01", "Test item", 5, 10.0);

    private boolean[] expectedResults;

    public TestSearchSupplierPurchase(String supplierName, String itemName, Integer quantity, Double price, String date, boolean[] expectedResults) {
        this.supplierName = supplierName;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
        this.expectedResults = expectedResults;
    }


    @Parameterized.Parameters
    public static Object[][] provideTestCases() {
        return new Object[][]{
                {"Test supplier", "Test item", 5, 10.0, "2025-01-01", new boolean[]{true, true, true, true, true}},
                {"Another supplier", "Test item", 5, 10.0, "2025-01-01", new boolean[]{false, true, true, true, true}},
                {"Another supplier", "Another item", 5, 10.0, "2025-01-01", new boolean[]{false, false, true, true, true}},
                {"Another supplier", "Another item", 6, 10.0, "2025-01-01", new boolean[]{false, false, false, true, true}},
                {"Another supplier", "Another item", 6, null, null, new boolean[]{false, false, false, true, true}},
                {null, null, null, null, null, new boolean[]{true, true, true, true, true}},
                {"", "", Integer.MIN_VALUE, Double.MIN_VALUE, "", new boolean[]{true, true, true, true, true}}
        };
    }


    @Test
    public void testSearchSupplierPurchase() {
        List<Predicate<SupplierPurchaseEntity>> query = QueryBuilder.searchSupplierPurchase(supplierName, itemName, quantity, price, date);

        for (int i = 0; i < query.size(); i++) {
            assertEquals("Predicate " + i + " failed", expectedResults[i], query.get(i).test(entity));
        }
    }
}
