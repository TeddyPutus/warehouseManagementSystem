package command.command;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;
import putus.teddy.command.command.CustomerOrder;
import putus.teddy.command.command.RegisterItem;
import putus.teddy.data.builder.EntityBuilder;
import putus.teddy.data.entity.CustomerPurchaseEntity;
import putus.teddy.data.entity.FinancialEntity;
import putus.teddy.data.entity.InventoryEntity;
import putus.teddy.printer.Printer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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


        RegisterItem.inventoryRepository.deleteMany(Map.of());
        RegisterItem.financialRepository.deleteMany(Map.of());
        RegisterItem.customerPurchaseRepository.deleteMany(Map.of());

        inventoryEntity = new InventoryEntity("item1", 1, 10.0);
        financialEntity = new FinancialEntity("item1", 0, 0, 10.0, 0.0);

        RegisterItem.inventoryRepository.create(inventoryEntity);
        RegisterItem.financialRepository.create(financialEntity);
    }

    @Test
    public void testCustomerOrder() {
        try (
                MockedStatic<EntityBuilder> mockBuilder = org.mockito.Mockito.mockStatic(EntityBuilder.class);
        ) {
            mockBuilder.when(EntityBuilder::buildCustomerPurchaseEntity).thenReturn(new CustomerPurchaseEntity("customer", "item1", 1, "2025-01-01"));

            command.execute();

            String output = outContent.toString();

            assertTrue(output.contains("Order placed successfully."));

            assertEquals(1, RegisterItem.customerPurchaseRepository.findAll().toList().size());
            assertEquals(0, RegisterItem.inventoryRepository.findOne(Map.of("itemName", "item1")).getQuantity());

            assertEquals(1, RegisterItem.financialRepository.findOne(Map.of("itemName", "item1")).getQuantitySold());
            assertEquals(10.0, RegisterItem.financialRepository.findOne(Map.of("itemName", "item1")).getTotalRevenue(), 0.001);
        }
    }

    @Test
    public void testCustomerOrderNotEnoughStock() {
        try (
                MockedStatic<EntityBuilder> mockBuilder = org.mockito.Mockito.mockStatic(EntityBuilder.class);
        ) {
            mockBuilder.when(EntityBuilder::buildCustomerPurchaseEntity).thenReturn(new CustomerPurchaseEntity("customer", "item1", 2, "2025-01-01"));

            command.execute();

            String output = outContent.toString();

            assertTrue(output.contains("Not enough stock available."));

            assertEquals(0, RegisterItem.customerPurchaseRepository.findAll().toList().size());
            assertEquals(1, RegisterItem.inventoryRepository.findOne(Map.of("itemName", "item1")).getQuantity());
        }
    }

    @Test
    public void testCustomerOrderLowStockAlert() {
        try (
                MockedStatic<EntityBuilder> mockBuilder = org.mockito.Mockito.mockStatic(EntityBuilder.class);
        ) {
            mockBuilder.when(EntityBuilder::buildCustomerPurchaseEntity).thenReturn(new CustomerPurchaseEntity("customer", "item1", 1, "2025-01-01"));

            command.execute();

            String output = outContent.toString();

            assertTrue(output.contains("Stock for item1 is low. Please order more stock."));
        }
    }

    @Test
    public void testCustomerOrderWhenItemNotFound() {
        try (
                MockedStatic<EntityBuilder> mockBuilder = org.mockito.Mockito.mockStatic(EntityBuilder.class);
        ) {
            mockBuilder.when(EntityBuilder::buildCustomerPurchaseEntity).thenReturn(new CustomerPurchaseEntity("customer", "item2", 1, "2025-01-01"));

            command.execute();

            String output = outContent.toString();

            assertTrue(output.contains("Item does not exist. Please register item first."));

            assertEquals(0, RegisterItem.customerPurchaseRepository.findAll().toList().size());
        }
    }


    @Test
    public void testFinancialEntityNotFound() {
        try (
                MockedStatic<EntityBuilder> mockBuilder = org.mockito.Mockito.mockStatic(EntityBuilder.class);
        ) {
            mockBuilder.when(EntityBuilder::buildCustomerPurchaseEntity).thenReturn(new CustomerPurchaseEntity("customer", "item1", 1, "2025-01-01"));

            CustomerOrder.financialRepository.deleteMany(Map.of());

            command.execute();

            String output = outContent.toString();

            assertTrue(output.contains("Financial entity not found for item:"));

            assertEquals(0, RegisterItem.customerPurchaseRepository.findAll().toList().size());
        }
    }

}
