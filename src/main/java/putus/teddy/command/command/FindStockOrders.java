package putus.teddy.command.command;

import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.DataEntity;
import putus.teddy.data.entity.SupplierPurchaseEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.printer.Printer;

public class FindStockOrders implements Command {
    public Result execute() {
        Printer.infoLine("Finding Stock Orders, please enter optional filter values...");

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
