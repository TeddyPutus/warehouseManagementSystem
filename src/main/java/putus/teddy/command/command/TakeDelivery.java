package putus.teddy.command.command;

import putus.teddy.data.entity.FinancialEntity;
import putus.teddy.data.entity.InventoryEntity;
import putus.teddy.data.entity.SupplierPurchaseEntity;
import putus.teddy.data.parser.InputParser;

import java.util.Map;

import static putus.teddy.data.entity.SupplierPurchaseEntity.Status.PENDING;

public class TakeDelivery implements Command {

    public boolean execute() {
        System.out.println("Taking delivery...");

        String orderId = InputParser.parseString("Order ID", true);
        SupplierPurchaseEntity order = supplierPurchaseRepository.findOne(Map.of("id", orderId));

        if (order == null) {
            System.out.println("Order not found.");
            return false;
        }
        if (!order.getStatus().equals(PENDING)) {
            System.out.println("Order already delivered.");
            return false;
        }

        InventoryEntity inventoryEntity = inventoryRepository.findOne(Map.of("itemName", order.getItemName()));

        if(inventoryEntity == null){
            System.out.println("Item not found in inventory.");
            return false;
        }

        FinancialEntity financialEntity = financialRepository.findOne(Map.of("itemName", order.getItemName()));

        if(financialEntity == null){
            System.out.println("Financial entity not found.");
            return false;
        }

        inventoryEntity.setQuantity(inventoryEntity.getQuantity() + order.getQuantity());

        financialEntity.update(Map.of(
                "totalCost", order.getTotalPrice() + financialEntity.getTotalCost(),
                "quantityPurchased", order.getQuantity() + financialEntity.getQuantityPurchased()
        ));

        order.setStatus(SupplierPurchaseEntity.Status.DELIVERED);
        System.out.println("Delivery taken successfully.");

        return false;
    }
}
