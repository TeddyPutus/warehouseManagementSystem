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
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.printer.Printer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;

public class TestFindOrders {
    static ByteArrayOutputStream outContent;
    FindOrders command = new FindOrders();

    static CustomerPurchaseEntity entity1 = new CustomerPurchaseEntity("customer1", "item1", 10, "2025-01-01");
    static CustomerPurchaseEntity entity2 = new CustomerPurchaseEntity("customer2", "item2", 20, "2025-01-02");
    static CustomerPurchaseEntity entity3 = new CustomerPurchaseEntity("customer3", "item1", 10, "2025-01-03");

    @BeforeClass
    public static void classSetUp() {
        FindInventory.customerPurchaseRepository.deleteMany(List.of(entity -> true));
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

        try (MockedStatic<ValidatedInputParser> mockParser = org.mockito.Mockito.mockStatic(ValidatedInputParser.class)) {
            mockParser.when(()-> ValidatedInputParser.parseString("name", true,1,15)).thenReturn("");
            mockParser.when(()-> ValidatedInputParser.parseString("itemName", true,1,15)).thenReturn("item1");
            mockParser.when(()-> ValidatedInputParser.parseAmount(anyString(),anyBoolean())).thenReturn(Double.MIN_VALUE);
            mockParser.when(()-> ValidatedInputParser.parseQuantity(anyString(),anyBoolean())).thenReturn(10);

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
    public void testFindOrdersWithNoResults() { try (MockedStatic<ValidatedInputParser> mockParser = org.mockito.Mockito.mockStatic(ValidatedInputParser.class)) {
        mockParser.when(()-> ValidatedInputParser.parseString("name", false,1,15)).thenReturn("");
        mockParser.when(()-> ValidatedInputParser.parseString("itemName", false,1,15)).thenReturn("itemxyzabc");
        mockParser.when(()-> ValidatedInputParser.parseAmount(anyString(),anyBoolean())).thenReturn(Double.MIN_VALUE);
        mockParser.when(()-> ValidatedInputParser.parseQuantity(anyString(),anyBoolean())).thenReturn(10);


        Command.Result result = command.execute();
        assertEquals(Command.Result.SUCCESS, result);

        assertFalse(outContent.toString().contains(entity1.getCustomerName()));
        assertFalse(outContent.toString().contains(entity2.getCustomerName()));
        assertFalse(outContent.toString().contains(entity3.getCustomerName()));
    }
    }
}
