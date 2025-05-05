package command.command;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;
import putus.teddy.command.command.FindInventory;
import putus.teddy.command.command.FindSuppliers;
import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.SupplierEntity;
import putus.teddy.printer.Printer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestFindSupplier {
    static ByteArrayOutputStream outContent;
    FindSuppliers findSuppliers = new FindSuppliers();

    static SupplierEntity entity1 = new SupplierEntity("supplier1", "1234", "email");
    static SupplierEntity entity2 = new SupplierEntity("supplier2", "5678", "email2");
    static SupplierEntity entity3 = new SupplierEntity("supplier3", "1234", "email3");

    @BeforeClass
    public static void classSetUp() {
        FindInventory.supplierRepository.deleteMany(Map.of());
        FindInventory.supplierRepository.create(entity1);
        FindInventory.supplierRepository.create(entity2);
        FindInventory.supplierRepository.create(entity3);
        outContent = new ByteArrayOutputStream();
        Printer.setOutputStream(new PrintStream(outContent));
    }

    @Test
    public void testFindSupplier() {

        try (MockedStatic<QueryBuilder> mockedBuilder = org.mockito.Mockito.mockStatic(QueryBuilder.class)) {
            mockedBuilder.when(QueryBuilder::supplierQuery).thenReturn(Map.of(
                    "phoneNumber", "1234"
            ));

            findSuppliers.execute();

            String output = outContent.toString();

            assertTrue(output.contains(SupplierEntity.getTableHead()));
            assertTrue(output.contains("supplier1"));
            assertTrue(output.contains("supplier3"));


            assertFalse(output.contains("supplier2"));
        }
    }

    @Test
    public void testFindInventoryWithNoResults() {
        try (MockedStatic<QueryBuilder> mockedBuilder = org.mockito.Mockito.mockStatic(QueryBuilder.class)) {

            mockedBuilder.when(QueryBuilder::supplierQuery).thenReturn(Map.of(
                    "phoneNumber", "9999"
            ));

            findSuppliers.execute();

            assertFalse(outContent.toString().contains("supplier1"));
            assertFalse(outContent.toString().contains("supplier2"));
            assertFalse(outContent.toString().contains("supplier3"));
        }
    }
}
