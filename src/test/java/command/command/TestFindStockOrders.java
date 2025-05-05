package command.command;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;
import putus.teddy.command.command.FindStockOrders;
import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.SupplierPurchaseEntity;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestFindStockOrders {
    ByteArrayOutputStream outContent;
    FindStockOrders command = new FindStockOrders();

    static SupplierPurchaseEntity entity1 = new SupplierPurchaseEntity("Supplier A", "2025-01-01", "item1", 10, 1.0);
    static SupplierPurchaseEntity entity2 = new SupplierPurchaseEntity("Supplier B", "2025-01-02", "item2", 20, 2.0);
    static SupplierPurchaseEntity entity3 = new SupplierPurchaseEntity("Supplier A", "2025-01-03", "item1", 10, 3.0);

    @BeforeClass
    public static void classSetUp() {
        FindStockOrders.supplierPurchaseRepository.create(entity1);
        FindStockOrders.supplierPurchaseRepository.create(entity2);
        FindStockOrders.supplierPurchaseRepository.create(entity3);
    }

    @Before
    public void testSetUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testFindOrders() {

        try (MockedStatic<QueryBuilder> mockedBuilder = org.mockito.Mockito.mockStatic(QueryBuilder.class)) {
            mockedBuilder.when(QueryBuilder::supplierPurchaseQuery).thenReturn(Map.of(
                    "supplierName", "Supplier A"
            ));

            command.execute();

            String header = "---------------------------------------------------------------------------------------------------------------------------------\n" +
                    "| ID                                   | SUPPLIER    | DATE        | ITEM NAME            | QUANTITY | PRICE/UNIT | TOTAL PRICE |\n" +
                    "---------------------------------------------------------------------------------------------------------------------------------" ;

            String output = outContent.toString();

            assertTrue(output.contains(header));
            assertTrue(output.contains(entity1.getSupplierName()));
            assertTrue(output.contains(entity3.getSupplierName()));


            assertFalse(output.contains(entity2.getSupplierName()));
        }
    }

    @Test
    public void testFindOrdersWithNoResults() {
        try (MockedStatic<QueryBuilder> mockedBuilder = org.mockito.Mockito.mockStatic(QueryBuilder.class)) {

            mockedBuilder.when(QueryBuilder::supplierPurchaseQuery).thenReturn(Map.of(
                    "name", "Supplier Z" //Incorrect field name and value
            ));

            command.execute();

            assertFalse(outContent.toString().contains(entity1.getSupplierName()));
            assertFalse(outContent.toString().contains(entity2.getSupplierName()));
            assertFalse(outContent.toString().contains(entity3.getSupplierName()));
        }
    }
}
