package putus.teddy.command.command;

import putus.teddy.data.builder.EntityBuilder;
import putus.teddy.data.entity.CustomerPurchaseEntity;
import putus.teddy.data.entity.FinancialEntity;
import putus.teddy.data.entity.InventoryEntity;

import java.util.Map;

public class CustomerOrder implements Command {

    public boolean execute() {
        System.out.println("Taking customer order...");
        CustomerPurchaseEntity newOrder;

        try{
            newOrder = buildCustomerPurchaseEntity();
        } catch (Exception e) {
            System.out.println("An error occurred while processing the order: " + e.getMessage());
            return false;
        }

        if (validateOrder(newOrder) && updateFinancialEntity(newOrder)) {
            customerPurchaseRepository.create(newOrder);
            System.out.println("Order placed successfully. Order ID is " + newOrder.getId());
        } else {
            System.out.println("Financial entity not found for item: " + newOrder.getItemName());
        }
        return false;
    }

    private boolean updateFinancialEntity(CustomerPurchaseEntity newOrder){
        FinancialEntity financialEntity = financialRepository.findOne(Map.of("itemName", newOrder.getItemName()));

        if (financialEntity == null) {
            return false;
        }

        financialEntity.setTotalRevenue(
                financialEntity.getTotalRevenue() + newOrder.getTotalPrice()
        );

        financialEntity.setQuantitySold(
                financialEntity.getQuantitySold() + newOrder.getQuantity()
        );
        return true;
    }

    private CustomerPurchaseEntity buildCustomerPurchaseEntity() {
        CustomerPurchaseEntity newOrder = EntityBuilder.buildCustomerPurchaseEntity();

        newOrder.setTotalPrice(
                newOrder.getQuantity() * inventoryRepository.findOne(Map.of("itemName", newOrder.getItemName())).getPricePerUnit()
        );

        return newOrder;
    }

    private boolean validateOrder(CustomerPurchaseEntity newOrder) {
        return validateItem(newOrder.getItemName()) && updateStockLevel(newOrder.getItemName(), newOrder.getQuantity());
    }

    private boolean updateStockLevel(String itemName, int quantity) {
        InventoryEntity inventoryEntity = inventoryRepository.findOne(Map.of("itemName", itemName));
        if (inventoryEntity.getQuantity() < quantity) {
            System.out.println("Not enough stock available. Please order more stock.");
            return false;
        }

        inventoryEntity.setQuantity(inventoryEntity.getQuantity() - quantity);
        System.out.println("Remaining stock for " + itemName + ": " + inventoryEntity.getQuantity());

        if (inventoryEntity.getQuantity() <= 5) {
            printAlert("Stock for " + itemName + " is low. Please order more stock.");
        }

        return true;
    }

    private void printAlert(String message){
        System.out.println("!!! ALERT: " + message + " !!!");
    }

    private boolean validateItem(String itemName) {
        boolean exists =  inventoryRepository.findOne(Map.of("itemName", itemName)) != null;
        if (!exists) {
            System.out.println("Item does not exist. Please register item first.");
        }
        return exists;
    }
}
