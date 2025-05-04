package putus.teddy.data.builder;

import putus.teddy.command.parser.CommandParser;
import putus.teddy.data.parser.InputParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class QueryBuilder {
    public static Map<String, Object> supplierQuery() {
        String name = InputParser.parseString("name", false);
        String phoneNumber = InputParser.parseString("phone number", false);
        String email = InputParser.parseString("email", false);

        HashMap<String, Object> query = new HashMap<>();

        if (!name.isEmpty()) {
            query.put("name", name);
        }
        if (!phoneNumber.isEmpty()) {
            query.put("phoneNumber", phoneNumber);
        }
        if (!email.isEmpty()) {
            query.put("email", email);
        }

        return query;
    }

    public static Map<String, Object> inventoryQuery() {
        String name = InputParser.parseString("name", false);
        int quantity = InputParser.parseInt("quantity", false);
        double price = InputParser.parseDouble("price", false);

        HashMap<String, Object> query = new HashMap<>();

        if (!name.isEmpty()) {
            query.put("itemName", name);
        }
        if (quantity != -1) {
            query.put("quantity", quantity);
        }
        if (price > 0) {
            query.put("price", price);
        }

        return query;
    }

    public static Map<String, Object> customerOrderQuery() {
        String name = InputParser.parseString("name", false);
        String itemName = InputParser.parseString("item name", false);
        int quantity = InputParser.parseInt("quantity", true);
        String date = InputParser.parseString("date", false);

        HashMap<String, Object> query = new HashMap<>();

        if (!name.isEmpty()) {
            query.put("customerName", name);
        }
        if (!itemName.isEmpty()) {
            query.put("itemName", itemName);
        }
        if (quantity != -1) {
            query.put("quantity", quantity);
        }
        if (!date.isEmpty()) {
            query.put("date", date);
        }

        return query;
    }

    public static Map<String, Object> supplierPurchaseQuery() {
        String supplierName = InputParser.parseString("supplier name", false);
        String itemName = InputParser.parseString("item name", false);
        int quantity = InputParser.parseInt("quantity", false);
        double price = InputParser.parseDouble("price", false);
        String date = InputParser.parseString("date", false);

        HashMap<String, Object> query = new HashMap<>();

        if (!supplierName.isEmpty()) {
            query.put("supplierName", supplierName);
        }
        if (!itemName.isEmpty()) {
            query.put("itemName", itemName);
        }
        if (quantity != -1) {
            query.put("quantity", quantity);
        }
        if (price > 0) {
            query.put("price", price);
        }
        if (!date.isEmpty()) {
            query.put("date", date);
        }

        return query;
    }
}
