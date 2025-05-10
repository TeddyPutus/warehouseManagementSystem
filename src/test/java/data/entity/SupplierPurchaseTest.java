package data.entity;

import org.junit.Before;
import org.junit.Test;
import putus.teddy.data.entity.SupplierPurchaseEntity;

import static org.junit.Assert.*;

public class SupplierPurchaseTest {
    private SupplierPurchaseEntity supplierPurchase;

    @Before
    public void setUp() {
        supplierPurchase = new SupplierPurchaseEntity("Test Supplier", "2025-01-01", "item1",10, 100.0);
    }

    @Test
    public void testPrintTableHead() {

        String expectedOutput = "| ID                                   | SUPPLIER    | DATE        | ITEM NAME            | QUANTITY | PRICE/UNIT | TOTAL PRICE |";
        assertEquals(expectedOutput, SupplierPurchaseEntity.getTableHead());
    }

    @Test
    public void testPrintTableRow() {

        String expectedOutput = "| Test Supplier | 2025-01-01  | item1                |       10 |      100.0 |      1000.0 |\n";
        assertTrue(supplierPurchase.getTableRow().contains(expectedOutput));
    }
}
