package putus.teddy.command.command;

import putus.teddy.data.builder.EntityBuilder;
import putus.teddy.data.entity.SupplierPurchaseEntity;

import java.util.Map;

public class OrderStock implements Command {
    public boolean execute() {
        System.out.println("Ordering stock...");
        SupplierPurchaseEntity newOrder = EntityBuilder.buildSupplierPurchaseEntity();

        boolean itemExists = validateItem(newOrder.getItemName());
        boolean supplierExists = validateSupplier(newOrder.getSupplierName());

        if(!itemExists || !supplierExists) {
            return false;
        }

        supplierPurchaseRepository.create(newOrder);
        System.out.println("Order placed successfully. Order ID is " + newOrder.getId());

        return false;
    }

    private boolean validateSupplier(String supplierName) {
        boolean exists = supplierRepository.findOne(Map.of("name", supplierName)) != null;
        if (!exists) {
            System.out.println("Supplier does not exist. Please register supplier first.");
        }
        return exists;
    }

    private boolean validateItem(String itemName) {
        boolean exists =  inventoryRepository.findOne(Map.of("itemName", itemName)) != null;
        if (!exists) {
            System.out.println("Item does not exist. Please register item first.");
        }
        return exists;
    }
}
