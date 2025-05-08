package command.command;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import putus.teddy.command.command.Command;
import putus.teddy.command.command.RegisterItem;

import putus.teddy.data.entity.DataEntity;
import putus.teddy.data.entity.FinancialEntity;
import putus.teddy.data.entity.InventoryEntity;

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

public class TestRegisterItem {
    static InMemoryRepository<InventoryEntity> inventoryRepository = new InMemoryRepository<>();
    static InMemoryRepository<FinancialEntity> financialRepository = new InMemoryRepository<>();
    RegisterItem command = new RegisterItem(financialRepository, inventoryRepository);

    MockedStatic<ValidatedInputParser> mockParser;
    MockedStatic<Printer> mockPrinter;

    static InventoryEntity entity1 = new InventoryEntity("item1", 1, 1.0);

    @BeforeClass
    public static void classSetUp() {
        inventoryRepository.create(entity1);
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
    public void testRegisterItemWhenItemAlreadyExists() {
        mockParser.when(() -> ValidatedInputParser.parseString(anyString(), anyBoolean(), anyInt(), anyInt())).thenReturn("item1");
        mockParser.when(() -> ValidatedInputParser.parseAmount(anyString(), anyBoolean())).thenReturn(0.0);

        Command.Result result = command.execute();
        assertEquals(Command.Result.FAILURE, result);

        mockPrinter.verify(() -> Printer.info("Registering item..."));
        mockPrinter.verify(() -> Printer.error("Item already exists."));
        mockPrinter.verifyNoMoreInteractions();

        assertEquals(1, inventoryRepository.findAll().toList().size());
        assertNull(financialRepository.findOne(List.of(entity -> entity.getItemName().equals("item1"))));
    }

    @Test
    public void testRegisterItem() {
        mockParser.when(() -> ValidatedInputParser.parseString(anyString(), anyBoolean(), anyInt(), anyInt())).thenReturn("item2");
        mockParser.when(() -> ValidatedInputParser.parseAmount(anyString(), anyBoolean())).thenReturn(0.0);

        Command.Result result = command.execute();
        assertEquals(Command.Result.SUCCESS, result);

        mockPrinter.verify(() -> Printer.info("Registering item..."));
        mockPrinter.verify(() -> Printer.success("Item registered successfully."));
        mockPrinter.verifyNoMoreInteractions();

        assertEquals(2, inventoryRepository.findAll().toList().size());
        assertNotNull(inventoryRepository.findOne(
                List.of(entity -> entity.getItemName().equals("item2"))));
        assertNotNull(financialRepository.findOne(
                List.of(entity -> entity.getItemName().equals("item2"))
        ));
    }

}
