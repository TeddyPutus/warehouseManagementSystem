package putus.teddy.command.command;

import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.DataEntity;
import putus.teddy.data.entity.SupplierPurchaseEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.data.repository.Repository;
import putus.teddy.printer.Printer;

public class FindStockOrders implements Command {

    private final Repository<SupplierPurchaseEntity> supplierPurchaseRepository;

    public FindStockOrders(Repository<SupplierPurchaseEntity> supplierPurchaseRepository) {
        this.supplierPurchaseRepository = supplierPurchaseRepository;
    }

    public Result execute() {
        Printer.info("Finding Stock Orders, please enter optional filter values...");

        Printer.printTable(
                supplierPurchaseRepository.findMany(QueryBuilder.searchSupplierPurchase(
                        ValidatedInputParser.parseString("name", false, 1, 15),
                        ValidatedInputParser.parseString("itemName", false, 1, 15),
                        ValidatedInputParser.parseQuantity("quantity", false),
                        ValidatedInputParser.parseAmount("price", false),
                        ValidatedInputParser.parseString("date", false, 1, 10)
                )).map(entity -> (DataEntity) entity),
                SupplierPurchaseEntity.getTableHead()
        );

        return Result.SUCCESS;
    }
}
