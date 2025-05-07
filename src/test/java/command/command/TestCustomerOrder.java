package command.command;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;
import putus.teddy.command.command.Command;
import putus.teddy.command.command.CustomerOrder;
import putus.teddy.command.command.RegisterItem;
import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.CustomerPurchaseEntity;
import putus.teddy.data.entity.FinancialEntity;
import putus.teddy.data.entity.InventoryEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.printer.Printer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;

public class TestCustomerOrder {
    static ByteArrayOutputStream outContent;
    CustomerOrder command = new CustomerOrder();

    static InventoryEntity inventoryEntity = new InventoryEntity("item1", 1, 1.0);
    static FinancialEntity financialEntity = new FinancialEntity("item1", 0, 0, 10.0, 0.0);

    @BeforeClass
    public static void setUpClass() {
        outContent = new ByteArrayOutputStream();
        Printer.setOutputStream(new PrintStream(outContent));
    }

    @Before
    public void testSetUp() {


        RegisterItem.inventoryRepository.deleteMany(List.of(entity -> true));
        RegisterItem.financialRepository.deleteMany(List.of(entity -> true));
        RegisterItem.customerPurchaseRepository.deleteMany(List.of(entity -> true));

        inventoryEntity = new InventoryEntity("item1", 1, 10.0);
        financialEntity = new FinancialEntity("item1", 0, 0, 10.0, 0.0);

        RegisterItem.inventoryRepository.create(inventoryEntity);
        RegisterItem.financialRepository.create(financialEntity);
    }

    @Test
    public void testCustomerOrder() {
        try (
                MockedStatic<ValidatedInputParser> mockParser = org.mockito.Mockito.mockStatic(ValidatedInputParser.class);
        ) {
            mockParser.when(()-> ValidatedInputParser.parseString("name", true,1,15)).thenReturn("customer");
            mockParser.when(()-> ValidatedInputParser.parseString("itemName",true,1,15)).thenReturn("item1");
            mockParser.when(() -> ValidatedInputParser.parseQuantity(anyString(), anyBoolean())).thenReturn(1);

            Command.Result result = command.execute();

            String output = outContent.toString();

            assertTrue(output.contains("Order placed successfully."));
            assertEquals(Command.Result.SUCCESS, result);

            assertEquals(1, RegisterItem.customerPurchaseRepository.findAll().toList().size());
            assertEquals(0, (int) RegisterItem.inventoryRepository.findOne(QueryBuilder.searchInventoryByItemName("item1")).getQuantity());

            assertEquals(1, (int) RegisterItem.financialRepository.findOne(QueryBuilder.searchFinancial("item1")).getQuantitySold());
            assertEquals(10.0, RegisterItem.financialRepository.findOne(QueryBuilder.searchFinancial("item1")).getTotalRevenue(), 0.001);
        }
    }

    @Test
    public void testCustomerOrderNotEnoughStock() {
        try(
                MockedStatic<ValidatedInputParser> mockParser = org.mockito.Mockito.mockStatic(ValidatedInputParser.class);
        ) {
            mockParser.when(()-> ValidatedInputParser.parseString("name", true,1,15)).thenReturn("customer");
            mockParser.when(()-> ValidatedInputParser.parseString("itemName",true,1,15)).thenReturn("item1");
            mockParser.when(()-> ValidatedInputParser.parseQuantity(anyString(),anyBoolean())).thenReturn(2);

            Command.Result result = command.execute();

            String output = outContent.toString();

            assertTrue(output.contains("Not enough stock available."));
            assertEquals(Command.Result.FAILURE, result);

            assertEquals(0, RegisterItem.customerPurchaseRepository.findAll().toList().size());
            assertEquals(1, (int) RegisterItem.inventoryRepository.findOne(QueryBuilder.searchInventoryByItemName("item1")).getQuantity());
        }
    }

    @Test
    public void testCustomerOrderLowStockAlert() {

        try(
                MockedStatic<ValidatedInputParser> mockParser = org.mockito.Mockito.mockStatic(ValidatedInputParser.class);
        ) {

            mockParser.when(()-> ValidatedInputParser.parseString("name", true,1,15)).thenReturn("customer");
            mockParser.when(()-> ValidatedInputParser.parseString("itemName",true,1,15)).thenReturn("item1");
            mockParser.when(()-> ValidatedInputParser.parseQuantity(anyString(),anyBoolean())).thenReturn(1);

            Command.Result result = command.execute();
            assertEquals(Command.Result.SUCCESS, result);

            String output = outContent.toString();

            assertTrue(output.contains("Stock for item1 is low. Please order more stock."));
        }
    }

    @Test
    public void testCustomerOrderWhenItemNotFound() {

        try(
                MockedStatic<ValidatedInputParser> mockParser = org.mockito.Mockito.mockStatic(ValidatedInputParser.class);
        ) {
            mockParser.when(()-> ValidatedInputParser.parseString("name", true,1,15)).thenReturn("customer");
            mockParser.when(()-> ValidatedInputParser.parseString("itemName",true,1,15)).thenReturn("item2");
            mockParser.when(()-> ValidatedInputParser.parseQuantity(anyString(),anyBoolean())).thenReturn(2);
            Command.Result result = command.execute();
            assertEquals(Command.Result.FAILURE, result);

            String output = outContent.toString();

            assertTrue(output.contains("Item does not exist. Please register item first."));

            assertEquals(0, RegisterItem.customerPurchaseRepository.findAll().toList().size());
        }
    }


    @Test
    public void testFinancialEntityNotFound() {

        try(
                MockedStatic<ValidatedInputParser> mockParser = org.mockito.Mockito.mockStatic(ValidatedInputParser.class);
        ) {
            mockParser.when(()-> ValidatedInputParser.parseString("name", true,1,15)).thenReturn("customer");
            mockParser.when(()-> ValidatedInputParser.parseString("itemName",true,1,15)).thenReturn("item1");
            mockParser.when(()-> ValidatedInputParser.parseQuantity(anyString(),anyBoolean())).thenReturn(1);

            CustomerOrder.financialRepository.deleteMany(List.of(entity -> true));

            Command.Result result = command.execute();
            assertEquals(Command.Result.FAILURE, result);

            String output = outContent.toString();

            assertTrue(output.contains("Financial entity not found for item:"));

            assertEquals(0, RegisterItem.customerPurchaseRepository.findAll().toList().size());
        }
    }

}
