package command.command;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import putus.teddy.command.command.Command;
import putus.teddy.command.command.OrderStock;
import putus.teddy.data.entity.*;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.data.repository.InMemoryRepository;
import putus.teddy.printer.Printer;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestOrderStock {
    InMemoryRepository<SupplierPurchaseEntity> mockSupplierPurchaseRespository = Mockito.mock(InMemoryRepository.class);
    InMemoryRepository<SupplierEntity> mockSupplierRepository = Mockito.mock(InMemoryRepository.class);
    InMemoryRepository<InventoryEntity> mockInventoryRepository = Mockito.mock(InMemoryRepository.class);
    MockedStatic<ValidatedInputParser> mockParser;
    MockedStatic<Printer> mockPrinter;

    OrderStock command = new OrderStock(mockSupplierPurchaseRespository, mockSupplierRepository, mockInventoryRepository);

    static InventoryEntity inventoryEntity = new InventoryEntity("item1", 1, 1.0);
    static SupplierEntity supplierEntity = new SupplierEntity("Supplier 1", "1234", "email");

    @Before
    public void setUp() {
        mockParser = Mockito.mockStatic(ValidatedInputParser.class);
        mockPrinter = Mockito.mockStatic(Printer.class);
    }

    @After
    public void tearDown() {
        mockParser.close();
        mockPrinter.close();
    }

    @Test
    public void testOrderStock() {
        mockParser.when(() -> ValidatedInputParser.parseString("item name", true, 1, 15)).thenReturn("item1");
        mockParser.when(() -> ValidatedInputParser.parseString("supplier name", true, 1, 15)).thenReturn("Supplier 1");
        mockParser.when(() -> ValidatedInputParser.parseQuantity(anyString(), anyBoolean())).thenReturn(1);
        mockParser.when(() -> ValidatedInputParser.parseAmount(anyString(), anyBoolean())).thenReturn(100.0);

        when(mockInventoryRepository.findOne(anyList())).thenReturn(inventoryEntity);
        when(mockSupplierRepository.findOne(anyList())).thenReturn(supplierEntity);

        Command.Result result = command.execute();

        mockPrinter.verify(() -> Printer.success(startsWith("Order placed successfully. Order ID is ")));

        assertEquals(Command.Result.SUCCESS, result);

        verify(mockInventoryRepository).findOne(anyList());
        verify(mockSupplierRepository).findOne(anyList());
        verify(mockSupplierPurchaseRespository).create(any(SupplierPurchaseEntity.class));
    }

    @Test
    public void testOrderStockInvalidItem() {
        mockParser.when(() -> ValidatedInputParser.parseString("item name", true, 1, 15)).thenReturn("invalidItem");
        mockParser.when(() -> ValidatedInputParser.parseString("supplier name", true, 1, 15)).thenReturn("Supplier 1");
        mockParser.when(() -> ValidatedInputParser.parseQuantity(anyString(), anyBoolean())).thenReturn(1);
        mockParser.when(() -> ValidatedInputParser.parseAmount(anyString(), anyBoolean())).thenReturn(100.0);

        when(mockInventoryRepository.findOne(anyList())).thenReturn(null);

        Command.Result result = command.execute();

        mockPrinter.verify(() -> Printer.error("Item does not exist. Please register item first."));

        assertEquals(Command.Result.FAILURE, result);
        verify(mockInventoryRepository).findOne(anyList());
        verify(mockSupplierRepository, Mockito.never()).findOne(anyList());
    }

    @Test
    public void testOrderStockInvalidSupplier() {
        mockParser.when(() -> ValidatedInputParser.parseString("item name", true, 1, 15)).thenReturn("item1");
        mockParser.when(() -> ValidatedInputParser.parseString("supplier name", true, 1, 15)).thenReturn("invalidSupplier");
        mockParser.when(() -> ValidatedInputParser.parseQuantity(anyString(), anyBoolean())).thenReturn(1);
        mockParser.when(() -> ValidatedInputParser.parseAmount(anyString(), anyBoolean())).thenReturn(100.0);

        when(mockInventoryRepository.findOne(anyList())).thenReturn(inventoryEntity);
        when(mockSupplierRepository.findOne(anyList())).thenReturn(null);

        Command.Result result = command.execute();

        mockPrinter.verify(() -> Printer.error("Supplier does not exist. Please register supplier first."));

        assertEquals(Command.Result.FAILURE, result);
        verify(mockInventoryRepository).findOne(anyList());
        verify(mockSupplierRepository).findOne(anyList());
        verify(mockSupplierPurchaseRespository, Mockito.never()).create(any(SupplierPurchaseEntity.class));
    }

}
