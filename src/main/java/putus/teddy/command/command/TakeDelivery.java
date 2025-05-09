package putus.teddy.command.command;

import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.FinancialEntity;
import putus.teddy.data.entity.InventoryEntity;
import putus.teddy.data.entity.SupplierPurchaseEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.data.repository.Repository;
import putus.teddy.printer.Printer;

import static putus.teddy.data.entity.SupplierPurchaseEntity.Status.PENDING;

/**
 * Handles the delivery of supplier orders.
 * This class validates the order, updates stock levels, and updates financial records.
 */
public class TakeDelivery implements Command {

    private final Repository<SupplierPurchaseEntity> supplierPurchaseRepository;
    private final Repository<InventoryEntity> inventoryRepository;
    private final Repository<FinancialEntity> financialRepository;

    public TakeDelivery(Repository<SupplierPurchaseEntity> supplierPurchaseRepository, Repository<InventoryEntity> inventoryRepository, Repository<FinancialEntity> financialRepository) {
        this.supplierPurchaseRepository = supplierPurchaseRepository;
        this.inventoryRepository = inventoryRepository;
        this.financialRepository = financialRepository;
    }

    /**
     * Main method of the command.
     * Takes Order ID as user input using ValidatedInputParser, and updates the supplier purchase entity to delivered; stock levels in inventory; and updates financial data.
     *
     * @return Success or Failure.
     */
    public Result execute() {
        Printer.info("Taking delivery...");
        SupplierPurchaseEntity order;
        InventoryEntity inventoryEntity;
        FinancialEntity financialEntity;

        try {
            order = getOrder();
            inventoryEntity = getInventory(order.getItemName());
            financialEntity = getFinancial(order.getItemName());
        } catch (Exception e) {
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

    /**
     * Retrieves the financial entity based on the item name.
     *
     * @param itemName The name of the item.
     * @return The financial entity.
     * @throws Exception If the financial entity is not found.
     */
    private FinancialEntity getFinancial(String itemName) throws Exception {

        FinancialEntity financialEntity = financialRepository.findOne(QueryBuilder.searchFinancial(itemName));

        if (financialEntity == null) {
            throw new Exception("Financial entity not found.");
        }
        return financialEntity;
    }

    /**
     * Retrieves the inventory entity based on the item name.
     *
     * @param itemName The name of the item.
     * @return The inventory entity.
     * @throws Exception If the inventory entity is not found.
     */
    private InventoryEntity getInventory(String itemName) throws Exception {
        InventoryEntity inventoryEntity = inventoryRepository.findOne(QueryBuilder.searchInventoryByItemName(itemName));

        if (inventoryEntity == null) {
            throw new Exception("Item not found in inventory.");
        }

        return inventoryEntity;
    }

    /**
     * Retrieves the order based on the order ID.
     *
     * @return The supplier purchase entity.
     * @throws Exception If the order is not found or already delivered.
     */
    private SupplierPurchaseEntity getOrder() throws Exception {
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
