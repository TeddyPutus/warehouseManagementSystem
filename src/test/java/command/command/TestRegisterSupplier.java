package command.command;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;
import putus.teddy.command.command.RegisterSupplier;
import putus.teddy.data.builder.EntityBuilder;
import putus.teddy.data.entity.InventoryEntity;
import putus.teddy.data.entity.SupplierEntity;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.Assert.*;

public class TestRegisterSupplier {
    ByteArrayOutputStream outContent;
    RegisterSupplier command = new RegisterSupplier();

    static SupplierEntity entity1 = new SupplierEntity("supplier", "01234", "email");

    @BeforeClass
    public static void classSetUp() {
        RegisterSupplier.supplierRepository.create(entity1);
    }

    @Before
    public void testSetUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testRegisterSupplierWhenSupplierAlreadyExists() {

        try (MockedStatic<EntityBuilder> mockBuilder = org.mockito.Mockito.mockStatic(EntityBuilder.class)) {
            mockBuilder.when(EntityBuilder::buildSupplierEntity).thenReturn(new SupplierEntity("supplier", "5678", "email"));

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
        try (MockedStatic<EntityBuilder> mockBuilder = org.mockito.Mockito.mockStatic(EntityBuilder.class)) {
            mockBuilder.when(EntityBuilder::buildSupplierEntity).thenReturn(new SupplierEntity("supplier2", "5678", "email"));

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
