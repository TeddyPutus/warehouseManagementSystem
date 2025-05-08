package command.command;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import putus.teddy.command.command.Command;
import putus.teddy.command.command.FindInventory;
import putus.teddy.command.command.GenerateReport;
import putus.teddy.data.entity.DataEntity;
import putus.teddy.data.entity.FinancialEntity;
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
import static org.mockito.ArgumentMatchers.eq;

public class TestGenerateReport {
    static InMemoryRepository<FinancialEntity> financialRepository = new InMemoryRepository<>();
    GenerateReport command = new GenerateReport(financialRepository);

    MockedStatic<Printer> mockPrinter;
    static ArgumentCaptor<Stream<DataEntity>> captor;

    static FinancialEntity entity1 = new FinancialEntity("item1", 1, 10, 10.0, 20.0);
    static FinancialEntity entity2 = new FinancialEntity("item2", 2, 20, 20.0, 40.0);
    static FinancialEntity entity3 = new FinancialEntity("item3", 3, 10, 30.0, 60.0);

    @BeforeClass
    public static void classSetUp() {
        financialRepository.create(entity1);
        financialRepository.create(entity2);
        financialRepository.create(entity3);
        captor = ArgumentCaptor.forClass(Stream.class);
    }

    @Before
    public void setUp() {
        mockPrinter = Mockito.mockStatic(Printer.class);
    }

    @After
    public void tearDown() {
        mockPrinter.close();
    }

    @Test
    public void testGenerateReport() {
        Command.Result result = command.execute();
        assertEquals(Command.Result.SUCCESS, result);

        mockPrinter.verify(() -> Printer.printTable(captor.capture(), eq(FinancialEntity.getTableHead())));

        List<DataEntity> capturedEntities = captor.getValue().toList();
        assertTrue(capturedEntities.contains(entity1));
        assertTrue(capturedEntities.contains(entity3));
        assertTrue(capturedEntities.contains(entity2));
        assertEquals(3, capturedEntities.size());

        mockPrinter.verify(() -> Printer.info("Total Sales: 120.0"));
        mockPrinter.verify(() -> Printer.info("Total Expenses: 60.0"));
        mockPrinter.verify(() -> Printer.info("Net Profit: 60.0"));
    }
}
