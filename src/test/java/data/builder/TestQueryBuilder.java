package data.builder;

import org.junit.Test;
import putus.teddy.data.builder.*;
import putus.teddy.data.entity.*;

import java.util.List;
import java.util.function.Predicate;

import static org.junit.Assert.*;

public class TestQueryBuilder {

    @Test
    public void testSearchSupplierById() {
        SupplierEntity entity = new SupplierEntity("Test name", "Test phone", "Test email");
        List<Predicate<SupplierEntity>> query = QueryBuilder.searchSupplierById(entity.getId());

        assertEquals(1, query.size());
        assertTrue(query.getFirst().test(entity));

        assertFalse(query.getFirst().test(new SupplierEntity("Another name", "Another phone", "Another email")));

        List<Predicate<SupplierEntity>> nullQuery = QueryBuilder.searchSupplierById(null);
        List<Predicate<SupplierEntity>> emptyQuery = QueryBuilder.searchSupplierById("");

        assertTrue(nullQuery.getFirst().test(entity));
        assertTrue(emptyQuery.getFirst().test(entity));
    }

    @Test
    public void testSearchSupplierByName() {
        SupplierEntity entity = new SupplierEntity("Test name", "Test phone", "Test email");
        List<Predicate<SupplierEntity>> query = QueryBuilder.searchSupplierByName(entity.getName());

        assertEquals(1, query.size());
        assertTrue(query.getFirst().test(entity));

        assertFalse(query.getFirst().test(new SupplierEntity("Another name", "Another phone", "Another email")));

        List<Predicate<SupplierEntity>> nullQuery = QueryBuilder.searchSupplierByName(null);
        List<Predicate<SupplierEntity>> emptyQuery = QueryBuilder.searchSupplierByName("");

        assertTrue(nullQuery.getFirst().test(entity));
        assertTrue(emptyQuery.getFirst().test(entity));
    }

    @Test
    public void testSearchInventoryByItemName() {
        InventoryEntity entity = new InventoryEntity("Test item", 5, 10.0);
        List<Predicate<InventoryEntity>> query = QueryBuilder.searchInventoryByItemName(entity.getItemName());

        assertEquals(1, query.size());
        assertTrue(query.getFirst().test(entity));

        assertFalse(query.getFirst().test(new InventoryEntity("Another item", 5, 10.0)));

        List<Predicate<InventoryEntity>> nullQuery = QueryBuilder.searchInventoryByItemName(null);
        List<Predicate<InventoryEntity>> emptyQuery = QueryBuilder.searchInventoryByItemName("");

        assertTrue(nullQuery.getFirst().test(entity));
        assertTrue(emptyQuery.getFirst().test(entity));
    }

    @Test
    public void testSearchSupplierPurchaseById() {
        SupplierPurchaseEntity entity = new SupplierPurchaseEntity("Test supplier", "2025-01-01", "Test item", 5, 10.0);
        List<Predicate<SupplierPurchaseEntity>> query = QueryBuilder.searchSupplierPurchaseById(entity.getId());

        assertEquals(1, query.size());
        assertTrue(query.getFirst().test(entity));

        assertFalse(query.getFirst().test(new SupplierPurchaseEntity("Another supplier", "2025-01-01", "Another item", 6, 20.0)));

        List<Predicate<SupplierPurchaseEntity>> nullQuery = QueryBuilder.searchSupplierPurchaseById(null);
        List<Predicate<SupplierPurchaseEntity>> emptyQuery = QueryBuilder.searchSupplierPurchaseById("");

        assertTrue(nullQuery.getFirst().test(entity));
        assertTrue(emptyQuery.getFirst().test(entity));
    }

    @Test
    public void testSearchFinancial(){
        FinancialEntity entity = new FinancialEntity("Test item", 100, 50, 0.0, 0.0);

        List<Predicate<FinancialEntity>> query = QueryBuilder.searchFinancial(entity.getItemName());
        assertTrue(query.getFirst().test(entity));

        assertFalse(query.getFirst().test(new FinancialEntity("Another item", 100, 50, 0.0, 0.0)));

        List<Predicate<FinancialEntity>> nullQuery = QueryBuilder.searchFinancial(null);
        List<Predicate<FinancialEntity>> emptyQuery = QueryBuilder.searchFinancial("");

        assertTrue(nullQuery.getFirst().test(entity));
    }


}

