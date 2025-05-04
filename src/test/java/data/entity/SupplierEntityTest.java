package data.entity;

import org.junit.Before;
import org.junit.Test;
import putus.teddy.data.entity.SupplierEntity;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import static org.junit.Assert.*;

public class SupplierEntityTest {

    private SupplierEntity supplier;

    @Before
    public void setUp() {
        supplier = new SupplierEntity("Test Supplier", "1234567890", "test@example.com");
    }


    @Test
    public void testIncrementId() {
        int initialId = SupplierEntity.getIdCounter();
        SupplierEntity newEntity = new SupplierEntity("Test Supplier", "1234567890", "test@example.com");
        assertEquals(initialId + 1, SupplierEntity.getIdCounter());
        SupplierEntity anotherEntity = new SupplierEntity("Test Supplier", "1234567890", "test@example.com");
        assertEquals(initialId, newEntity.getId());
        assertEquals(initialId + 1, anotherEntity.getId());
        assertEquals(initialId + 2, SupplierEntity.getIdCounter());
    }

    @Test
    public void testConstructor() {
        assertEquals("Test Supplier", supplier.getName());
        assertEquals("1234567890", supplier.getPhoneNumber());
        assertEquals("test@example.com", supplier.getEmail());
    }

    @Test
    public void testUpdate() {
        HashMap<String, Object> updates = new HashMap<>();
        updates.put("name", "Updated Supplier");
        updates.put("phoneNumber", "0987654321");
        updates.put("email", "updated@example.com");

        supplier.update(updates);

        assertEquals("Updated Supplier", supplier.getName());
        assertEquals("0987654321", supplier.getPhoneNumber());
        assertEquals("updated@example.com", supplier.getEmail());
    }

    @Test
    public void testPrintTableHead() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        supplier.printTableHead();

        String expectedOutput = "----------------------------------------------------\n" +
                "| NAME       |        PHONE | EMAIL                |\n" +
                "----------------------------------------------------\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testPrintTableRow() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        supplier.printTableRow();

        String expectedOutput = "| Test Supplier |   1234567890 | test@example.com     |\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testMatches() {
        HashMap<String, Object> updates = new HashMap<>();
        updates.put("name", "Test Supplier");

        assertTrue(supplier.matches(updates));
    }

    @Test
    public void testDoesNotMatch() {
        HashMap<String, Object> updates = new HashMap<>();
        updates.put("name", "Nonexistent Supplier");

        assertFalse(supplier.matches(updates));

        updates.clear();
        updates.put("nonExistentField", "456 Test St");
        assertFalse(supplier.matches(updates));
    }

}