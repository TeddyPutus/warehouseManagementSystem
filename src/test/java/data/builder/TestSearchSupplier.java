package data.builder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.SupplierEntity;

import java.util.List;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestSearchSupplier {

    private final String name;
    private final String phoneNumber;
    private final String email;

    private final SupplierEntity entity = new SupplierEntity("Test name", "Test phone", "Test email");

    private final boolean[] expectedResults;

    public TestSearchSupplier(String name, String phoneNumber, String email, boolean[] expectedResults) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.expectedResults = expectedResults;
    }

    @Parameterized.Parameters
    public static Object[][] provideTestCases() {
        return new Object[][]{
                {"Test name", "Test phone", "Test email", new boolean[]{true, true, true}},
                {"Another name", "Test phone", "Test email", new boolean[]{false, true, true}},
                {"Another name", "Another phone", "Test email", new boolean[]{false, false, true}},
                {"Another name", "Another phone", "Another email", new boolean[]{false, false, false}},
                {null, null, null, new boolean[]{true, true, true}},
                {"", "", "", new boolean[]{true, true, true}}
        };
    }

    @Test
    public void testSearchSupplier() {
        List<Predicate<SupplierEntity>> query = QueryBuilder.searchSupplier(name, phoneNumber, email);

        for (int i = 0; i < query.size(); i++) {
            assertEquals("Predicate " + i + " failed", expectedResults[i], query.get(i).test(entity));
        }
    }
}
