package data.builder;

import org.junit.Test;
import org.mockito.MockedStatic;
import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.parser.InputParser;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestQueryBuilder {
    @Test
    public void testSupplierQuery() {
        System.out.println(org.mockito.internal.configuration.plugins.Plugins.getMockMaker().getClass());
        try (MockedStatic<InputParser> utilities = mockStatic(InputParser.class)) {
            utilities.when(() -> InputParser.parseString(anyString(), anyBoolean())).thenReturn("Test name").thenReturn("Test phone").thenReturn("Test email");

            var query = QueryBuilder.supplierQuery();
            assertEquals("Test name", query.get("name"));
            assertEquals("Test phone", query.get("phoneNumber"));
            assertEquals("Test email", query.get("email"));
        }
    }

    @Test
    public void testSupplierQueryOptionalFields() {
        try (MockedStatic<InputParser> utilities = mockStatic(InputParser.class)) {
            utilities.when(() -> InputParser.parseString(anyString(), anyBoolean())).thenReturn("");

            var query = QueryBuilder.supplierQuery();
            assertTrue(query.isEmpty());
        }
    }

    @Test
    public void testInventoryQuery() {
        try (MockedStatic<InputParser> utilities = mockStatic(InputParser.class)) {
            utilities.when(() -> InputParser.parseString(anyString(), anyBoolean())).thenReturn("Test String");
            utilities.when(() -> InputParser.parseDouble(anyString(), anyBoolean())).thenReturn(10.0);
            utilities.when(() -> InputParser.parseInt(anyString(), anyBoolean())).thenReturn(5);

            var query = QueryBuilder.inventoryQuery();
            assertEquals("Test String", query.get("itemName"));
            assertEquals(5, query.get("quantity"));
            assertEquals(10.0, query.get("price"));
        }
    }

    @Test
    public void testInventoryQueryOptionalFields() {
        try (MockedStatic<InputParser> utilities = mockStatic(InputParser.class)) {
            utilities.when(() -> InputParser.parseString(anyString(), anyBoolean())).thenReturn("");
            utilities.when(() -> InputParser.parseDouble(anyString(), anyBoolean())).thenReturn(-1.0);
            utilities.when(() -> InputParser.parseInt(anyString(), anyBoolean())).thenReturn(-1);

            var query = QueryBuilder.inventoryQuery();
            assertTrue(query.isEmpty());
        }
    }

    @Test
    public void testCustomerOrderQuery() {
        try (MockedStatic<InputParser> utilities = mockStatic(InputParser.class)) {
            utilities.when(() -> InputParser.parseString(anyString(), anyBoolean())).thenReturn("Test customer").thenReturn("Test item");
            utilities.when(() -> InputParser.parseInt(anyString(), anyBoolean())).thenReturn(5);

            var query = QueryBuilder.customerOrderQuery();
            assertEquals("Test customer", query.get("customerName"));
            assertEquals("Test item", query.get("itemName"));
            assertEquals(5, query.get("quantity"));
        }
    }

    @Test
    public void testCustomerOrderQueryOptionalFields() {
        try (MockedStatic<InputParser> utilities = mockStatic(InputParser.class)) {
            utilities.when(() -> InputParser.parseString(anyString(), anyBoolean())).thenReturn("");
            utilities.when(() -> InputParser.parseInt(anyString(), anyBoolean())).thenReturn(-1);

            var query = QueryBuilder.customerOrderQuery();
            assertTrue(query.isEmpty());
        }
    }

    @Test
    public void testCustomerOrderQueryWithDate() {
        try (MockedStatic<InputParser> utilities = mockStatic(InputParser.class)) {
            utilities.when(() -> InputParser.parseString(anyString(), anyBoolean())).thenReturn("2023-10-01");
            utilities.when(() -> InputParser.parseDouble(anyString(), anyBoolean())).thenReturn(10.0);
            utilities.when(() -> InputParser.parseInt(anyString(), anyBoolean())).thenReturn(5);

            var query = QueryBuilder.customerOrderQuery();
            assertEquals("2023-10-01", query.get("date"));
        }
    }

    @Test
    public void testCustomerOrderQueryWithEmptyDate() {
        try (MockedStatic<InputParser> utilities = mockStatic(InputParser.class)) {
            utilities.when(() -> InputParser.parseString(anyString(), anyBoolean())).thenReturn("");
            utilities.when(() -> InputParser.parseDouble(anyString(), anyBoolean())).thenReturn(-1.0);
            utilities.when(() -> InputParser.parseInt(anyString(), anyBoolean())).thenReturn(-1);

            var query = QueryBuilder.customerOrderQuery();
            assertTrue(query.isEmpty());
        }
    }


}
