package command.command;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;
import putus.teddy.command.command.UpdateSupplier;
import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.SupplierEntity;
import putus.teddy.data.parser.InputParser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.Assert.*;

public class TestUpdateSupplier {
    ByteArrayOutputStream outContent;
    UpdateSupplier command = new UpdateSupplier();

    static SupplierEntity entity1 = new SupplierEntity("item1", "1234", "email");

    @BeforeClass
    public static void classSetUp() {
        UpdateSupplier.supplierRepository.deleteMany(Map.of());
        UpdateSupplier.supplierRepository.create(entity1);
    }

    @Before
    public void testSetUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testUpdateSupplier() {

        try (MockedStatic<InputParser> mockParser = org.mockito.Mockito.mockStatic(InputParser.class);
             MockedStatic<QueryBuilder> mockedBuilder = org.mockito.Mockito.mockStatic(QueryBuilder.class)) {
            mockParser.when(
                    () -> InputParser.parseString("Supplier ID", true)
            ).thenReturn(entity1.getId());

            mockedBuilder.when(QueryBuilder::supplierQuery).thenReturn(Map.of(
                    "phoneNumber", "5678"
            ));

            command.execute();
            String output = outContent.toString();
            assertFalse(output.contains("Supplier not found."));
            assertTrue(output.contains("Supplier information updated successfully."));
            assertEquals("5678", entity1.getPhoneNumber());
        }
    }

    @Test
    public void testUpdateSupplierNotFound() {
        try (MockedStatic<InputParser> mockParser = org.mockito.Mockito.mockStatic(InputParser.class)) {
            mockParser.when(
                    () -> InputParser.parseString("Supplier ID", true)
            ).thenReturn("non-existing-id");

            command.execute();
            String output = outContent.toString();
            assertTrue(output.contains("Supplier not found."));
            assertFalse(output.contains("Supplier information updated successfully."));
        }
    }
}
