package data.builder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.CustomerPurchaseEntity;

import java.util.List;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestSearchCustomerOrder {

    private final String customerName;
    private final String itemName;
    private final Integer quantity;
    private final String date;

    private final CustomerPurchaseEntity entity = new CustomerPurchaseEntity("Test customer", "Test item", 5, "2025-01-01");
    private final boolean[] expectedResults;

    public TestSearchCustomerOrder(String customerName, String itemName, Integer quantity, String date, boolean[] expectedResults) {
        this.customerName = customerName;
        this.itemName = itemName;
        this.quantity = quantity;
        this.date = date;
        this.expectedResults = expectedResults;
    }

    @Parameterized.Parameters
    public static Object[][] provideTestCases() {
        return new Object[][]{
                {"Test customer", "Test item", 5, "2025-01-01", new boolean[]{true, true, true, true}},
                {"Another customer", "Test item", 5, "2025-01-01", new boolean[]{false, true, true, true}},
                {"Another customer", "Another item", 5, "2025-01-01", new boolean[]{false, false, true, true}},
                {"Another customer", "Another item", 6, "2025-01-01", new boolean[]{false, false, false, true}},
                {"Another customer", "Another item", 6, null, new boolean[]{false, false, false, true}},
                {null, null, null, null, new boolean[]{true, true, true, true}},
                {"", "", Integer.MIN_VALUE, "", new boolean[]{true, true, true, true}}
        };
    }

    @Test
    public void testSearchCustomerOrder() {
        List<Predicate<CustomerPurchaseEntity>> query = QueryBuilder.searchCustomerOrder(customerName, itemName, quantity, date);

        for (int i = 0; i < query.size(); i++) {
            assertEquals("Predicate " + i + " failed", expectedResults[i], query.get(i).test(entity));
        }
    }
}
