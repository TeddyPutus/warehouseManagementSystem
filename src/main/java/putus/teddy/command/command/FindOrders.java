package putus.teddy.command.command;

import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.CustomerPurchaseEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.data.repository.Repository;
import putus.teddy.printer.Printer;

/**
 * Command to find customer orders based on optional filters.
 */
public class FindOrders implements Command {

    private final Repository<CustomerPurchaseEntity> customerPurchaseRepository;

    public FindOrders(Repository<CustomerPurchaseEntity> customerPurchaseRepository) {
        this.customerPurchaseRepository = customerPurchaseRepository;
    }

    /**
     * Main method of the command.
     * Takes user input using ValidatedInputParser, and searches the customerPurchaseRepository.
     * Results are sent to the Printer to print the table.
     *
     * @return Success or Failure.
     */
    public Result execute() {
        Printer.info("Finding Customer Orders, please enter optional filter values...");

        Printer.printTable(
                customerPurchaseRepository.findMany(QueryBuilder.searchCustomerOrder(
                        ValidatedInputParser.parseString("name", false, 1, 15),
                        ValidatedInputParser.parseString("itemName", false, 1, 15),
                        ValidatedInputParser.parseQuantity("quantity", false),
                        ValidatedInputParser.parseString("date", false, 1, 10)
                )),
                CustomerPurchaseEntity.getTableHead()
        );

        return Result.SUCCESS;
    }
}
