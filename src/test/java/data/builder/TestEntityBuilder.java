package data.builder;

import org.junit.Test;
import org.mockito.MockedStatic;
import putus.teddy.data.builder.EntityBuilder;
import putus.teddy.data.parser.InputParser;
import putus.teddy.data.parser.ValidatedInputParser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mockStatic;

public class TestEntityBuilder {
    @Test
    public void testBuildInventoryEntity() {
        try (MockedStatic<ValidatedInputParser> utilities = mockStatic(ValidatedInputParser.class)) {
            utilities.when(() -> ValidatedInputParser.parseString(anyString(), anyBoolean(), anyInt(), anyInt())).thenReturn("Test item");
            utilities.when(() -> ValidatedInputParser.parseAmount(anyString(), anyBoolean())).thenReturn(10.0);

            var inventoryEntity = EntityBuilder.buildInventoryEntity();
            assertNotNull(inventoryEntity);
            assertEquals("Test item", inventoryEntity.getItemName());
            assertEquals(0, inventoryEntity.getQuantity());
            assertEquals(10.0, inventoryEntity.getPricePerUnit(), 0.0);
        }
    }

    @Test
    public void testBuildSupplierEntity() {
        try (MockedStatic<ValidatedInputParser> utilities = mockStatic(ValidatedInputParser.class)) {
            utilities.when(() -> ValidatedInputParser.parseString(anyString(), anyBoolean(), anyInt(), anyInt())).thenReturn("Test name").thenReturn("1234").thenReturn("test@test.com");

            var supplierEntity = EntityBuilder.buildSupplierEntity();
            assertNotNull(supplierEntity);
            assertEquals("Test name", supplierEntity.getName());

        }
    }

    @Test
    public void testBuildSupplierPurchaseEntity() {
        try (MockedStatic<ValidatedInputParser> utilities = mockStatic(ValidatedInputParser.class)) {
            utilities.when(() -> ValidatedInputParser.parseString(anyString(), anyBoolean(), anyInt(), anyInt())).thenReturn("Test supplier").thenReturn("Test item");
            utilities.when(() -> ValidatedInputParser.parseQuantity(anyString(), anyBoolean())).thenReturn(10);
            utilities.when(() -> ValidatedInputParser.parseAmount(anyString(), anyBoolean())).thenReturn(120.0);

            var supplierPurchaseEntity = EntityBuilder.buildSupplierPurchaseEntity();
            assertNotNull(supplierPurchaseEntity);
            assertEquals("Test supplier", supplierPurchaseEntity.getSupplierName());
            assertEquals("Test item", supplierPurchaseEntity.getItemName());
            assertEquals(10, supplierPurchaseEntity.getQuantity());
            assertEquals(120.0, supplierPurchaseEntity.getPricePerUnit(), 0.001);
            assertEquals(1200.0, supplierPurchaseEntity.getTotalPrice(), 0.001);
        }
    }

    @Test
    public void testBuildCustomerPurchaseEntity() {
        try (MockedStatic<ValidatedInputParser> utilities = mockStatic(ValidatedInputParser.class)) {
            utilities.when(() -> ValidatedInputParser.parseString(anyString(), anyBoolean(), anyInt(), anyInt())).thenReturn("Test customer").thenReturn("Test item");
            utilities.when(() -> ValidatedInputParser.parseQuantity(anyString(), anyBoolean())).thenReturn(10);

            var customerPurchaseEntity = EntityBuilder.buildCustomerPurchaseEntity();
            assertNotNull(customerPurchaseEntity);
            assertEquals("Test customer", customerPurchaseEntity.getCustomerName());
            assertEquals("Test item", customerPurchaseEntity.getItemName());
            assertEquals(10, customerPurchaseEntity.getQuantity());
        }
    }
}
