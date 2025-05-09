package putus.teddy.command.command;

import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.InventoryEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.data.repository.Repository;
import putus.teddy.printer.Printer;

/**
 * Command to find inventory items based on optional filters.
 */
public class FindInventory implements Command {
    private final Repository<InventoryEntity> inventoryRepository;

    public FindInventory(Repository<InventoryEntity> inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    /**
     * Main method of the command.
     * Takes user input using ValidatedInputParser, and searches the inventoryRepository.
     * Results are sent to the Printer to print the table.
     *
     * @return Success or Failure.
     */
    public Result execute() {
        Printer.info("Finding Inventory, please enter optional filter values...");

        Printer.printTable(
                inventoryRepository.findMany(QueryBuilder.searchInventory(
                        ValidatedInputParser.parseString("name", false, 1, 15),
                        ValidatedInputParser.parseQuantity("quantity", false),
                        ValidatedInputParser.parseAmount("price", false)
                )),
                InventoryEntity.getTableHead()
        );

        return Result.SUCCESS;
    }
}
