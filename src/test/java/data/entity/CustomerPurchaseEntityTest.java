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
    public void testUpdate() {
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("customerName", "John");
        queryMap.put("itemName", "Item2");
        queryMap.put("totalPrice", 200.0);
        queryMap.put("purchaseDate", "2023-10-02");
        queryMap.put("quantity", 5);

        customerPurchase.update(queryMap);

        assertEquals("John", customerPurchase.getCustomerName());
        assertEquals("Item2", customerPurchase.getItemName());
        assertEquals(5, customerPurchase.getQuantity());
        assertEquals(200.0, customerPurchase.getTotalPrice(), 0.0);
        assertEquals("2023-10-02", customerPurchase.getPurchaseDate());
    }

    @Test
    public void testMatches() {
        HashMap<String, Object> queryMap = new HashMap<>();

        customerPurchase.setTotalPrice(20.0);

        queryMap.put("purchaseDate", "2023-10-01");
        queryMap.put("quantity", 100);
        queryMap.put("totalPrice", 20.0);

        assertTrue(customerPurchase.matches(queryMap));

        queryMap.put("id", 2);
        assertFalse(customerPurchase.matches(queryMap));
    }

    @Test
    public void testDoesNotMatch() {
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("customerId", 1);
        queryMap.put("totalPrice", 200);

        assertFalse(customerPurchase.matches(queryMap));
    }

    @Test
    public void testPrintTableHead() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        CustomerPurchaseEntity.printTableHead();

        String expectedOutput = "------------------------------------------------------------------------------------------------------------------------------------\n" +
                "| ID                                   | CUSTOMER NAME   | PURCHASED ITEM       |    QUANTITY | TOTAL PRICE | PURCHASE DATE        |\n" +
                "------------------------------------------------------------------------------------------------------------------------------------\n";
        assertTrue(outContent.toString().contains(expectedOutput));

        System.setOut(System.out);
    }

    @Test
    public void testPrintTableRow() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        customerPurchase.printTableRow();

        String expectedOutput = "| Ted             | Item1                |         100 |         0.0 | 2023-10-01           |\n";
        assertTrue(outContent.toString().contains(expectedOutput));

        System.setOut(System.out);
    }


}
