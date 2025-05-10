package command.command;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import putus.teddy.command.command.Command;
import putus.teddy.command.command.FindOrders;
import putus.teddy.data.entity.CustomerPurchaseEntity;
import putus.teddy.data.entity.DataEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.data.repository.InMemoryRepository;
import putus.teddy.printer.Printer;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;

public class TestFindOrders {
    static InMemoryRepository<CustomerPurchaseEntity> customerPurchaseRepository = new InMemoryRepository<>();
    FindOrders command = new FindOrders(customerPurchaseRepository);

    MockedStatic<ValidatedInputParser> mockParser;
    MockedStatic<Printer> mockPrinter;
    static ArgumentCaptor<Stream<DataEntity>> captor;

    static CustomerPurchaseEntity entity1 = new CustomerPurchaseEntity("customer1", "item1", 10, "2025-01-01");
    static CustomerPurchaseEntity entity2 = new CustomerPurchaseEntity("customer2", "item2", 20, "2025-01-02");
    static CustomerPurchaseEntity entity3 = new CustomerPurchaseEntity("customer3", "item1", 10, "2025-01-03");

    @BeforeClass
    public static void classSetUp() {
        customerPurchaseRepository.create(entity1);
        customerPurchaseRepository.create(entity2);
        customerPurchaseRepository.create(entity3);
        captor = ArgumentCaptor.forClass(Stream.class);
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
    }

    @Test
    public void testFindOrders() {
            mockParser.when(()-> ValidatedInputParser.parseString("name", true,1,15)).thenReturn("");
            mockParser.when(()-> ValidatedInputParser.parseString("itemName", true,1,15)).thenReturn("item1");
            mockParser.when(()-> ValidatedInputParser.parseAmount(anyString(),anyBoolean())).thenReturn(Double.MIN_VALUE);
            mockParser.when(()-> ValidatedInputParser.parseQuantity(anyString(),anyBoolean())).thenReturn(10);

            Command.Result result = command.execute();
            assertEquals(Command.Result.SUCCESS, result);

            mockPrinter.verify(() -> Printer.printTable(captor.capture(), eq(CustomerPurchaseEntity.getTableHead())));

            List<DataEntity> capturedEntities = captor.getValue().toList();
            assertTrue(capturedEntities.contains(entity1));
            assertTrue(capturedEntities.contains(entity3));
            assertFalse(capturedEntities.contains(entity2));
            assertEquals(2, capturedEntities.size());
    }

    @Test
    public void testFindOrdersWithNoResults() {
        mockParser.when(()-> ValidatedInputParser.parseString("name", false,1,15)).thenReturn("");
        mockParser.when(()-> ValidatedInputParser.parseString("itemName", false,1,15)).thenReturn("itemxyzabc");
        mockParser.when(()-> ValidatedInputParser.parseAmount(anyString(),anyBoolean())).thenReturn(Double.MIN_VALUE);
        mockParser.when(()-> ValidatedInputParser.parseQuantity(anyString(),anyBoolean())).thenReturn(10);

        Command.Result result = command.execute();
        assertEquals(Command.Result.SUCCESS, result);

        mockPrinter.verify(() -> Printer.printTable(captor.capture(), eq(CustomerPurchaseEntity.getTableHead())));
        assertEquals(0, captor.getValue().count());
    }
}
