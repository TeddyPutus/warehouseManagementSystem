package putus.teddy.command.command;

import putus.teddy.data.entity.FinancialEntity;
import putus.teddy.data.entity.InventoryEntity;
import putus.teddy.data.entity.SupplierPurchaseEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.printer.Printer;

import java.util.Map;

import static putus.teddy.data.entity.SupplierPurchaseEntity.Status.PENDING;

public class TakeDelivery implements Command {

    public boolean execute() {
        Printer.info("Taking delivery...");

        String orderId = ValidatedInputParser.parseString("Order ID", true, 1, 36);
        SupplierPurchaseEntity order = supplierPurchaseRepository.findOne(Map.of("id", orderId));

        if (order == null) {
            Printer.warning("Order not found.");
            return false;
        }
        if (!order.getStatus().equals(PENDING)) {
            Printer.warning("Order already delivered.");
            return false;
        }

        InventoryEntity inventoryEntity = inventoryRepository.findOne(Map.of("itemName", order.getItemName()));

        if(inventoryEntity == null){
            Printer.error("Item not found in inventory.");
            return false;
        }

        FinancialEntity financialEntity = financialRepository.findOne(Map.of("itemName", order.getItemName()));

        if(financialEntity == null){
            Printer.error("Financial entity not found.");
            return false;
        }

        inventoryEntity.setQuantity(inventoryEntity.getQuantity() + order.getQuantity());

        financialEntity.setTotalCost(financialEntity.getTotalCost() + order.getTotalPrice());
        financialEntity.setQuantityPurchased(financialEntity.getQuantityPurchased() + order.getQuantity());

        order.setStatus(SupplierPurchaseEntity.Status.DELIVERED);

        Printer.success("Delivery taken successfully.");

        return false;
    }
}
