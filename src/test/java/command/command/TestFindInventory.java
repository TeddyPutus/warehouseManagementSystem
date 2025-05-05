package command.command;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;
import putus.teddy.command.command.FindInventory;
import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.InventoryEntity;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestFindInventory {
    ByteArrayOutputStream outContent;
    FindInventory findInventoryCommand = new FindInventory();

    static InventoryEntity inventoryEntity1 = new InventoryEntity("item1", 10, 5.0);
    static InventoryEntity inventoryEntity2 = new InventoryEntity("item2", 20, 10.0);
    static InventoryEntity inventoryEntity3 = new InventoryEntity("item3", 10, 15.0);

    @BeforeClass
    public static void classSetUp() {
        FindInventory.inventoryRepository.create(inventoryEntity1);
        FindInventory.inventoryRepository.create(inventoryEntity2);
        FindInventory.inventoryRepository.create(inventoryEntity3);
    }

    @Before
    public void testSetUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testFindInventory() {

        try (MockedStatic<QueryBuilder> mockedBuilder = org.mockito.Mockito.mockStatic(QueryBuilder.class)) {
            // Mock the behavior of the static method
            mockedBuilder.when(QueryBuilder::supplierQuery).thenReturn(Map.of(
                    "quantity", 10
            ));

            findInventoryCommand.execute();

            String expectedOutput = "--------------------------------------\n" +
                    "| ITEM NAME  | QUANTITY | PRICE/UNIT |\n" +
                    "--------------------------------------\n" +
                    "| item1      |       10 |        5.0 |\n" +
                    "| item3      |       10 |       15.0 |\n";

            assertTrue(outContent.toString().contains(expectedOutput));
        }
    }

    @Test
    public void testFindInventoryWithNoResults() {
        try (MockedStatic<QueryBuilder> mockedBuilder = org.mockito.Mockito.mockStatic(QueryBuilder.class)) {

            mockedBuilder.when(QueryBuilder::supplierQuery).thenReturn(Map.of(
                    "quantity", 10000
            ));

            findInventoryCommand.execute();

            assertFalse(outContent.toString().contains("| item1      |       10 |        5.0 |\n"));
            assertFalse(outContent.toString().contains("| item2      |       20 |       10.0 |\n"));
            assertFalse(outContent.toString().contains("| item3      |       10 |       15.0 |\n"));
        }
    }
}
