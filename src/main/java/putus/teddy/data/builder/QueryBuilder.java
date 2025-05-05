package putus.teddy.data.builder;

import putus.teddy.command.parser.CommandParser;
import putus.teddy.data.parser.InputParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class QueryBuilder {
    public static Map<String, Object> supplierQuery() {
        return filterQuery(Map.of(
                "name", InputParser.parseString("name", false),
                "contactNumber", InputParser.parseString("phone number", false),
                "email", InputParser.parseString("email", false)
        ));
    }

    public static Map<String, Object> inventoryQuery() {
        return filterQuery(Map.of(
                "name", InputParser.parseString("name", false),
                "quantity", InputParser.parseInt("quantity", false),
                "price", InputParser.parseDouble("price", false)
        ));
    }

    public static Map<String, Object> customerOrderQuery() {
        return filterQuery(Map.of(
                "customerName", InputParser.parseString("name", false),
                "itemName", InputParser.parseString("item name", false),
                "quantity", InputParser.parseInt("quantity", false),
                "date", InputParser.parseString("date", false)
        ));
    }

    public static Map<String, Object> supplierPurchaseQuery() {
        return filterQuery(Map.of(
                "supplierName", InputParser.parseString("supplier name", false),
                "itemName", InputParser.parseString("item name", false),
                "quantity", InputParser.parseInt("quantity", false),
                "price", InputParser.parseDouble("price", false),
                "date", InputParser.parseString("date", false)
        ));
    }

    private static Map<String, Object> filterQuery(Map<String, Object> query){
        Map<String, Object> filteredQuery = new HashMap<>(query);

        filteredQuery.entrySet().removeIf(entry -> {
            Object value = entry.getValue();
            if (value instanceof String) {
                return ((String) value).isEmpty();
            } else if (value instanceof Number) {
                return ((Number) value).doubleValue() < 0;
            }
            return false;
        });


        return filteredQuery;
    }
}

