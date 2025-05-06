package data.builder;

import org.junit.Test;
import org.mockito.MockedStatic;
import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.parser.InputParser;
import putus.teddy.data.parser.ValidatedInputParser;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestQueryBuilder {
    @Test
    public void testSupplierQuery() {
        try (MockedStatic<ValidatedInputParser> utilities = mockStatic(ValidatedInputParser.class)) {
            utilities.when(() -> ValidatedInputParser.parseString(anyString(), anyBoolean(), anyInt(), anyInt())).thenReturn("Test name").thenReturn("Test phone").thenReturn("Test email");

            var query = QueryBuilder.supplierQuery();
            assertEquals("Test name", query.get("name"));
            assertEquals("Test phone", query.get("phoneNumber"));
            assertEquals("Test email", query.get("email"));
        }
    }

    @Test
    public void testSupplierQueryOptionalFields() {
        try (MockedStatic<ValidatedInputParser> utilities = mockStatic(ValidatedInputParser.class)) {
            utilities.when(() -> ValidatedInputParser.parseString(anyString(), anyBoolean(), anyInt(), anyInt())).thenReturn("");

            var query = QueryBuilder.supplierQuery();
            assertTrue(query.isEmpty());
        }
    }

    @Test
    public void testInventoryQuery() {
        try (MockedStatic<ValidatedInputParser> utilities = mockStatic(ValidatedInputParser.class)) {
            utilities.when(() -> ValidatedInputParser.parseString(anyString(), anyBoolean(), anyInt(), anyInt())).thenReturn("Test String");
            utilities.when(() -> ValidatedInputParser.parseAmount(anyString(), anyBoolean())).thenReturn(10.0);
            utilities.when(() -> ValidatedInputParser.parseQuantity(anyString(), anyBoolean())).thenReturn(5);

            var query = QueryBuilder.inventoryQuery();
            assertEquals("Test String", query.get("itemName"));
            assertEquals(5, query.get("quantity"));
            assertEquals(10.0, query.get("pricePerUnit"));
        }
    }

    @Test
    public void testInventoryQueryOptionalFields() {
        try (MockedStatic<ValidatedInputParser> utilities = mockStatic(ValidatedInputParser.class)) {
            utilities.when(() -> ValidatedInputParser.parseString(anyString(), anyBoolean(), anyInt(), anyInt())).thenReturn("");
            utilities.when(() -> ValidatedInputParser.parseAmount(anyString(), anyBoolean())).thenReturn(Double.MIN_VALUE);
            utilities.when(() -> ValidatedInputParser.parseQuantity(anyString(), anyBoolean())).thenReturn(Integer.MIN_VALUE);

            var query = QueryBuilder.inventoryQuery();
            assertTrue(query.isEmpty());
        }
    }

    @Test
    public void testCustomerOrderQuery() {
        try (MockedStatic<ValidatedInputParser> utilities = mockStatic(ValidatedInputParser.class)) {
            utilities.when(() -> ValidatedInputParser.parseString(anyString(), anyBoolean(), anyInt(), anyInt())).thenReturn("Test customer").thenReturn("Test item");
            utilities.when(() -> ValidatedInputParser.parseQuantity(anyString(), anyBoolean())).thenReturn(5);

            var query = QueryBuilder.customerOrderQuery();
            assertEquals("Test customer", query.get("customerName"));
            assertEquals("Test item", query.get("itemName"));
            assertEquals(5, query.get("quantity"));
        }
    }

    @Test
    public void testCustomerOrderQueryOptionalFields() {
        try (MockedStatic<ValidatedInputParser> utilities = mockStatic(ValidatedInputParser.class)) {
            utilities.when(() -> ValidatedInputParser.parseString(anyString(), anyBoolean(), anyInt(), anyInt())).thenReturn("");
            utilities.when(() -> ValidatedInputParser.parseQuantity(anyString(), anyBoolean())).thenReturn(Integer.MIN_VALUE);
            utilities.when(() -> ValidatedInputParser.parseAmount(anyString(), anyBoolean())).thenReturn(Double.MIN_VALUE);

            var query = QueryBuilder.customerOrderQuery();
            assertTrue(query.isEmpty());
        }
    }

    @Test
    public void testCustomerOrderQueryWithDate() {
        try (MockedStatic<ValidatedInputParser> utilities = mockStatic(ValidatedInputParser.class)) {
            utilities.when(() -> ValidatedInputParser.parseString(anyString(), anyBoolean(), anyInt(), anyInt())).thenReturn("2023-10-01");
            utilities.when(() -> ValidatedInputParser.parseAmount(anyString(), anyBoolean())).thenReturn(10.0);
            utilities.when(() -> ValidatedInputParser.parseQuantity(anyString(), anyBoolean())).thenReturn(5);

            var query = QueryBuilder.customerOrderQuery();
            assertEquals("2023-10-01", query.get("date"));
        }
    }

    @Test
    public void testCustomerOrderQueryWithEmptyDate() {
        try (MockedStatic<ValidatedInputParser> utilities = mockStatic(ValidatedInputParser.class)) {
            utilities.when(() -> ValidatedInputParser.parseString(anyString(), anyBoolean(), anyInt(), anyInt())).thenReturn("");
            utilities.when(() -> ValidatedInputParser.parseAmount(anyString(), anyBoolean())).thenReturn(Double.MIN_VALUE);
            utilities.when(() -> ValidatedInputParser.parseQuantity(anyString(), anyBoolean())).thenReturn(Integer.MIN_VALUE);

            var query = QueryBuilder.customerOrderQuery();
            assertTrue(query.isEmpty());
        }
    }


}
