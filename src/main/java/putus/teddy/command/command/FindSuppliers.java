package putus.teddy.command.command;

import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.SupplierEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.data.repository.Repository;
import putus.teddy.printer.Printer;

/*
 * Command to find stock supplier information based on optional filters.
 */
public class FindSuppliers implements Command {

    private final Repository<SupplierEntity> supplierRepository;

    public FindSuppliers(Repository<SupplierEntity> supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    /**
     * Main method of the command.
     * Takes user input using ValidatedInputParser, and searches the supplierRepository.
     * Results are sent to the Printer to print the table.
     *
     * @return Success or Failure.
     */
    public Result execute() {
        Printer.info("Finding Suppliers, please enter optional filter values...");

        Printer.printTable(
                supplierRepository.findMany(QueryBuilder.searchSupplier(
                        ValidatedInputParser.parseString("name", false, 1, 15),
                        ValidatedInputParser.parseString("phone number", false, 1, 12),
                        ValidatedInputParser.parseString("email", false, 1, 20)
                )),
                SupplierEntity.getTableHead()
        );

        return Result.SUCCESS;
    }
}
