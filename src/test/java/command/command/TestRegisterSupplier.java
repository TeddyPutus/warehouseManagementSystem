package command.command;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;
import putus.teddy.command.command.RegisterSupplier;
import putus.teddy.data.entity.SupplierEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.printer.Printer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;

public class TestRegisterSupplier {
    static ByteArrayOutputStream outContent;
    RegisterSupplier command = new RegisterSupplier();

    static SupplierEntity entity1 = new SupplierEntity("supplier", "01234", "email");

    @BeforeClass
    public static void classSetUp() {
        RegisterSupplier.supplierRepository.deleteMany(Map.of());
        RegisterSupplier.supplierRepository.create(entity1);
        outContent = new ByteArrayOutputStream();
        Printer.setOutputStream(new PrintStream(outContent));
    }

    @Before
    public void testSetUp() {
        outContent.reset();
    }

    @Test
    public void testRegisterSupplierWhenSupplierAlreadyExists() {

        try(
                MockedStatic<ValidatedInputParser> mockParser = org.mockito.Mockito.mockStatic(ValidatedInputParser.class);
        ) {
            mockParser.when(()-> ValidatedInputParser.parseString(anyString(),anyBoolean(),anyInt(),anyInt())).thenReturn("supplier").thenReturn("5678").thenReturn("email");

            boolean result = command.execute();
            String output = outContent.toString();
            assertTrue(output.contains("Supplier already exists."));
            assertFalse(output.contains("Supplier registered successfully."));
            assertEquals(1, RegisterSupplier.supplierRepository.findAll().toList().size());
            assertFalse(result);
        }
    }

    @Test
    public void testRegisterSupplier() {
        try(
                MockedStatic<ValidatedInputParser> mockParser = org.mockito.Mockito.mockStatic(ValidatedInputParser.class);
        ) {
            mockParser.when(()-> ValidatedInputParser.parseString(anyString(),anyBoolean(),anyInt(),anyInt())).thenReturn("supplier2").thenReturn("5678").thenReturn("email");

            boolean result = command.execute();
            String output = outContent.toString();
            assertFalse(output.contains("Supplier already exists."));
            assertTrue(output.contains("Supplier registered successfully."));
            assertEquals(2, RegisterSupplier.supplierRepository.findAll().toList().size());
            assertNotNull(RegisterSupplier.supplierRepository.findOne(Map.of("name", "supplier2")));
            assertFalse(result);
        }
    }
}
