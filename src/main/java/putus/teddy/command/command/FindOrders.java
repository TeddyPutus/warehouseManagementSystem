package putus.teddy.command.command;

import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.CustomerPurchaseEntity;
import putus.teddy.data.entity.DataEntity;
import putus.teddy.printer.Printer;

public class FindOrders implements Command{
    public Result execute() {
        Printer.info("Finding Customer Orders, please enter optional filter values...");

        Printer.printTable(
                customerPurchaseRepository.findMany(QueryBuilder.customerOrderQuery()).map(entity -> (DataEntity) entity),
                CustomerPurchaseEntity.getTableHead()
        );

        return Result.SUCCESS;
    }
}
