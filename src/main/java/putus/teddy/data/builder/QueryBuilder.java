package putus.teddy.data.builder;

import putus.teddy.data.entity.*;
import java.util.List;
import java.util.function.Predicate;

public class QueryBuilder {

    public static boolean isEmptyString(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isEmptyInteger(Integer integer) {
        return integer == null || integer.equals(Integer.MIN_VALUE);
    }

    public static boolean isEmptyDouble(Double doubleValue) {
        return doubleValue == null || doubleValue.equals(Double.MIN_VALUE);
    }

    public static List<Predicate<SupplierEntity>> supplierSearch(String name, String phoneNumber, String email) {
       return List.of(
               entity -> QueryBuilder.isEmptyString(name) || entity.getName().equals(name),
               entity -> QueryBuilder.isEmptyString(phoneNumber) || entity.getPhoneNumber().equals(phoneNumber),
               entity -> QueryBuilder.isEmptyString(email) ||entity.getEmail().equals(email)
       );
    }

    public static List<Predicate<SupplierEntity>> supplierSearchById(String id) {
        return List.of(
                entity -> QueryBuilder.isEmptyString(id) || entity.getId().equals(id)
        );
    }

    public static List<Predicate<SupplierEntity>> supplierSearchByName(String name) {
        return List.of(
                entity -> QueryBuilder.isEmptyString(name) || entity.getName().equals(name)
        );
    }

    public static List<Predicate<InventoryEntity>> searchInventory(String name, Integer quantity, Double price) {
        return List.of(
                entity -> QueryBuilder.isEmptyString(name) || entity.getItemName().equals(name),
                entity -> QueryBuilder.isEmptyInteger(quantity) || entity.getQuantity().equals(quantity),
                entity -> QueryBuilder.isEmptyDouble(price) || DataEntity.compareDoubles(entity.getPricePerUnit(), price)
        );
    }

    public static List<Predicate<InventoryEntity>> searchInventoryByItemName(String itemName) {
        return List.of(
                entity -> QueryBuilder.isEmptyString(itemName) || entity.getItemName().equals(itemName)
        );
    }

    public static List<Predicate<CustomerPurchaseEntity>> searchCustomerOrder(String customerName, String itemName, Integer quantity, String date) {
        return List.of(
                entity -> QueryBuilder.isEmptyString(customerName) || entity.getCustomerName().equals(customerName),
                entity -> QueryBuilder.isEmptyString(itemName) || entity.getItemName().equals(itemName),
                entity -> QueryBuilder.isEmptyInteger(quantity) || entity.getQuantity().equals(quantity),
                entity -> QueryBuilder.isEmptyString(date) || entity.getPurchaseDate().equals(date)
        );
    }

    public static List<Predicate<SupplierPurchaseEntity>> searchSupplierPurchase(String supplierName, String itemName, Integer quantity, Double price, String date) {
        return List.of(
                entity -> QueryBuilder.isEmptyString(supplierName)|| entity.getSupplierName().equals(supplierName),
                entity -> QueryBuilder.isEmptyString(itemName)|| entity.getItemName().equals(itemName),
                entity -> QueryBuilder.isEmptyInteger(quantity) || entity.getQuantity().equals(quantity),
                entity -> QueryBuilder.isEmptyDouble(price) || DataEntity.compareDoubles(entity.getPricePerUnit(), price),
                entity -> QueryBuilder.isEmptyString(date) || entity.getPurchaseDate().equals(date)
        );
    }

    public static List<Predicate<SupplierPurchaseEntity>> searchSupplierPurchaseById(String id) {
        return List.of(
                entity -> QueryBuilder.isEmptyString(id) || entity.getId().equals(id)
        );
    }

    public static List<Predicate<FinancialEntity>> searchFinancial(String itemName) {
        return List.of(
                entity -> QueryBuilder.isEmptyString(itemName) || entity.getItemName().equals(itemName)
        );
    }
}

