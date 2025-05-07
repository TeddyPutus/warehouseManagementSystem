package putus.teddy.command.command;

import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.DataEntity;
import putus.teddy.data.entity.SupplierPurchaseEntity;
import putus.teddy.printer.Printer;

public class FindStockOrders implements Command {
     public Result execute() {
         Printer.info("Finding Stock Orders, please enter optional filter values...");

         Printer.printTable(
                 supplierPurchaseRepository.findMany(QueryBuilder.supplierPurchaseQuery()).map(entity -> (DataEntity) entity),
                 SupplierPurchaseEntity.getTableHead()
         );

         return Result.SUCCESS;
     }
}
