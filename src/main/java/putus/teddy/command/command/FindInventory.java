package putus.teddy.command.command;

import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.DataEntity;
import putus.teddy.data.entity.InventoryEntity;
import putus.teddy.printer.Printer;

public class FindInventory implements Command {

    public Result execute() {
        Printer.info("Finding Inventory, please enter optional filter values...");

        Printer.printTable(
                inventoryRepository.findMany(QueryBuilder.inventoryQuery()).map(entity -> (DataEntity) entity),
                InventoryEntity.getTableHead()
        );

        return Result.SUCCESS;
    }
}
