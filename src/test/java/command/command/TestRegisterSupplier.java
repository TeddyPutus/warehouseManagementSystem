package command.command;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import putus.teddy.command.command.Command;
import putus.teddy.command.command.RegisterSupplier;
import putus.teddy.data.entity.SupplierEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.data.repository.InMemoryRepository;
import putus.teddy.printer.Printer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;

public class TestRegisterSupplier {
    static InMemoryRepository<SupplierEntity> supplierRepository = new InMemoryRepository<>();
    RegisterSupplier command = new RegisterSupplier(supplierRepository);

    MockedStatic<ValidatedInputParser> mockParser;
    MockedStatic<Printer> mockPrinter;

    static SupplierEntity entity1 = new SupplierEntity("supplier", "01234", "email");

    @BeforeClass
    public static void classSetUp() {
        supplierRepository.create(entity1);
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
    public void testRegisterSupplierWhenSupplierAlreadyExists() {
        mockParser.when(() -> ValidatedInputParser.parseString(anyString(), anyBoolean(), anyInt(), anyInt())).thenReturn("supplier").thenReturn("5678").thenReturn("email");

        Command.Result result = command.execute();
        assertEquals(Command.Result.FAILURE, result);

        mockPrinter.verify(() -> Printer.info("Registering supplier..."));
        mockPrinter.verify(() -> Printer.error("Supplier already exists."));
        mockPrinter.verifyNoMoreInteractions();

        assertEquals(1, supplierRepository.findAll().toList().size());

    }

    @Test
    public void testRegisterSupplier() {
        mockParser.when(() -> ValidatedInputParser.parseString(anyString(), anyBoolean(), anyInt(), anyInt())).thenReturn("supplier2").thenReturn("5678").thenReturn("email");

        Command.Result result = command.execute();
        assertEquals(Command.Result.SUCCESS, result);

        mockPrinter.verify(() -> Printer.info("Registering supplier..."));
        mockPrinter.verify(() -> Printer.success("Supplier registered successfully."));
        mockPrinter.verifyNoMoreInteractions();

        assertEquals(2, supplierRepository.findAll().toList().size());
        assertNotNull(supplierRepository.findOne(List.of(entity -> entity.getName().equals("supplier2"))));

    }
}
