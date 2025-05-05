package data.entity;

import org.junit.Before;
import org.junit.Test;
import putus.teddy.data.entity.SupplierPurchaseEntity;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.Assert.*;

public class SupplierPurchaseTest {
    private SupplierPurchaseEntity supplierPurchase;

    @Before
    public void setUp() {
        supplierPurchase = new SupplierPurchaseEntity("Test Supplier", "2025-01-01", "item1",10, 100.0);
    }

    @Test
    public void testConstructor() {
        assertEquals("Test Supplier", supplierPurchase.getSupplierName());
        assertEquals("item1", supplierPurchase.getItemName());
        assertEquals("2025-01-01", supplierPurchase.getPurchaseDate());
        assertEquals(10, supplierPurchase.getQuantity());
        assertEquals(100.0, supplierPurchase.getPricePerUnit(), 0.01);
        assertEquals(1000.0, supplierPurchase.getTotalPrice(), 0.01);
    }

    @Test
    public void testUpdate() {
        supplierPurchase.update(Map.of("quantity", 20));
        assertEquals(20, supplierPurchase.getQuantity());
        assertEquals(2000.0, supplierPurchase.getTotalPrice(), 0.01);
    }

    @Test
    public void testMatches() {
        assertTrue(supplierPurchase.matches(Map.of("supplierName", "Test Supplier")));
        assertFalse(supplierPurchase.matches(Map.of("supplierName", "Other Supplier")));
    }

    @Test
    public void testPrintTableHead() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        SupplierPurchaseEntity.printTableHead();

        String expectedOutput = "---------------------------------------------------------------------------------------------------------------------------------\n" +
                "| ID                                   | SUPPLIER    | DATE        | ITEM NAME            | QUANTITY | PRICE/UNIT | TOTAL PRICE |\n" +
                "---------------------------------------------------------------------------------------------------------------------------------\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testPrintTableRow() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        supplierPurchase.printTableRow();

        String expectedOutput = "| Test Supplier | 2025-01-01  | item1                |       10 |      100.0 |      1000.0 |\n";
        assertTrue(outContent.toString().contains(expectedOutput));
    }
}
