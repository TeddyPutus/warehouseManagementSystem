package putus.teddy.data.builder;

import putus.teddy.data.parser.ValidatedInputParser;

import java.util.HashMap;
import java.util.Map;

public class QueryBuilder {
    public static Map<String, Object> supplierQuery() {
        return filterQuery(Map.of(
                "name", ValidatedInputParser.parseString("name", false, 1,15),
                "phoneNumber", ValidatedInputParser.parseString("phone number", false,1,12),
                "email", ValidatedInputParser.parseString("email", false,1,20)
        ));
    }

    public static Map<String, Object> inventoryQuery() {
        return filterQuery(Map.of(
                "name", ValidatedInputParser.parseString("name", false,1,15),
                "quantity", ValidatedInputParser.parseQuantity("quantity", false),
                "price", ValidatedInputParser.parseAmount("price", false)
        ));
    }

    public static Map<String, Object> customerOrderQuery() {
        return filterQuery(Map.of(
                "customerName", ValidatedInputParser.parseString("name", false,1,15),
                "itemName", ValidatedInputParser.parseString("item name", false,1,15),
                "quantity", ValidatedInputParser.parseQuantity("quantity", false),
                "date", ValidatedInputParser.parseString("date", false,1,10)
        ));
    }

    public static Map<String, Object> supplierPurchaseQuery() {
        return filterQuery(Map.of(
                "supplierName", ValidatedInputParser.parseString("supplier name", false,1,15),
                "itemName", ValidatedInputParser.parseString("item name", false,1,15),
                "quantity", ValidatedInputParser.parseQuantity("quantity", false),
                "price", ValidatedInputParser.parseAmount("price", false),
                "date", ValidatedInputParser.parseString("date", false, 1,10)
        ));
    }

    private static Map<String, Object> filterQuery(Map<String, Object> query){
        Map<String, Object> filteredQuery = new HashMap<>(query);

        filteredQuery.entrySet().removeIf(entry -> {
            Object value = entry.getValue();
            if (value instanceof String) {
                return ((String) value).isEmpty();
            } else if (value instanceof Double) {
                return ((Double) value)  == Double.MIN_VALUE;
            }else if (value instanceof Integer) {
                return ((Integer) value) == Integer.MIN_VALUE;
            }
            return false;
        });


        return filteredQuery;
    }
}

