package putus.teddy.command.command;

import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.DataEntity;
import putus.teddy.data.entity.SupplierEntity;
import putus.teddy.printer.Printer;

public class FindSuppliers implements Command {
     public boolean execute() {
         Printer.info("Finding Suppliers, please enter optional filter values...");

         Printer.printTable(
                 supplierRepository.findMany(QueryBuilder.supplierQuery()).map(entity -> (DataEntity) entity),
                 SupplierEntity.getTableHead()
         );

         return false;
     }
}
