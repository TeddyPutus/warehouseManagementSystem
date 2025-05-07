package command.command;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;
import putus.teddy.command.command.Command;
import putus.teddy.command.command.FindInventory;
import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.InventoryEntity;
import putus.teddy.printer.Printer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.Assert.*;

public class TestFindInventory {
    static ByteArrayOutputStream outContent;
    FindInventory command = new FindInventory();

    static InventoryEntity inventoryEntity1 = new InventoryEntity("item1", 10, 5.0);
    static InventoryEntity inventoryEntity2 = new InventoryEntity("item2", 20, 10.0);
    static InventoryEntity inventoryEntity3 = new InventoryEntity("item3", 10, 15.0);

    @BeforeClass
    public static void classSetUp() {
        FindInventory.inventoryRepository.deleteMany(Map.of());
        FindInventory.inventoryRepository.create(inventoryEntity1);
        FindInventory.inventoryRepository.create(inventoryEntity2);
        FindInventory.inventoryRepository.create(inventoryEntity3);
        outContent = new ByteArrayOutputStream();
        Printer.setOutputStream(new PrintStream(outContent));
    }

    @Test
    public void testFindInventory() {

        try (MockedStatic<QueryBuilder> mockedBuilder = org.mockito.Mockito.mockStatic(QueryBuilder.class)) {
            // Mock the behavior of the static method
            mockedBuilder.when(QueryBuilder::supplierQuery).thenReturn(Map.of(
                    "quantity", 10
            ));

            Command.Result result = command.execute();
            assertEquals(Command.Result.SUCCESS, result);

            assertTrue(outContent.toString().contains(InventoryEntity.getTableHead()));
            assertTrue(outContent.toString().contains(inventoryEntity1.getItemName()));
            assertTrue(outContent.toString().contains(inventoryEntity3.getItemName()));
        }
    }

    @Test
    public void testFindInventoryWithNoResults() {
        try (MockedStatic<QueryBuilder> mockedBuilder = org.mockito.Mockito.mockStatic(QueryBuilder.class)) {

            mockedBuilder.when(QueryBuilder::inventoryQuery).thenReturn(Map.of(
                    "quantity", 10000
            ));

            Command.Result result = command.execute();
            assertEquals(Command.Result.SUCCESS, result);

            assertFalse(outContent.toString().contains(inventoryEntity1.getTableRow()));
            assertFalse(outContent.toString().contains(inventoryEntity2.getTableRow()));
            assertFalse(outContent.toString().contains(inventoryEntity3.getTableRow()));
        }
    }
}
