package command.command;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;
import putus.teddy.command.command.FindInventory;
import putus.teddy.command.command.FindSuppliers;
import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.SupplierEntity;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestFindSupplier {
    ByteArrayOutputStream outContent;
    FindSuppliers findSuppliers = new FindSuppliers();

    static SupplierEntity entity1 = new SupplierEntity("item1", "1234", "email");
    static SupplierEntity entity2 = new SupplierEntity("item2", "5678", "email2");
    static SupplierEntity entity3 = new SupplierEntity("item3", "1234", "email3");

    @BeforeClass
    public static void classSetUp() {
        FindInventory.supplierRepository.deleteMany(Map.of());
        FindInventory.supplierRepository.create(entity1);
        FindInventory.supplierRepository.create(entity2);
        FindInventory.supplierRepository.create(entity3);
    }

    @Before
    public void testSetUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testFindSupplier() {

        try (MockedStatic<QueryBuilder> mockedBuilder = org.mockito.Mockito.mockStatic(QueryBuilder.class)) {
            mockedBuilder.when(QueryBuilder::supplierQuery).thenReturn(Map.of(
                    "phoneNumber", "1234"
            ));

            findSuppliers.execute();

            String header = "-------------------------------------------------------------------------------------------\n" +
                    "| ID                                   | NAME       |        PHONE | EMAIL                |\n" +
                    "-------------------------------------------------------------------------------------------" ;

            String output = outContent.toString();

            assertTrue(output.contains(header));
            assertTrue(output.contains("item1"));
            assertTrue(output.contains("item3"));


            assertFalse(output.contains("item2"));
        }
    }

    @Test
    public void testFindInventoryWithNoResults() {
        try (MockedStatic<QueryBuilder> mockedBuilder = org.mockito.Mockito.mockStatic(QueryBuilder.class)) {

            mockedBuilder.when(QueryBuilder::supplierQuery).thenReturn(Map.of(
                    "phoneNumber", "9999"
            ));

            findSuppliers.execute();

            assertFalse(outContent.toString().contains("item1"));
            assertFalse(outContent.toString().contains("item2"));
            assertFalse(outContent.toString().contains("item3"));
        }
    }
}
