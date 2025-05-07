package command.command;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;
import putus.teddy.command.command.Command;
import putus.teddy.command.command.FindStockOrders;
import putus.teddy.command.command.TakeDelivery;
import putus.teddy.data.entity.FinancialEntity;
import putus.teddy.data.entity.InventoryEntity;
import putus.teddy.data.entity.SupplierPurchaseEntity;
import putus.teddy.data.parser.InputParser;
import putus.teddy.printer.Printer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestTakeDelivery {
    static ByteArrayOutputStream outContent;
    TakeDelivery command = new TakeDelivery();

    static SupplierPurchaseEntity supplierPurchaseEntity = new SupplierPurchaseEntity("Supplier A", "2025-01-01", "item1", 10, 1.0);
    static InventoryEntity inventoryEntity = new InventoryEntity("item1", 2, 10.0);
    static FinancialEntity financialEntity = new FinancialEntity("item1", 1, 0, 10.0, 0.0);

    @BeforeClass
    public static void classSetUp() {
        FindStockOrders.supplierPurchaseRepository.deleteMany(List.of(entity -> true));
        FindStockOrders.inventoryRepository.deleteMany(List.of(entity -> true));
        FindStockOrders.financialRepository.deleteMany(List.of(entity -> true));

        FindStockOrders.supplierPurchaseRepository.create(supplierPurchaseEntity);
        FindStockOrders.inventoryRepository.create(inventoryEntity);
        FindStockOrders.financialRepository.create(financialEntity);
        outContent = new ByteArrayOutputStream();
        Printer.setOutputStream(new PrintStream(outContent));
    }

    @Before
    public void testSetUp() {
        outContent.reset();
        supplierPurchaseEntity.setStatus(SupplierPurchaseEntity.Status.PENDING);
        supplierPurchaseEntity.setItemName("item1");
        financialEntity.setItemName("item1");
    }

    @Test
    public void testTakeDelivery() {
        try (
                MockedStatic<InputParser> mockParser = org.mockito.Mockito.mockStatic(InputParser.class);
        ) {
            mockParser.when(() -> InputParser.parseString("Order ID", true)).thenReturn(supplierPurchaseEntity.getId());

            Command.Result result = command.execute();
            assertEquals(Command.Result.SUCCESS, result);

            String output = outContent.toString();

            assertTrue(output.contains("Delivery taken successfully."));

            assertEquals(SupplierPurchaseEntity.Status.DELIVERED, supplierPurchaseEntity.getStatus());

            assertEquals(11, financialEntity.getQuantityPurchased());
            assertEquals(20.0, financialEntity.getTotalCost(), 0.01);
            assertEquals(0.0, financialEntity.getTotalRevenue(), 0.01);
            assertEquals(-20.0, financialEntity.getProfit(), 0.01);

            assertEquals(12, (int) inventoryEntity.getQuantity());
        }
    }

    @Test
    public void testTakeDeliveryWhenOrderNotFound() {
        try (
                MockedStatic<InputParser> mockParser = org.mockito.Mockito.mockStatic(InputParser.class);
        ) {
            mockParser.when(() -> InputParser.parseString("Order ID", true)).thenReturn("nonexistent_order_id");

            Command.Result result = command.execute();
            assertEquals(Command.Result.FAILURE, result);

            String output = outContent.toString();

            assertTrue(output.contains("Order not found."));
        }
    }

    @Test
    public void testTakeDeliveryWhenOrderAlreadyDelivered() {
        try (
                MockedStatic<InputParser> mockParser = org.mockito.Mockito.mockStatic(InputParser.class);
        ) {

            supplierPurchaseEntity.setStatus(SupplierPurchaseEntity.Status.DELIVERED);
            mockParser.when(() -> InputParser.parseString("Order ID", true)).thenReturn(supplierPurchaseEntity.getId());

            Command.Result result = command.execute();
            assertEquals(Command.Result.FAILURE, result);

            String output = outContent.toString();

            assertTrue(output.contains("Order already delivered."));
        }
    }

    @Test
    public void testTakeDeliveryWhenItemNotFoundInInventory() {
        try (
                MockedStatic<InputParser> mockParser = org.mockito.Mockito.mockStatic(InputParser.class);
        ) {
            supplierPurchaseEntity.setItemName("some_item");
            mockParser.when(() -> InputParser.parseString("Order ID", true)).thenReturn(supplierPurchaseEntity.getId());

            Command.Result result = command.execute();
            assertEquals(Command.Result.FAILURE, result);

            String output = outContent.toString();

            assertTrue(output.contains("Item not found in inventory."));
        }
    }

    @Test
    public void testTakeDeliveryWhenFinancialEntityNotFound() {
        try (
                MockedStatic<InputParser> mockParser = org.mockito.Mockito.mockStatic(InputParser.class);
        ) {
            financialEntity.setItemName("nonexistent_item");
            mockParser.when(() -> InputParser.parseString("Order ID", true)).thenReturn(supplierPurchaseEntity.getId());

            Command.Result result = command.execute();
            assertEquals(Command.Result.FAILURE, result);

            String output = outContent.toString();

            assertTrue(output.contains("Financial entity not found."));
        }
    }
}
