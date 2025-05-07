package command.command;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;
import putus.teddy.command.command.Command;
import putus.teddy.command.command.FindInventory;
import putus.teddy.command.command.FindOrders;
import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.CustomerPurchaseEntity;
import putus.teddy.printer.Printer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.Assert.*;

public class TestFindOrders {
    static ByteArrayOutputStream outContent;
    FindOrders command = new FindOrders();

    static CustomerPurchaseEntity entity1 = new CustomerPurchaseEntity("customer1", "item1", 10, "2025-01-01");
    static CustomerPurchaseEntity entity2 = new CustomerPurchaseEntity("customer2", "item2", 20, "2025-01-02");
    static CustomerPurchaseEntity entity3 = new CustomerPurchaseEntity("customer3", "item1", 10, "2025-01-03");

    @BeforeClass
    public static void classSetUp() {
        FindInventory.customerPurchaseRepository.deleteMany(Map.of());
        FindInventory.customerPurchaseRepository.create(entity1);
        FindInventory.customerPurchaseRepository.create(entity2);
        FindInventory.customerPurchaseRepository.create(entity3);

        outContent = new ByteArrayOutputStream();
        Printer.setOutputStream(new PrintStream(outContent));
    }

    @Before
    public void testSetUp() {
        outContent.reset();
    }

    @Test
    public void testFindOrders() {

        try (MockedStatic<QueryBuilder> mockedBuilder = org.mockito.Mockito.mockStatic(QueryBuilder.class)) {
            mockedBuilder.when(QueryBuilder::customerOrderQuery).thenReturn(Map.of(
                    "itemName", "item1"
            ));

            Command.Result result = command.execute();
            assertEquals(Command.Result.SUCCESS, result);

            String output = outContent.toString();

            assertTrue(output.contains(CustomerPurchaseEntity.getTableHead()));
            assertTrue(output.contains(entity1.getCustomerName()));
            assertTrue(output.contains(entity3.getCustomerName()));


            assertFalse(output.contains(entity2.getCustomerName()));
        }
    }

    @Test
    public void testFindOrdersWithNoResults() {
        try (MockedStatic<QueryBuilder> mockedBuilder = org.mockito.Mockito.mockStatic(QueryBuilder.class)) {

            mockedBuilder.when(QueryBuilder::customerOrderQuery).thenReturn(Map.of(
                    "phoneNumber", "9999"
            ));

            Command.Result result = command.execute();
            assertEquals(Command.Result.SUCCESS, result);

            assertFalse(outContent.toString().contains(entity1.getCustomerName()));
            assertFalse(outContent.toString().contains(entity2.getCustomerName()));
            assertFalse(outContent.toString().contains(entity3.getCustomerName()));
        }
    }
}
