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
import putus.teddy.data.entity.DataEntity;
import putus.teddy.data.entity.InventoryEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.data.repository.InMemoryRepository;
import putus.teddy.printer.Printer;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;

public class TestFindInventory {
    static InMemoryRepository<InventoryEntity> inventoryRepository = new InMemoryRepository<>();
    FindInventory command = new FindInventory(inventoryRepository);

    MockedStatic<ValidatedInputParser> mockParser;
    MockedStatic<Printer> mockPrinter;
    static ArgumentCaptor<Stream<DataEntity>> captor;

    static InventoryEntity inventoryEntity1 = new InventoryEntity("item1", 10, 5.0);
    static InventoryEntity inventoryEntity2 = new InventoryEntity("item2", 20, 10.0);
    static InventoryEntity inventoryEntity3 = new InventoryEntity("item3", 10, 15.0);

    @BeforeClass
    public static void setUpClass() {
        inventoryRepository.create(inventoryEntity1);
        inventoryRepository.create(inventoryEntity2);
        inventoryRepository.create(inventoryEntity3);
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
    public void testFindInventory() {
        mockParser.when(() -> ValidatedInputParser.parseString("name", false, 1, 15)).thenReturn("");
        mockParser.when(() -> ValidatedInputParser.parseAmount("price", false)).thenReturn(Double.MIN_VALUE);
        mockParser.when(() -> ValidatedInputParser.parseQuantity("quantity", false)).thenReturn(10);

        Command.Result result = command.execute();
        assertEquals(Command.Result.SUCCESS, result);

        mockPrinter.verify(() -> Printer.printTable(captor.capture(), eq(InventoryEntity.getTableHead())));

        List<DataEntity> capturedEntities = captor.getValue().toList();
        assertTrue(capturedEntities.contains(inventoryEntity1));
        assertTrue(capturedEntities.contains(inventoryEntity3));
        assertFalse(capturedEntities.contains(inventoryEntity2));
        assertEquals(2, capturedEntities.size());
    }

    @Test
    public void testFindInventoryWithNoResults() {
            mockParser.when(() -> ValidatedInputParser.parseString("name", true, 1, 15)).thenReturn("");
            mockParser.when(() -> ValidatedInputParser.parseAmount(anyString(), anyBoolean())).thenReturn(Double.MIN_VALUE);
            mockParser.when(() -> ValidatedInputParser.parseQuantity(anyString(), anyBoolean())).thenReturn(10000);

            Command.Result result = command.execute();
            assertEquals(Command.Result.SUCCESS, result);

            mockPrinter.verify(() -> Printer.printTable(captor.capture(), eq(InventoryEntity.getTableHead())));

            assertEquals(0, captor.getValue().count());
        }

}
