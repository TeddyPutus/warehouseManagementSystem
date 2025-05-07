package data.entity;

import org.junit.Before;
import org.junit.Test;
import putus.teddy.data.entity.CustomerPurchaseEntity;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import static org.junit.Assert.*;

public class CustomerPurchaseEntityTest {
    private CustomerPurchaseEntity customerPurchase;

    @Before
    public void setUp() {
        customerPurchase = new CustomerPurchaseEntity("Ted", "Item1", 100, "2023-10-01");
    }

    @Test
    public void testConstructor() {
        assertEquals("Ted", customerPurchase.getCustomerName());

        assertEquals(0.0, customerPurchase.getTotalPrice(), 0.0);
        assertEquals("2023-10-01", customerPurchase.getPurchaseDate());
    }

    @Test
    public void testPrintTableHead() {
        String expectedOutput = "| ID                                   | CUSTOMER NAME   | PURCHASED ITEM       |    QUANTITY | TOTAL PRICE | PURCHASE DATE        |";
        assertTrue(CustomerPurchaseEntity.getTableHead().contains(expectedOutput));

    }

    @Test
    public void testPrintTableRow() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String expectedOutput = "| Ted             | Item1                |         100 |         0.0 | 2023-10-01           |\n";
        assertTrue(customerPurchase.getTableRow().contains(expectedOutput));

        System.setOut(System.out);
    }


}
