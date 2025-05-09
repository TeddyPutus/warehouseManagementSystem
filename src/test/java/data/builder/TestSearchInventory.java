package data.builder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.InventoryEntity;

import java.util.List;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestSearchInventory {

    private final String name;
    private final Integer quantity;
    private final Double price;
    private final InventoryEntity entity = new InventoryEntity("Test String", 5, 10.0);
    private final boolean[] expectedResults;

    public TestSearchInventory(String name, Integer quantity, Double price, boolean[] expectedResults) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.expectedResults = expectedResults;
    }

    @Parameterized.Parameters
    public static Object[][] provideTestCases() {
        return new Object[][]{
                {"Test String", 6, 10.0, new boolean[]{true, false, true}},
                {"Another String", 5, 10.0, new boolean[]{false, true, true}},
                {"Another String", 6, 11.0, new boolean[]{false, false, false}},
                {"Test String", null, null, new boolean[]{true, true, true}},
                {null, null, null, new boolean[]{true, true, true}},
                {"", Integer.MIN_VALUE, Double.MIN_VALUE, new boolean[]{true, true, true}}
        };
    }

    @Test
    public void testSearchInventory() {
        List<Predicate<InventoryEntity>> query = QueryBuilder.searchInventory(name, quantity, price);

        for (int i = 0; i < query.size(); i++) {
            assertEquals("Predicate " + i + " failed", expectedResults[i], query.get(i).test(entity));
        }
    }
}
