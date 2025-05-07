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
    public void testConstructor() {
        assertEquals("Test Supplier", supplier.getName());
        assertEquals("1234567890", supplier.getPhoneNumber());
        assertEquals("test@example.com", supplier.getEmail());
    }

    @Test
    public void testPrintTableHead() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String expectedOutput = "| ID                                   | NAME            |        PHONE | EMAIL                |";
        assertEquals(expectedOutput, SupplierEntity.getTableHead());
    }

    @Test
    public void testPrintTableRow() {
        String expectedOutput = "| Test Supplier   |   1234567890 | test@example.com     |\n";
        assertTrue(supplier.getTableRow().contains(expectedOutput));
    }

}