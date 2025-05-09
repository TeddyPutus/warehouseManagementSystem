package putus.teddy.command.command;

import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.InventoryEntity;
import putus.teddy.data.entity.SupplierEntity;
import putus.teddy.data.entity.SupplierPurchaseEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.data.repository.Repository;
import putus.teddy.printer.Printer;

import java.time.LocalDate;

/**
 * Command to order stock from suppliers.
 * This class handles the creation of a new supplier purchase entity,
 * validates the item and supplier, and places the order.
 */
public class OrderStock implements Command {

    private final Repository<SupplierPurchaseEntity> supplierPurchaseRepository;
    private final Repository<SupplierEntity> supplierRepository;
    private final Repository<InventoryEntity> inventoryRepository;

    public OrderStock(Repository<SupplierPurchaseEntity> supplierPurchaseRepository,
                      Repository<SupplierEntity> supplierRepository,
                      Repository<InventoryEntity> inventoryRepository) {
        this.supplierPurchaseRepository = supplierPurchaseRepository;
        this.supplierRepository = supplierRepository;
        this.inventoryRepository = inventoryRepository;
    }

    /**
     * Main method of the command.
     * Creates a new supplier purchase entity.
     * Validates the item and supplier before placing the order.
     *
     * @return Success or Failure.
     */
    public Result execute() {
        Printer.info("Ordering stock...");

        SupplierPurchaseEntity newOrder = createSupplierPurchaseEntity();

        try {
            validateItem(newOrder.getItemName());
            validateSupplier(newOrder.getSupplierName());
        } catch (Exception e) {
            Printer.error(e.getMessage());
            return Result.FAILURE;
        }

        supplierPurchaseRepository.create(newOrder);
        Printer.success("Order placed successfully. Order ID is " + newOrder.getId());

        return Result.SUCCESS;
    }

    /**
     * Creates a new supplier purchase entity based on user input.
     *
     * @return A new SupplierPurchaseEntity object.
     */
    private SupplierPurchaseEntity createSupplierPurchaseEntity() {
        return new SupplierPurchaseEntity(
                ValidatedInputParser.parseString("supplier name", true, 1, 15),
                LocalDate.now().toString(),
                ValidatedInputParser.parseString("item name", true, 1, 15),
                ValidatedInputParser.parseQuantity("quantity", true),
                ValidatedInputParser.parseAmount("price", true)
        );
    }

    /**
     * Validates if the supplier exists in the repository.
     *
     * @param supplierName The name of the supplier to validate.
     * @throws Exception If the supplier does not exist.
     */
    private void validateSupplier(String supplierName) throws Exception {
        if (supplierRepository.findOne(QueryBuilder.searchSupplierByName(supplierName)) == null) {
            throw new Exception("Supplier does not exist. Please register supplier first.");
        }
    }

    /**
     * Validates if the item exists in the inventory repository.
     *
     * @param itemName The name of the item to validate.
     * @throws Exception If the item does not exist.
     */
    private void validateItem(String itemName) throws Exception {
        if (inventoryRepository.findOne(QueryBuilder.searchInventoryByItemName(itemName)) == null) {
            throw new Exception("Item does not exist. Please register item first.");
        }
    }
}
