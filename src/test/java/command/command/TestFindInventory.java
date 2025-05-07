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
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.printer.Printer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;

public class TestFindInventory {
    static ByteArrayOutputStream outContent;
    FindInventory command = new FindInventory();

    static InventoryEntity inventoryEntity1 = new InventoryEntity("item1", 10, 5.0);
    static InventoryEntity inventoryEntity2 = new InventoryEntity("item2", 20, 10.0);
    static InventoryEntity inventoryEntity3 = new InventoryEntity("item3", 10, 15.0);

    @BeforeClass
    public static void classSetUp() {
        FindInventory.inventoryRepository.deleteMany(List.of(entity -> true));
        FindInventory.inventoryRepository.create(inventoryEntity1);
        FindInventory.inventoryRepository.create(inventoryEntity2);
        FindInventory.inventoryRepository.create(inventoryEntity3);
        outContent = new ByteArrayOutputStream();
        Printer.setOutputStream(new PrintStream(outContent));
    }

    @Test
    public void testFindInventory() {
        try (MockedStatic<ValidatedInputParser> mockParser = org.mockito.Mockito.mockStatic(ValidatedInputParser.class)) {
            mockParser.when(()-> ValidatedInputParser.parseString("name", true,1,15)).thenReturn("");
            mockParser.when(()-> ValidatedInputParser.parseAmount(anyString(),anyBoolean())).thenReturn(Double.MIN_VALUE);
            mockParser.when(()-> ValidatedInputParser.parseQuantity(anyString(),anyBoolean())).thenReturn(10);

            Command.Result result = command.execute();
            assertEquals(Command.Result.SUCCESS, result);

            assertTrue(outContent.toString().contains(InventoryEntity.getTableHead()));
            assertTrue(outContent.toString().contains(inventoryEntity1.getItemName()));
            assertTrue(outContent.toString().contains(inventoryEntity3.getItemName()));
        }
    }

    @Test
    public void testFindInventoryWithNoResults() {
        try (MockedStatic<ValidatedInputParser> mockParser = org.mockito.Mockito.mockStatic(ValidatedInputParser.class)) {
            mockParser.when(()-> ValidatedInputParser.parseString("name", true,1,15)).thenReturn("");
            mockParser.when(()-> ValidatedInputParser.parseAmount(anyString(),anyBoolean())).thenReturn(Double.MIN_VALUE);
            mockParser.when(()-> ValidatedInputParser.parseQuantity(anyString(),anyBoolean())).thenReturn(10000);

            Command.Result result = command.execute();
            assertEquals(Command.Result.SUCCESS, result);

            assertFalse(outContent.toString().contains(inventoryEntity1.getTableRow()));
            assertFalse(outContent.toString().contains(inventoryEntity2.getTableRow()));
            assertFalse(outContent.toString().contains(inventoryEntity3.getTableRow()));
        }
    }
}
