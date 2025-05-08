package putus.teddy.command.command;

import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.DataEntity;
import putus.teddy.data.entity.InventoryEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.printer.Printer;

public class FindInventory implements Command {

    public Result execute() {
        Printer.infoLine("Finding Inventory, please enter optional filter values...");

        Printer.printTable(
                inventoryRepository.findMany(QueryBuilder.searchInventory(
                        ValidatedInputParser.parseString("name", false,1,15),
                        ValidatedInputParser.parseQuantity("quantity", false),
                        ValidatedInputParser.parseAmount("price", false)
                )),
                InventoryEntity.getTableHead()
        );

        return Result.SUCCESS;
    }
}
