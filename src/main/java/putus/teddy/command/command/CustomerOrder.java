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

        newOrder = EntityBuilder.buildCustomerPurchaseEntity();

        try {
            validateOrder(newOrder);
            setTotalPrice(newOrder);
            updateFinancialEntity(newOrder);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        customerPurchaseRepository.create(newOrder);
        System.out.println("Order placed successfully. Order ID is " + newOrder.getId());
        return false;
    }

    private void setTotalPrice(CustomerPurchaseEntity newOrder) throws Exception {
        try{
            newOrder.setTotalPrice(
                    newOrder.getQuantity() * inventoryRepository.findOne(Map.of("itemName", newOrder.getItemName())).getPricePerUnit()
            );
        } catch (Exception e) {
            throw new Exception("Error generating order: " + e.getMessage());
        }
    }

    private void updateFinancialEntity(CustomerPurchaseEntity newOrder) throws Exception {
        FinancialEntity financialEntity = financialRepository.findOne(Map.of("itemName", newOrder.getItemName()));

        if (financialEntity == null) {
            throw new Exception("Financial entity not found for item: " + newOrder.getItemName());
        }

        financialEntity.setTotalRevenue(
                financialEntity.getTotalRevenue() + newOrder.getTotalPrice()
        );

        financialEntity.setQuantitySold(
                financialEntity.getQuantitySold() + newOrder.getQuantity()
        );
    }

    private void validateOrder(CustomerPurchaseEntity newOrder) throws Exception {
        validateItem(newOrder.getItemName());
        updateStockLevel(newOrder.getItemName(), newOrder.getQuantity());
    }

    private void updateStockLevel(String itemName, int quantity) throws Exception {
        InventoryEntity inventoryEntity = inventoryRepository.findOne(Map.of("itemName", itemName));
        if (inventoryEntity.getQuantity() < quantity) {
            throw new Exception("Not enough stock available. Please order more stock.");
        }

        inventoryEntity.setQuantity(inventoryEntity.getQuantity() - quantity);
        System.out.println("Remaining stock for " + itemName + ": " + inventoryEntity.getQuantity());

        if (inventoryEntity.getQuantity() <= 5) {
            printAlert("Stock for " + itemName + " is low. Please order more stock.");
        }

    }

    private void printAlert(String message){
        System.out.println("!!! ALERT: " + message + " !!!");
    }

    private void validateItem(String itemName) throws Exception {
        if (inventoryRepository.findOne(Map.of("itemName", itemName)) == null) {
            throw new Exception("Item does not exist. Please register item first.");
        }
    }
}
