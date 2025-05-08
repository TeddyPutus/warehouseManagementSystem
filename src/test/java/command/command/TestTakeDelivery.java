package command.command;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import putus.teddy.command.command.Command;
import putus.teddy.command.command.FindStockOrders;
import putus.teddy.command.command.TakeDelivery;
import putus.teddy.data.entity.FinancialEntity;
import putus.teddy.data.entity.InventoryEntity;
import putus.teddy.data.entity.SupplierPurchaseEntity;
import putus.teddy.data.parser.InputParser;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.data.repository.InMemoryRepository;
import putus.teddy.printer.Printer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestTakeDelivery {
    static InMemoryRepository<SupplierPurchaseEntity> supplierPurchaseRepository = new InMemoryRepository<>();
    static InMemoryRepository<InventoryEntity> inventoryRepository = new InMemoryRepository<>();
    static InMemoryRepository<FinancialEntity> financialRepository = new InMemoryRepository<>();

    MockedStatic<ValidatedInputParser> mockParser;
    MockedStatic<Printer> mockPrinter;

    TakeDelivery command = new TakeDelivery(supplierPurchaseRepository, inventoryRepository, financialRepository);

    static SupplierPurchaseEntity supplierPurchaseEntity = new SupplierPurchaseEntity("Supplier A", "2025-01-01", "item1", 10, 1.0);
    static InventoryEntity inventoryEntity = new InventoryEntity("item1", 2, 10.0);
    static FinancialEntity financialEntity = new FinancialEntity("item1", 1, 0, 10.0, 0.0);

    @BeforeClass
    public static void classSetUp() {
        inventoryRepository.deleteMany(List.of(entity -> true));
        financialRepository.deleteMany(List.of(entity -> true));

        supplierPurchaseRepository.create(supplierPurchaseEntity);
        inventoryRepository.create(inventoryEntity);
        financialRepository.create(financialEntity);
    }


    @Before
    public void setUp() {
        mockParser = Mockito.mockStatic(ValidatedInputParser.class);
        mockPrinter = Mockito.mockStatic(Printer.class);
    }

    @After
    public void tearDown() {
        mockParser.close();
        mockPrinter.close();

        supplierPurchaseEntity.setStatus(SupplierPurchaseEntity.Status.PENDING);
        supplierPurchaseEntity.setItemName("item1");
        inventoryEntity.setQuantity(2);
        financialEntity.setItemName("item1");
        financialEntity.setQuantityPurchased(1);
        financialEntity.setTotalCost(10.0);
        financialEntity.setTotalRevenue(0.0);
    }

    @Test
    public void testTakeDelivery() {
        mockParser.when(() -> ValidatedInputParser.parseString("Order ID", true, 1, 36)).thenReturn(supplierPurchaseEntity.getId());

        Command.Result result = command.execute();
        assertEquals(Command.Result.SUCCESS, result);

        mockPrinter.verify(() -> Printer.info("Taking delivery..."));
        mockPrinter.verify(() -> Printer.success("Delivery taken successfully."));
        mockPrinter.verifyNoMoreInteractions();

        assertEquals(SupplierPurchaseEntity.Status.DELIVERED, supplierPurchaseEntity.getStatus());

        assertEquals(11, financialEntity.getQuantityPurchased());
        assertEquals(20.0, financialEntity.getTotalCost(), 0.01);
        assertEquals(0.0, financialEntity.getTotalRevenue(), 0.01);
        assertEquals(-20.0, financialEntity.getProfit(), 0.01);

        assertEquals(12, (int) inventoryEntity.getQuantity());
    }

    @Test
    public void testTakeDeliveryWhenOrderNotFound() {
        mockParser.when(() -> ValidatedInputParser.parseString("Order ID", true, 1, 36)).thenReturn("nonexistent_order_id");

        Command.Result result = command.execute();
        assertEquals(Command.Result.FAILURE, result);

        mockPrinter.verify(() -> Printer.info("Taking delivery..."));
        mockPrinter.verify(() -> Printer.error("Order not found."));
        mockPrinter.verifyNoMoreInteractions();

        assertEquals(1, financialEntity.getQuantityPurchased());
        assertEquals(10.0, financialEntity.getTotalCost(), 0.01);
        assertEquals(0.0, financialEntity.getTotalRevenue(), 0.01);
        assertEquals(-10.0, financialEntity.getProfit(), 0.01);

        assertEquals(2, (int) inventoryEntity.getQuantity());
    }

    @Test
    public void testTakeDeliveryWhenOrderAlreadyDelivered() {

        supplierPurchaseEntity.setStatus(SupplierPurchaseEntity.Status.DELIVERED);
        mockParser.when(() -> ValidatedInputParser.parseString("Order ID", true, 1, 36)).thenReturn(supplierPurchaseEntity.getId());

        Command.Result result = command.execute();
        assertEquals(Command.Result.FAILURE, result);

        mockPrinter.verify(() -> Printer.info("Taking delivery..."));
        mockPrinter.verify(() -> Printer.error("Order already delivered."));
        mockPrinter.verifyNoMoreInteractions();

        assertEquals(1, financialEntity.getQuantityPurchased());
        assertEquals(10.0, financialEntity.getTotalCost(), 0.01);
        assertEquals(0.0, financialEntity.getTotalRevenue(), 0.01);
        assertEquals(-10.0, financialEntity.getProfit(), 0.01);

        assertEquals(2, (int) inventoryEntity.getQuantity());
    }

    @Test
    public void testTakeDeliveryWhenItemNotFoundInInventory() {

        supplierPurchaseEntity.setItemName("some_item");
        mockParser.when(() -> ValidatedInputParser.parseString("Order ID", true, 1, 36)).thenReturn(supplierPurchaseEntity.getId());

        Command.Result result = command.execute();
        assertEquals(Command.Result.FAILURE, result);

        mockPrinter.verify(() -> Printer.info("Taking delivery..."));
        mockPrinter.verify(() -> Printer.error("Item not found in inventory."));
        mockPrinter.verifyNoMoreInteractions();

        assertEquals(1, financialEntity.getQuantityPurchased());
        assertEquals(10.0, financialEntity.getTotalCost(), 0.01);
        assertEquals(0.0, financialEntity.getTotalRevenue(), 0.01);
        assertEquals(-10.0, financialEntity.getProfit(), 0.01);

        assertEquals(2, (int) inventoryEntity.getQuantity());
    }

    @Test
    public void testTakeDeliveryWhenFinancialEntityNotFound() {

        financialEntity.setItemName("nonexistent_item");
        mockParser.when(() -> ValidatedInputParser.parseString("Order ID", true, 1, 36)).thenReturn(supplierPurchaseEntity.getId());

        Command.Result result = command.execute();
        assertEquals(Command.Result.FAILURE, result);

        mockPrinter.verify(() -> Printer.info("Taking delivery..."));
        mockPrinter.verify(() -> Printer.error("Financial entity not found."));
        mockPrinter.verifyNoMoreInteractions();

        assertEquals(1, financialEntity.getQuantityPurchased());
        assertEquals(10.0, financialEntity.getTotalCost(), 0.01);
        assertEquals(0.0, financialEntity.getTotalRevenue(), 0.01);
        assertEquals(-10.0, financialEntity.getProfit(), 0.01);

        assertEquals(2, (int) inventoryEntity.getQuantity());
    }

}
