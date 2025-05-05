package putus.teddy.data.builder;


import putus.teddy.data.entity.CustomerPurchaseEntity;
import putus.teddy.data.entity.InventoryEntity;
import putus.teddy.data.entity.SupplierEntity;
import putus.teddy.data.entity.SupplierPurchaseEntity;
import putus.teddy.data.parser.ValidatedInputParser;

import java.time.LocalDate;

public class EntityBuilder {

    public static InventoryEntity buildInventoryEntity() {
        String name = ValidatedInputParser.parseString("name", true, 1, 15);
        double price = ValidatedInputParser.parseAmount("price", true);
        return new InventoryEntity(name, 0, price);
    }

    public static SupplierEntity buildSupplierEntity() {
        String name = ValidatedInputParser.parseString("name", true,1,15);
        String contactNumber = ValidatedInputParser.parseString("phone number", true,1,12);
        String email = ValidatedInputParser.parseString("email", true,1,20);
        return new SupplierEntity(name, contactNumber, email);
    }

    public static SupplierPurchaseEntity buildSupplierPurchaseEntity() {
        String supplierName = ValidatedInputParser.parseString("supplier name", true,1,15);
        String itemName = ValidatedInputParser.parseString("item name", true,1,15);
        int quantity = ValidatedInputParser.parseQuantity("quantity", true);
        double price = ValidatedInputParser.parseAmount("price", true);
        String date = LocalDate.now().toString();
        return new SupplierPurchaseEntity(supplierName, date, itemName, quantity, price);
    }

    public static CustomerPurchaseEntity buildCustomerPurchaseEntity() {
        String customerName = ValidatedInputParser.parseString("customer name", true,1,15);
        String itemName = ValidatedInputParser.parseString("item name", true,1,15);
        int quantity = ValidatedInputParser.parseQuantity("quantity", true);
        String date = LocalDate.now().toString();
        return new CustomerPurchaseEntity(customerName, itemName, quantity, date);
    }
}
