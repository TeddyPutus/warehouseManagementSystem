package data.builder;

import org.junit.Test;
import org.mockito.MockedStatic;
import putus.teddy.data.builder.*;
import putus.teddy.data.entity.CustomerPurchaseEntity;
import putus.teddy.data.entity.InventoryEntity;
import putus.teddy.data.entity.SupplierEntity;
import putus.teddy.data.entity.SupplierPurchaseEntity;
import putus.teddy.data.parser.InputParser;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestQueryBuilder {
    @Test
    public void testSupplierSearchQuery() {
            List<Predicate<SupplierEntity>> query = QueryBuilder.supplierSearch("Test name", "Test phone", "Test email");
            assertTrue("Test name", query.getFirst().test(new SupplierEntity("Test name", "Test phone", "Test email")));

    }

    @Test
    public void testInventorySearchQuery() {
            List<Predicate<InventoryEntity>> query = QueryBuilder.searchInventory("Test String", 6, 10.0);
            assertTrue(query.getFirst().test(new InventoryEntity("Test String", 5, 10.0)));
            assertFalse(query.get(1).test(new InventoryEntity("Test String", 5, 10.0)));
            assertTrue(query.get(2).test(new InventoryEntity("Test String", 5, 10.0)));

    }

    @Test
    public void testCustomerOrderQuery() {
            CustomerPurchaseEntity customerPurchase = new CustomerPurchaseEntity("Test customer", "Test item", 5, "2025-01-01");

            List<Predicate<CustomerPurchaseEntity>> query = QueryBuilder.searchCustomerOrder("Test customer", "A different item", 5, "2025");

            assertTrue(query.getFirst().test(customerPurchase));
            assertFalse(query.get(1).test(customerPurchase));
            assertTrue(query.get(2).test(customerPurchase));

    }

    @Test
    public void testSupplierPurchaseQuery() {
            SupplierPurchaseEntity purchase = new SupplierPurchaseEntity("Test supplier", "2025-01-01", "Test Item", 5, 10.0);
             List<Predicate<SupplierPurchaseEntity>> query = QueryBuilder.searchSupplierPurchase("Test supplier", "Some other item", 5, 10.0, "2025");
            assertFalse(query.get(1).test(purchase));
    }

}
