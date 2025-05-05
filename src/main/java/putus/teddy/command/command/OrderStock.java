package putus.teddy.command.command;

import putus.teddy.data.builder.EntityBuilder;
import putus.teddy.data.entity.SupplierPurchaseEntity;
import putus.teddy.printer.Printer;

import java.util.Map;

public class OrderStock implements Command {
    public boolean execute() {
        Printer.info("Ordering stock...");
        SupplierPurchaseEntity newOrder = EntityBuilder.buildSupplierPurchaseEntity();

        try{
            validateItem(newOrder.getItemName());
            validateSupplier(newOrder.getSupplierName());
        }catch (Exception e){
            Printer.error(e.getMessage());
            return false;
        }

        supplierPurchaseRepository.create(newOrder);
        Printer.success("Order placed successfully. Order ID is " + newOrder.getId());

        return false;
    }

    private void validateSupplier(String supplierName) throws Exception {
        if (supplierRepository.findOne(Map.of("name", supplierName)) == null) {
            throw new Exception("Supplier does not exist. Please register supplier first.");
        }
    }

    private void validateItem(String itemName) throws Exception {
        if (inventoryRepository.findOne(Map.of("itemName", itemName)) == null) {
            throw new Exception("Item does not exist. Please register item first.");
        }
    }
}
