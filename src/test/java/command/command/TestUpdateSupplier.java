package command.command;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import putus.teddy.command.command.Command;
import putus.teddy.command.command.UpdateSupplier;
import putus.teddy.data.entity.SupplierEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.data.repository.InMemoryRepository;
import putus.teddy.printer.Printer;

import static org.junit.Assert.*;

public class TestUpdateSupplier {
    static InMemoryRepository<SupplierEntity> supplierRepository = new InMemoryRepository<>();
    UpdateSupplier command = new UpdateSupplier(supplierRepository);

    MockedStatic<ValidatedInputParser> mockParser;
    MockedStatic<Printer> mockPrinter;

    static SupplierEntity entity1 = new SupplierEntity("Supplier 1", "1234", "email");

    @BeforeClass
    public static void classSetUp() {
        supplierRepository.create(entity1);
    }

    @Before
    public void setUp() {
        mockParser = Mockito.mockStatic(ValidatedInputParser.class);
        mockPrinter = Mockito.mockStatic(Printer.class);
        entity1.setPhoneNumber("1234");
        entity1.setEmail("email");
        entity1.setName("Supplier 1");
    }

    @After
    public void tearDown() {
        mockParser.close();
        mockPrinter.close();
    }

    @Test
    public void testUpdateSupplier() {
        mockParser.when(
                        () -> ValidatedInputParser.parseString("Supplier ID", true, 1, 36))
                .thenReturn(entity1.getId());
        mockParser.when(
                () -> ValidatedInputParser.parseString("name", false, 1, 15)
        ).thenReturn("Supplier XYZ");
        mockParser.when(
                () -> ValidatedInputParser.parseString("phone number", false, 1, 12)
        ).thenReturn("5678");
        mockParser.when(
                () -> ValidatedInputParser.parseString("email", false, 1, 20)
        ).thenReturn("new_email");

        Command.Result result = command.execute();

        mockPrinter.verify(() -> Printer.info("Updating supplier information..."));
        mockPrinter.verify(() -> Printer.success("Supplier information updated successfully."));
        mockPrinter.verifyNoMoreInteractions();

        assertEquals(Command.Result.SUCCESS, result);

        assertEquals("5678", entity1.getPhoneNumber());
        assertEquals("new_email", entity1.getEmail());
        assertEquals("Supplier XYZ", entity1.getName());
    }

    @Test
    public void testUpdateSupplierNotFound() {
        mockParser.when(
                () -> ValidatedInputParser.parseString("Supplier ID", true, 1, 36))
                .thenReturn("non-existing-id");

        Command.Result result = command.execute();
        assertEquals(Command.Result.FAILURE, result);

        mockPrinter.verify(() -> Printer.info("Updating supplier information..."));
        mockPrinter.verify(() -> Printer.warning("Supplier not found."));
        mockPrinter.verifyNoMoreInteractions();

        assertEquals("1234", entity1.getPhoneNumber());
        assertEquals("email", entity1.getEmail());
        assertEquals("Supplier 1", entity1.getName());
    }

    @Test
    public void testSupplierAlreadyExists(){
        SupplierEntity entity2 = new SupplierEntity("Supplier 2", "1234", "email");
        supplierRepository.create(entity2);

        mockParser.when(
                () -> ValidatedInputParser.parseString("Supplier ID", true, 1, 36))
                .thenReturn(entity1.getId());
        mockParser.when(
                () -> ValidatedInputParser.parseString("name", false, 1, 15)
        ).thenReturn("Supplier 2");
        mockParser.when(
                () -> ValidatedInputParser.parseString("phone number", false, 1, 12)
        ).thenReturn("1234");
        mockParser.when(
                () -> ValidatedInputParser.parseString("email", false, 1, 20)
        ).thenReturn("email");

        Command.Result result = command.execute();

        assertEquals(Command.Result.FAILURE, result);
        mockPrinter.verify(() -> Printer.info("Updating supplier information..."));
        mockPrinter.verify(() -> Printer.error("Name already exists.\nPhone number already exists.\nEmail already exists.\nSupplier already exists."));
        mockPrinter.verifyNoMoreInteractions();
    }
}
