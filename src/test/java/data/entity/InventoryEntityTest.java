package data.entity;

import org.junit.Before;
import org.junit.Test;
import putus.teddy.data.entity.InventoryEntity;

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
        assertEquals(10, (int) inventoryItem.getQuantity());
        assertEquals(100.0, inventoryItem.getPricePerUnit(), 0.0);
    }

    @Test
    public void testPrintTableHeader() {

        String expectedOutput = "| ITEM NAME  | QUANTITY | PRICE/UNIT |";
        assertEquals(expectedOutput, InventoryEntity.getTableHead());
    }

    @Test
    public void testPrintTableRow() {

        String expectedOutput = "| Item1      |       10 |      100.0 |\n";
        assertEquals(expectedOutput, inventoryItem.getTableRow());

        System.setOut(System.out);
    }
}
