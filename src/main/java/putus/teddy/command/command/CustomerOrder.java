package putus.teddy.command.command;

import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.CustomerPurchaseEntity;
import putus.teddy.data.entity.FinancialEntity;
import putus.teddy.data.entity.InventoryEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.printer.Printer;

import java.time.LocalDate;
import java.util.Map;

public class CustomerOrder implements Command {
    private FinancialEntity financialEntity;
    private InventoryEntity inventoryEntity;

    public Result execute() {
        Printer.info("Taking customer order...");
        CustomerPurchaseEntity newOrder;

        newOrder = createCustomerPurchaseEntity();

        try {
            validateItem(newOrder.getItemName());
            updateStockLevel(newOrder.getItemName(), newOrder.getQuantity());
            setTotalPrice(newOrder);
            updateFinancialEntity(newOrder);
        } catch (Exception e) {
            Printer.error(e.getMessage());
            return Result.FAILURE;
        }

        customerPurchaseRepository.create(newOrder);
        Printer.success("Order placed successfully. Order ID is " + newOrder.getId());
        return Result.SUCCESS;
    }

    private CustomerPurchaseEntity createCustomerPurchaseEntity() {
        return new CustomerPurchaseEntity(
                ValidatedInputParser.parseString("name", true, 1, 15),
                ValidatedInputParser.parseString("itemName", true, 1, 15),
                ValidatedInputParser.parseQuantity("quantity", true),
                LocalDate.now().toString()
        );
    }

    private void setTotalPrice(CustomerPurchaseEntity newOrder) throws Exception {

        try{
            newOrder.setTotalPrice(
                    newOrder.getQuantity() * inventoryEntity.getPricePerUnit()
            );
        } catch (Exception e) {
            throw new Exception("Error generating order: " + e.getMessage());
        }
    }

    private void updateFinancialEntity(CustomerPurchaseEntity newOrder) throws Exception {
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

    private void updateStockLevel(String itemName, int quantity) throws Exception {
        if (inventoryEntity.getQuantity() < quantity) {
            throw new Exception("Not enough stock available. Please order more stock.");
        }

        inventoryEntity.setQuantity(inventoryEntity.getQuantity() - quantity);
        Printer.info("Remaining stock for " + itemName + ": " + inventoryEntity.getQuantity());

        if (inventoryEntity.getQuantity() <= 5) {
            Printer.alert("Stock for " + itemName + " is low. Please order more stock.");
        }

    }

    private void validateItem(String itemName) throws Exception {
        inventoryEntity = inventoryRepository.findOne(QueryBuilder.searchInventoryByItemName(itemName));
        financialEntity = financialRepository.findOne(QueryBuilder.searchFinancial(itemName));
        if (inventoryEntity == null) {
            throw new Exception("Item does not exist. Please register item first.");
        }
        if (financialEntity== null) {
            throw new Exception("Financial entity not found for item: " + itemName);
        }
    }
}
