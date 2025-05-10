package putus.teddy.command.command;

import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.FinancialEntity;
import putus.teddy.data.entity.InventoryEntity;
import putus.teddy.data.repository.Repository;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.printer.Printer;

/**
 * Command to register a new item in the inventory.
 * This class handles the creation of a new inventory entity,
 * validates the item name, and registers it in the system.
 */
public class RegisterItem implements Command {

    private final Repository<FinancialEntity> financialRepository;
    private final Repository<InventoryEntity> inventoryRepository;

    public RegisterItem(Repository<FinancialEntity> financialRepository, Repository<InventoryEntity> inventoryRepository) {
        this.financialRepository = financialRepository;
        this.inventoryRepository = inventoryRepository;
    }

    /**
     * Main method of the command.
     * Creates a new inventory entity.
     * Validates the item name before registering it.
     *
     * @return Success or Failure.
     */
    public Result execute() {
        Printer.info("Registering item...");

        InventoryEntity newItem = createInventoryEntity();

        if (inventoryRepository.findOne(QueryBuilder.searchInventoryByItemName(newItem.getItemName())) != null) {
            Printer.error("Item already exists.");
            return Result.FAILURE;
        }

        FinancialEntity financialEntity = new FinancialEntity(newItem.getItemName(), newItem.getQuantity(), 0, 0.0, 0.0);

        inventoryRepository.create(newItem);
        financialRepository.create(financialEntity);

        Printer.success("Item registered successfully.");

        return Result.SUCCESS;
    }

    /**
     * Creates a new inventory entity based on user input.
     *
     * @return A new InventoryEntity object.
     */
    private InventoryEntity createInventoryEntity() {
        return new InventoryEntity(
                ValidatedInputParser.parseString("name", true, 1, 15),
                0,
                ValidatedInputParser.parseAmount("price", true)
        );
    }
}
