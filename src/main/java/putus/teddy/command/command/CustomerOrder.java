package putus.teddy.command.command;

import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.CustomerPurchaseEntity;
import putus.teddy.data.entity.FinancialEntity;
import putus.teddy.data.entity.InventoryEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.data.repository.Repository;
import putus.teddy.printer.Printer;

import java.time.LocalDate;

/**
 * Handles the creation of customer orders.
 * This class validates the order, updates stock levels, calculates total price,
 * and updates financial records.
 */
public class CustomerOrder implements Command {
    private FinancialEntity financialEntity;
    private InventoryEntity inventoryEntity;

    private final Repository<FinancialEntity> financialRepository;
    private final Repository<InventoryEntity> inventoryRepository;
    private final Repository<CustomerPurchaseEntity> customerPurchaseRepository;

    public CustomerOrder(Repository<FinancialEntity> financialRepository,
                         Repository<InventoryEntity> inventoryRepository,
                         Repository<CustomerPurchaseEntity> customerPurchaseRepository) {
        this.financialRepository = financialRepository;
        this.inventoryRepository = inventoryRepository;
        this.customerPurchaseRepository = customerPurchaseRepository;

    }

    /**
     * Main method of the command.
     * Takes user input using ValidatedInputParser, and creates a new customer purchase entity.
     *
     * @return Success or Failure.
     */
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

    /**
     * Creates a new customer purchase entity.
     * Uses the ValidatedInputParser to get user input.
     *
     * @return CustomerPurchaseEntity
     */
    private CustomerPurchaseEntity createCustomerPurchaseEntity() {
        return new CustomerPurchaseEntity(
                ValidatedInputParser.parseString("name", true, 1, 15),
                ValidatedInputParser.parseString("itemName", true, 1, 15),
                ValidatedInputParser.parseQuantity("quantity", true),
                LocalDate.now().toString()
        );
    }

    /**
     * Sets the total price of the order.
     *
     * @param newOrder The new order to set the total price for.
     * @throws Exception If an error occurs while setting the total price.
     *                   This would likely be that the inventory entity is null or has no price set.
     */
    private void setTotalPrice(CustomerPurchaseEntity newOrder) throws Exception {

        try {
            newOrder.setTotalPrice(
                    newOrder.getQuantity() * inventoryEntity.getPricePerUnit()
            );
        } catch (Exception e) {
            throw new Exception("Error generating order: " + e.getMessage());
        }
    }

    /**
     * Updates the financial entity with the new order.
     *
     * @param newOrder The new order to update the financial entity with.
     * @throws Exception If financial entity does not exist. This would indicate a repository mismatch between inventory and financial.
     */
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

    /**
     * Updates the stock level of the item.
     *
     * @param itemName The name of the item to update.
     * @param quantity The quantity required for the order.
     * @throws Exception If not enough stock is available.
     */
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

    /**
     * Validates the item by checking if it exists in the inventory and financial repositories.
     *
     * @param itemName The name of the item to find.
     * @throws Exception If the item does not exist in either repository.
     */
    private void validateItem(String itemName) throws Exception {
        inventoryEntity = inventoryRepository.findOne(QueryBuilder.searchInventoryByItemName(itemName));
        financialEntity = financialRepository.findOne(QueryBuilder.searchFinancial(itemName));
        if (inventoryEntity == null) {
            throw new Exception("Item does not exist. Please register item first.");
        }
        if (financialEntity == null) {
            throw new Exception("Financial entity not found for item: " + itemName);
        }
    }
}
