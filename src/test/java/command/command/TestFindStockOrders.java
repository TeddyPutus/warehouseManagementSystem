package command.command;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;
import putus.teddy.command.command.Command;
import putus.teddy.command.command.FindStockOrders;
import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.SupplierPurchaseEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.printer.Printer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;

public class TestFindStockOrders {
    static ByteArrayOutputStream outContent;
    FindStockOrders command = new FindStockOrders();

    static SupplierPurchaseEntity entity1 = new SupplierPurchaseEntity("Supplier A", "2025-01-01", "item1", 10, 1.0);
    static SupplierPurchaseEntity entity2 = new SupplierPurchaseEntity("Supplier B", "2025-01-02", "item2", 20, 2.0);
    static SupplierPurchaseEntity entity3 = new SupplierPurchaseEntity("Supplier A", "2025-01-03", "item1", 10, 3.0);

    @BeforeClass
    public static void classSetUp() {
        FindStockOrders.supplierPurchaseRepository.deleteMany(List.of(entity -> true));
        FindStockOrders.supplierPurchaseRepository.create(entity1);
        FindStockOrders.supplierPurchaseRepository.create(entity2);
        FindStockOrders.supplierPurchaseRepository.create(entity3);

        outContent = new ByteArrayOutputStream();
        Printer.setOutputStream(new PrintStream(outContent));
    }

    @Before
    public void testSetUp() {
        outContent.reset();
    }

    @Test
    public void testFindOrders() {

        try (MockedStatic<ValidatedInputParser> mockParser = org.mockito.Mockito.mockStatic(ValidatedInputParser.class)) {
            mockParser.when(()-> ValidatedInputParser.parseString("name", true,1,15)).thenReturn("Supplier A");
            mockParser.when(()-> ValidatedInputParser.parseString("itemName", true,1,15)).thenReturn("");
            mockParser.when(()-> ValidatedInputParser.parseString("date", true,1,15)).thenReturn("");
            mockParser.when(()-> ValidatedInputParser.parseAmount(anyString(),anyBoolean())).thenReturn(Double.MIN_VALUE);
            mockParser.when(()-> ValidatedInputParser.parseQuantity(anyString(),anyBoolean())).thenReturn(10);


            Command.Result result = command.execute();
            assertEquals(Command.Result.SUCCESS, result);
            String output = outContent.toString();

            assertTrue(output.contains(SupplierPurchaseEntity.getTableHead()));
            assertTrue(output.contains(entity1.getSupplierName()));
            assertTrue(output.contains(entity3.getSupplierName()));


            assertFalse(output.contains(entity2.getSupplierName()));
        }
    }

    @Test
    public void testFindOrdersWithNoResults() {
        try (MockedStatic<ValidatedInputParser> mockParser = org.mockito.Mockito.mockStatic(ValidatedInputParser.class)) {
            mockParser.when(()-> ValidatedInputParser.parseString("name", false,1,15)).thenReturn("Supplier Z");
            mockParser.when(()-> ValidatedInputParser.parseString("itemName", false,1,15)).thenReturn("");
            mockParser.when(()-> ValidatedInputParser.parseString("date", false,1,15)).thenReturn("");
            mockParser.when(()-> ValidatedInputParser.parseAmount(anyString(),anyBoolean())).thenReturn(Double.MIN_VALUE);
            mockParser.when(()-> ValidatedInputParser.parseQuantity(anyString(),anyBoolean())).thenReturn(10);

            Command.Result result = command.execute();
            assertEquals(Command.Result.SUCCESS, result);

            assertFalse(outContent.toString().contains(entity1.getSupplierName()));
            assertFalse(outContent.toString().contains(entity2.getSupplierName()));
            assertFalse(outContent.toString().contains(entity3.getSupplierName()));
        }
    }
}
