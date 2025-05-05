package data.entity;

import org.junit.Before;
import org.junit.Test;
import putus.teddy.data.entity.InventoryEntity;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import static org.junit.Assert.*;

public class InventoryEntityTest {
    private InventoryEntity inventoryItem;

    @Before
    public void setUp() {
        inventoryItem = new InventoryEntity("Item1", 10, 100.0);
    }

    @Test
    public void testConstructor() {
        assertEquals("Item1", inventoryItem.getItemName());
        assertEquals(10, inventoryItem.getQuantity());
        assertEquals(100.0, inventoryItem.getPricePerUnit(), 0.0);
    }

    @Test
    public void testUpdate() {
        HashMap<String, Object> updates = new HashMap<>();
        updates.put("itemName", "UpdatedItem");
        updates.put("quantity", 20);
        updates.put("pricePerUnit", 200.0);

        inventoryItem.update(updates);

        assertEquals("UpdatedItem", inventoryItem.getItemName());
        assertEquals(20, inventoryItem.getQuantity());
        assertEquals(200, inventoryItem.getPricePerUnit(), 0.0);
    }

    @Test
    public void testMatches() {
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("itemName", "Item1");
        queryMap.put("quantity", 10);
        queryMap.put("pricePerUnit", 100.0);

        assertTrue(inventoryItem.matches(queryMap));
    }

    @Test
    public void testDoesNotMatch() {
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("name", "NonExistentItem");

        assertFalse(inventoryItem.matches(queryMap));
    }

    @Test
    public void testPrintTableHeader() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        InventoryEntity.printTableHead();

        String expectedOutput =  "--------------------------------------\n" +
                "| ITEM NAME  | QUANTITY | PRICE/UNIT |\n" +
                "--------------------------------------\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testPrintTableRow() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        inventoryItem.printTableRow();

        String expectedOutput = "| Item1      |       10 |      100.0 |\n";
        assertEquals(expectedOutput, outContent.toString());

        System.setOut(System.out);
    }
}
