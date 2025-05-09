package putus.teddy.data.builder;

import putus.teddy.data.entity.*;
import java.util.List;
import java.util.function.Predicate;

/**
 * QueryBuilder is a utility class that provides methods to build queries for searching
 * different entities in the repository. It generates lists of predicates that the repository uses to filter results.
 */
public class QueryBuilder {

    /**
     * Checks if a string is empty or null.
     *
     * @param str The string to check.
     * @return true if the string is empty or null, false otherwise.
     */
    public static boolean isEmptyString(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * Checks if an integer is empty or null.
     *
     * @param integer The integer to check.
     * @return true if the integer is empty or null, false otherwise.
     */
    public static boolean isEmptyInteger(Integer integer) {
        return integer == null || integer.equals(Integer.MIN_VALUE);
    }

    /**
     * Checks if a double is empty or null.
     *
     * @param doubleValue The double to check.
     * @return true if the double is empty or null, false otherwise.
     */
    public static boolean isEmptyDouble(Double doubleValue) {
        return doubleValue == null || doubleValue.equals(Double.MIN_VALUE);
    }

    /**
     * Generates a list of predicates to search for suppliers based on the provided parameters.
     *
     * @param name The name of the supplier.
     * @param phoneNumber The phone number of the supplier.
     * @param email The email of the supplier.
     * @return A list of predicates that can be used to filter suppliers.
     */
    public static List<Predicate<SupplierEntity>> searchSupplier(String name, String phoneNumber, String email) {
       return List.of(
               entity -> QueryBuilder.isEmptyString(name) || entity.getName().equals(name),
               entity -> QueryBuilder.isEmptyString(phoneNumber) || entity.getPhoneNumber().equals(phoneNumber),
               entity -> QueryBuilder.isEmptyString(email) ||entity.getEmail().equals(email)
       );
    }

    /**
     * Generates a list of predicates to search for suppliers based on the provided ID.
     *
     * @param id The ID of the supplier.
     * @return A list of predicates that can be used to filter suppliers by ID.
     */
    public static List<Predicate<SupplierEntity>> searchSupplierById(String id) {
        return List.of(
                entity -> QueryBuilder.isEmptyString(id) || entity.getId().equals(id)
        );
    }

    /**
     * Generates a list of predicates to search for suppliers based on the provided name.
     *
     * @param name The name of the supplier.
     * @return A list of predicates that can be used to filter suppliers by name.
     */
    public static List<Predicate<SupplierEntity>> searchSupplierByName(String name) {
        return List.of(
                entity -> QueryBuilder.isEmptyString(name) || entity.getName().equals(name)
        );
    }

    /**
     * Generates a list of predicates to search for inventory items based on the provided parameters.
     *
     * @param name The name of the item.
     * @param quantity The quantity of the item.
     * @param price The price of the item.
     * @return A list of predicates that can be used to filter inventory items.
     */
    public static List<Predicate<InventoryEntity>> searchInventory(String name, Integer quantity, Double price) {
        return List.of(
                entity -> QueryBuilder.isEmptyString(name) || entity.getItemName().equals(name),
                entity -> QueryBuilder.isEmptyInteger(quantity) || entity.getQuantity().equals(quantity),
                entity -> QueryBuilder.isEmptyDouble(price) || DataEntity.compareDoubles(entity.getPricePerUnit(), price)
        );
    }

    /**
     * Generates a list of predicates to search for inventory items based on the provided name.
     *
     * @param itemName The name of the item.
     * @return A list of predicates that can be used to filter inventory items by name.
     */
    public static List<Predicate<InventoryEntity>> searchInventoryByItemName(String itemName) {
        return List.of(
                entity -> QueryBuilder.isEmptyString(itemName) || entity.getItemName().equals(itemName)
        );
    }

    /**
     * Generates a list of predicates to search for customer orders based on the provided parameters.
     *
     * @param customerName The name of the customer.
     * @param itemName The name of the item.
     * @param quantity The quantity of the item.
     * @param date The date of the order.
     * @return A list of predicates that can be used to filter customer orders.
     */
    public static List<Predicate<CustomerPurchaseEntity>> searchCustomerOrder(String customerName, String itemName, Integer quantity, String date) {
        return List.of(
                entity -> QueryBuilder.isEmptyString(customerName) || entity.getCustomerName().equals(customerName),
                entity -> QueryBuilder.isEmptyString(itemName) || entity.getItemName().equals(itemName),
                entity -> QueryBuilder.isEmptyInteger(quantity) || entity.getQuantity().equals(quantity),
                entity -> QueryBuilder.isEmptyString(date) || entity.getPurchaseDate().equals(date)
        );
    }

    /**
     * Generates a list of predicates to search for supplier purchases based on the provided parameters.
     *
     * @param supplierName The name of the supplier.
     * @param itemName The name of the item.
     * @param quantity The quantity of the item.
     * @param price The price of the item.
     * @param date The date of the purchase.
     * @return A list of predicates that can be used to filter supplier purchases.
     */
    public static List<Predicate<SupplierPurchaseEntity>> searchSupplierPurchase(String supplierName, String itemName, Integer quantity, Double price, String date) {
        return List.of(
                entity -> QueryBuilder.isEmptyString(supplierName)|| entity.getSupplierName().equals(supplierName),
                entity -> QueryBuilder.isEmptyString(itemName)|| entity.getItemName().equals(itemName),
                entity -> QueryBuilder.isEmptyInteger(quantity) || entity.getQuantity().equals(quantity),
                entity -> QueryBuilder.isEmptyDouble(price) || DataEntity.compareDoubles(entity.getPricePerUnit(), price),
                entity -> QueryBuilder.isEmptyString(date) || entity.getPurchaseDate().equals(date)
        );
    }

    /**
     * Generates a list of predicates to search for supplier purchases based on the provided ID.
     *
     * @param id The ID of the supplier purchase.
     * @return A list of predicates that can be used to filter supplier purchases by ID.
     */
    public static List<Predicate<SupplierPurchaseEntity>> searchSupplierPurchaseById(String id) {
        return List.of(
                entity -> QueryBuilder.isEmptyString(id) || entity.getId().equals(id)
        );
    }

    /**
     * Generates a list of predicates to search for financial entities based on the provided item name.
     *
     * @param itemName The name of the item.
     * @return A list of predicates that can be used to filter financial entities by item name.
     */
    public static List<Predicate<FinancialEntity>> searchFinancial(String itemName) {
        return List.of(
                entity -> QueryBuilder.isEmptyString(itemName) || entity.getItemName().equals(itemName)
        );
    }
}

