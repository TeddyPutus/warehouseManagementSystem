package putus.teddy.data.builder;


import putus.teddy.data.entity.CustomerPurchaseEntity;
import putus.teddy.data.entity.InventoryEntity;
import putus.teddy.data.entity.SupplierEntity;
import putus.teddy.data.entity.SupplierPurchaseEntity;
import putus.teddy.data.parser.InputParser;

import java.time.LocalDate;

public class EntityBuilder {

    public static InventoryEntity buildInventoryEntity() {
        String name = InputParser.parseString("name", true);
        double price = InputParser.parseDouble("price", true);
        return new InventoryEntity(name, 0, price);
    }

    public static SupplierEntity buildSupplierEntity() {
        String name = InputParser.parseString("name", true);
        String contactNumber = InputParser.parseString("phone number", true);
        String email = InputParser.parseString("email", true);
        return new SupplierEntity(name, contactNumber, email);
    }

    public static SupplierPurchaseEntity buildSupplierPurchaseEntity() {
        String supplierName = InputParser.parseString("supplier name", true);
        String itemName = InputParser.parseString("item name", true);
        int quantity = InputParser.parseInt("quantity", true);
        double price = InputParser.parseDouble("price", true);
        String date = LocalDate.now().toString();
        return new SupplierPurchaseEntity(supplierName, date, itemName, quantity, price);
    }

    public static CustomerPurchaseEntity buildCustomerPurchaseEntity() {
        String customerName = InputParser.parseString("customer name", true);
        String itemName = InputParser.parseString("item name", true);
        int quantity = InputParser.parseInt("quantity", true);
        String date = LocalDate.now().toString();
        return new CustomerPurchaseEntity(customerName, itemName, quantity, date);
    }
}
