package command.command;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import putus.teddy.command.command.Command;
import putus.teddy.command.command.CustomerOrder;
import putus.teddy.data.entity.CustomerPurchaseEntity;
import putus.teddy.data.entity.FinancialEntity;
import putus.teddy.data.entity.InventoryEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.data.repository.InMemoryRepository;
import putus.teddy.printer.Printer;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class TestCustomerOrder {

    InMemoryRepository<InventoryEntity> mockInventoryRepository = Mockito.mock(InMemoryRepository.class);
    InMemoryRepository<FinancialEntity> mockFinancialRepository = Mockito.mock(InMemoryRepository.class);
    InMemoryRepository<CustomerPurchaseEntity> mockCustomerPurchaseRepository = Mockito.mock(InMemoryRepository.class);
    MockedStatic<ValidatedInputParser> mockParser;
    MockedStatic<Printer> mockPrinter;

    CustomerOrder command = new CustomerOrder(mockFinancialRepository, mockInventoryRepository, mockCustomerPurchaseRepository);

    static InventoryEntity inventoryEntity = new InventoryEntity("item1", 1, 1.0);
    static FinancialEntity financialEntity = new FinancialEntity("item1", 0, 0, 10.0, 0.0);

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
    public void testCustomerOrder() {
        mockParser.when(() -> ValidatedInputParser.parseString("name", true, 1, 15)).thenReturn("customer");
        mockParser.when(() -> ValidatedInputParser.parseString("itemName", true, 1, 15)).thenReturn("item1");
        mockParser.when(() -> ValidatedInputParser.parseQuantity(anyString(), anyBoolean())).thenReturn(1);

        when(mockInventoryRepository.findOne(anyList())).thenReturn(inventoryEntity);
        when(mockFinancialRepository.findOne(anyList())).thenReturn(financialEntity);

        Command.Result result = command.execute();

        mockPrinter.verify(() -> Printer.success(startsWith("Order placed successfully. Order ID is ")));

        assertEquals(Command.Result.SUCCESS, result);

        verify(mockInventoryRepository).findOne(anyList());
        verify(mockFinancialRepository).findOne(anyList());
        verify(mockCustomerPurchaseRepository).create(any());
    }

    @Test
    public void testCustomerOrderNotEnoughStock() {
        mockParser.when(() -> ValidatedInputParser.parseString("name", true, 1, 15)).thenReturn("customer");
        mockParser.when(() -> ValidatedInputParser.parseString("itemName", true, 1, 15)).thenReturn("item1");
        mockParser.when(() -> ValidatedInputParser.parseQuantity(anyString(), anyBoolean())).thenReturn(2);

        when(mockInventoryRepository.findOne(anyList())).thenReturn(new InventoryEntity("item1", 0, 1.0));
        when(mockFinancialRepository.findOne(anyList())).thenReturn(financialEntity);

        Command.Result result = command.execute();

        mockPrinter.verify(() -> Printer.error("Not enough stock available. Please order more stock."));
        assertEquals(Command.Result.FAILURE, result);

        verify(mockInventoryRepository).findOne(anyList());
        verify(mockFinancialRepository).findOne(anyList());
        verify(mockCustomerPurchaseRepository, never()).create(any());
    }

    @Test
    public void testCustomerOrderLowStockAlert() {
        mockParser.when(() -> ValidatedInputParser.parseString("name", true, 1, 15)).thenReturn("customer");
        mockParser.when(() -> ValidatedInputParser.parseString("itemName", true, 1, 15)).thenReturn("item1");
        mockParser.when(() -> ValidatedInputParser.parseQuantity(anyString(), anyBoolean())).thenReturn(1);

        inventoryEntity.setQuantity(5);
        when(mockInventoryRepository.findOne(anyList())).thenReturn(inventoryEntity);
        when(mockFinancialRepository.findOne(anyList())).thenReturn(financialEntity);


        Command.Result result = command.execute();

        mockPrinter.verify(() -> Printer.alert("Stock for item1 is low. Please order more stock."));
        assertEquals(Command.Result.SUCCESS, result);

        verify(mockInventoryRepository).findOne(anyList());
        verify(mockFinancialRepository).findOne(anyList());
    }

    @Test
    public void testCustomerOrderWhenItemNotFound() {
        mockParser.when(() -> ValidatedInputParser.parseString("name", true, 1, 15)).thenReturn("customer");
        mockParser.when(() -> ValidatedInputParser.parseString("itemName", true, 1, 15)).thenReturn("item2");
        mockParser.when(() -> ValidatedInputParser.parseQuantity(anyString(), anyBoolean())).thenReturn(2);

        when(mockInventoryRepository.findOne(anyList())).thenReturn(null);

        Command.Result result = command.execute();

        mockPrinter.verify(() -> Printer.error("Item does not exist. Please register item first."));

        assertEquals(Command.Result.FAILURE, result);

        verify(mockInventoryRepository).findOne(anyList());
    }

    @Test
    public void testFinancialEntityNotFound() {
        mockParser.when(() -> ValidatedInputParser.parseString("name", true, 1, 15)).thenReturn("customer");
        mockParser.when(() -> ValidatedInputParser.parseString("itemName", true, 1, 15)).thenReturn("item1");
        mockParser.when(() -> ValidatedInputParser.parseQuantity(anyString(), anyBoolean())).thenReturn(1);

        when(mockInventoryRepository.findOne(anyList())).thenReturn(inventoryEntity);
        when(mockFinancialRepository.findOne(anyList())).thenReturn(null);

        Command.Result result = command.execute();

        mockPrinter.verify(() -> Printer.error("Financial entity not found for item: item1"));
        assertEquals(Command.Result.FAILURE, result);

        verify(mockInventoryRepository).findOne(anyList());
        verify(mockFinancialRepository).findOne(anyList());
    }
}
