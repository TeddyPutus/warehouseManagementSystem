package command.command;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;
import putus.teddy.command.command.RegisterItem;
import putus.teddy.data.builder.EntityBuilder;
import putus.teddy.data.entity.InventoryEntity;
import putus.teddy.data.entity.SupplierEntity;
import putus.teddy.printer.Printer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.Assert.*;

public class TestRegisterItem {
    static ByteArrayOutputStream outContent;
    RegisterItem command = new RegisterItem();

    static InventoryEntity entity1 = new InventoryEntity("item1", 1, 1.0);

    @BeforeClass
    public static void classSetUp() {
        RegisterItem.inventoryRepository.deleteMany(Map.of());
        RegisterItem.financialRepository.deleteMany(Map.of());
        RegisterItem.inventoryRepository.create(entity1);
        outContent = new ByteArrayOutputStream();
        Printer.setOutputStream(new PrintStream(outContent));
    }

    @Before
    public void testSetUp() {
        outContent.reset();
    }

    @Test
    public void testRegisterItemWhenItemAlreadyExists() {
        try(
                MockedStatic<EntityBuilder> mockBuilder = org.mockito.Mockito.mockStatic(EntityBuilder.class);
        ) {
            mockBuilder.when(EntityBuilder::buildInventoryEntity).thenReturn(new InventoryEntity("item1", 2, 2.0));

            boolean result = command.execute();
            String output = outContent.toString();
            assertTrue(output.contains("Item already exists."));
            assertFalse(output.contains("Item registered successfully."));
            assertEquals(1, RegisterItem.inventoryRepository.findAll().toList().size());
            assertNull(RegisterItem.financialRepository.findOne(Map.of("itemName", "item1")));
            assertFalse(result);
        }
    }

    @Test
    public void testRegisterItem() {
        try(
                MockedStatic<EntityBuilder> mockBuilder = org.mockito.Mockito.mockStatic(EntityBuilder.class);
        ) {

            mockBuilder.when(EntityBuilder::buildInventoryEntity).thenReturn(new InventoryEntity("item2", 2, 2.0));

            boolean result = command.execute();

            String output = outContent.toString();
            assertTrue(output.contains("Item registered successfully."));
            assertFalse(output.contains("Item already exists."));
            assertEquals(2, RegisterItem.inventoryRepository.findAll().toList().size());
            assertNotNull(RegisterItem.inventoryRepository.findOne(Map.of("itemName", "item2")));
            assertNotNull(RegisterItem.financialRepository.findOne(Map.of("itemName", "item2")));
            assertFalse(result);
        }
    }

}
