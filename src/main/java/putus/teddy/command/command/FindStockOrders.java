package putus.teddy.command.command;

import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.SupplierPurchaseEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.data.repository.Repository;
import putus.teddy.printer.Printer;

/**
 * Command to find stock orders from suppliers based on optional filters.
 */
public class FindStockOrders implements Command {

    private final Repository<SupplierPurchaseEntity> supplierPurchaseRepository;

    public FindStockOrders(Repository<SupplierPurchaseEntity> supplierPurchaseRepository) {
        this.supplierPurchaseRepository = supplierPurchaseRepository;
    }

    /**
     * Main method of the command.
     * Takes user input using ValidatedInputParser, and searches the supplierPurchaseRepository.
     * Results are sent to the Printer to print the table.
     *
     * @return Success or Failure.
     */
    public Result execute() {
        Printer.info("Finding Stock Orders, please enter optional filter values...");

        Printer.printTable(
                supplierPurchaseRepository.findMany(QueryBuilder.searchSupplierPurchase(
                        ValidatedInputParser.parseString("name", false, 1, 15),
                        ValidatedInputParser.parseString("itemName", false, 1, 15),
                        ValidatedInputParser.parseQuantity("quantity", false),
                        ValidatedInputParser.parseAmount("price", false),
                        ValidatedInputParser.parseString("date", false, 1, 10)
                )),
                SupplierPurchaseEntity.getTableHead()
        );

        return Result.SUCCESS;
    }
}
