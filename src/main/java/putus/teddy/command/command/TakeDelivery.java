package putus.teddy.command.command;

import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.FinancialEntity;
import putus.teddy.data.entity.InventoryEntity;
import putus.teddy.data.entity.SupplierPurchaseEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.printer.Printer;

import java.util.Map;

import static putus.teddy.data.entity.SupplierPurchaseEntity.Status.PENDING;

public class TakeDelivery implements Command {

    public Result execute() {
        Printer.info("Taking delivery...");
        SupplierPurchaseEntity order;
        InventoryEntity inventoryEntity;
        FinancialEntity financialEntity;

        try{
            order = getOrder();
            inventoryEntity = getInventory(order.getItemName());
            financialEntity = getFinancial(order.getItemName());
        }catch(Exception e){
            Printer.error(e.getMessage());
            return Result.FAILURE;
        }

        inventoryEntity.setQuantity(inventoryEntity.getQuantity() + order.getQuantity());

        financialEntity.setTotalCost(financialEntity.getTotalCost() + order.getTotalPrice());
        financialEntity.setQuantityPurchased(financialEntity.getQuantityPurchased() + order.getQuantity());

        order.setStatus(SupplierPurchaseEntity.Status.DELIVERED);

        Printer.success("Delivery taken successfully.");

        return Result.SUCCESS;
    }

    private FinancialEntity getFinancial(String itemName) throws Exception {

        FinancialEntity financialEntity = financialRepository.findOne(QueryBuilder.searchFinancial(itemName));

        if(financialEntity == null){
            throw new Exception("Financial entity not found.");
        }
        return financialEntity;
    }

    private InventoryEntity getInventory(String itemName) throws Exception {
        InventoryEntity inventoryEntity = inventoryRepository.findOne(QueryBuilder.searchInventoryByItemName(itemName));

        if(inventoryEntity == null){
            throw new Exception("Item not found in inventory.");
        }

        return inventoryEntity;
    }

    private SupplierPurchaseEntity getOrder() throws Exception{
        String orderId = ValidatedInputParser.parseString("Order ID", true, 1, 36);
        SupplierPurchaseEntity order = supplierPurchaseRepository.findOne(QueryBuilder.searchSupplierPurchaseById(orderId));

        if (order == null) {
            throw new Exception("Order not found.");
        }
        if (!order.getStatus().equals(PENDING)) {
            throw new Exception("Order already delivered.");
        }

        return order;
    }
}
