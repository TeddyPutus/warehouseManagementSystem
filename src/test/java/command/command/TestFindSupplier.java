package command.command;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import putus.teddy.command.command.Command;
import putus.teddy.command.command.FindSuppliers;
import putus.teddy.data.entity.DataEntity;
import putus.teddy.data.entity.SupplierEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.data.repository.InMemoryRepository;
import putus.teddy.printer.Printer;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;

public class TestFindSupplier {
    static InMemoryRepository<SupplierEntity> supplierRepository = new InMemoryRepository<>();
    FindSuppliers command = new FindSuppliers(supplierRepository);

    MockedStatic<ValidatedInputParser> mockParser;
    MockedStatic<Printer> mockPrinter;
    static ArgumentCaptor<Stream<DataEntity>> captor;

    static SupplierEntity entity1 = new SupplierEntity("supplier1", "1234", "email");
    static SupplierEntity entity2 = new SupplierEntity("supplier2", "5678", "email2");
    static SupplierEntity entity3 = new SupplierEntity("supplier3", "1234", "email3");

    @BeforeClass
    public static void classSetUp() {
        supplierRepository.create(entity1);
        supplierRepository.create(entity2);
        supplierRepository.create(entity3);
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
    public void testFindSupplier() {

        mockParser.when(() -> ValidatedInputParser.parseString("name", false, 1, 15)).thenReturn("");
        mockParser.when(() -> ValidatedInputParser.parseString("phone number", false, 1, 12)).thenReturn("1234");
        mockParser.when(() -> ValidatedInputParser.parseString("email", false, 1, 20)).thenReturn("");

        Command.Result result = command.execute();
        assertEquals(Command.Result.SUCCESS, result);

        mockPrinter.verify(() -> Printer.printTable(captor.capture(), eq(SupplierEntity.getTableHead())));

        List<DataEntity> capturedEntities = captor.getValue().toList();
        assertTrue(capturedEntities.contains(entity1));
        assertTrue(capturedEntities.contains(entity3));
        assertFalse(capturedEntities.contains(entity2));
        assertEquals(2, capturedEntities.size());
    }

    @Test
    public void testFindInventoryWithNoResults() {
        mockParser.when(() -> ValidatedInputParser.parseString("name", false, 1, 15)).thenReturn("");
        mockParser.when(() -> ValidatedInputParser.parseString("phone number", false, 1, 12)).thenReturn("9999");
        mockParser.when(() -> ValidatedInputParser.parseString("email", false, 1, 20)).thenReturn("");
        mockParser.when(() -> ValidatedInputParser.parseAmount(anyString(), anyBoolean())).thenReturn(Double.MIN_VALUE);
        mockParser.when(() -> ValidatedInputParser.parseQuantity(anyString(), anyBoolean())).thenReturn(10);

        Command.Result result = command.execute();
        assertEquals(Command.Result.SUCCESS, result);

        mockPrinter.verify(() -> Printer.printTable(captor.capture(), eq(SupplierEntity.getTableHead())));
        assertEquals(0, captor.getValue().count());
    }
}
