package command.command;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import putus.teddy.command.command.Command;
import putus.teddy.command.command.FindStockOrders;
import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.CustomerPurchaseEntity;
import putus.teddy.data.entity.DataEntity;
import putus.teddy.data.entity.SupplierPurchaseEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.data.repository.InMemoryRepository;
import putus.teddy.printer.Printer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;

public class TestFindStockOrders {
    static InMemoryRepository<SupplierPurchaseEntity> supplierPurchaseRepository = new InMemoryRepository<>();
    FindStockOrders command = new FindStockOrders(supplierPurchaseRepository);

    MockedStatic<ValidatedInputParser> mockParser;
    MockedStatic<Printer> mockPrinter;
    static ArgumentCaptor<Stream<DataEntity>> captor;

    static SupplierPurchaseEntity entity1 = new SupplierPurchaseEntity("Supplier A", "2025-01-01", "item1", 10, 1.0);
    static SupplierPurchaseEntity entity2 = new SupplierPurchaseEntity("Supplier B", "2025-01-02", "item2", 20, 2.0);
    static SupplierPurchaseEntity entity3 = new SupplierPurchaseEntity("Supplier A", "2025-01-03", "item1", 10, 3.0);

    @BeforeClass
    public static void classSetUp() {
        supplierPurchaseRepository.deleteMany(List.of(entity -> true));
        supplierPurchaseRepository.create(entity1);
        supplierPurchaseRepository.create(entity2);
        supplierPurchaseRepository.create(entity3);
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
            mockParser.when(()-> ValidatedInputParser.parseString("name", true,1,15)).thenReturn("Supplier A");
            mockParser.when(()-> ValidatedInputParser.parseString("itemName", true,1,15)).thenReturn("");
            mockParser.when(()-> ValidatedInputParser.parseString("date", true,1,15)).thenReturn("");
            mockParser.when(()-> ValidatedInputParser.parseAmount(anyString(),anyBoolean())).thenReturn(Double.MIN_VALUE);
            mockParser.when(()-> ValidatedInputParser.parseQuantity(anyString(),anyBoolean())).thenReturn(10);


            Command.Result result = command.execute();
            assertEquals(Command.Result.SUCCESS, result);

            mockPrinter.verify(() -> Printer.printTable(captor.capture(), eq(SupplierPurchaseEntity.getTableHead())));

            List<DataEntity> capturedEntities = captor.getValue().toList();
            assertTrue(capturedEntities.contains(entity1));
            assertTrue(capturedEntities.contains(entity3));
            assertFalse(capturedEntities.contains(entity2));
            assertEquals(2, capturedEntities.size());
    }

    @Test
    public void testFindOrdersWithNoResults() {
            mockParser.when(()-> ValidatedInputParser.parseString("name", false,1,15)).thenReturn("Supplier Z");
            mockParser.when(()-> ValidatedInputParser.parseString("itemName", false,1,15)).thenReturn("");
            mockParser.when(()-> ValidatedInputParser.parseString("date", false,1,15)).thenReturn("");
            mockParser.when(()-> ValidatedInputParser.parseAmount(anyString(),anyBoolean())).thenReturn(Double.MIN_VALUE);
            mockParser.when(()-> ValidatedInputParser.parseQuantity(anyString(),anyBoolean())).thenReturn(10);

            Command.Result result = command.execute();
            assertEquals(Command.Result.SUCCESS, result);

            mockPrinter.verify(() -> Printer.printTable(captor.capture(), eq(SupplierPurchaseEntity.getTableHead())));
            assertEquals(0, captor.getValue().count());
    }
}
