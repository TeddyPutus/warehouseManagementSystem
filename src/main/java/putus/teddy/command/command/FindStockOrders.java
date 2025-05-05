package putus.teddy.command.command;

import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.SupplierPurchaseEntity;

import java.util.stream.Stream;

public class FindStockOrders implements Command {
     public boolean execute() {
         System.out.println("Finding Stock Orders, please enter optional filter values...");

         Stream<SupplierPurchaseEntity> supplierPurchaseStream = supplierPurchaseRepository.findMany(QueryBuilder.supplierPurchaseQuery());

         SupplierPurchaseEntity.printTableHead();
         supplierPurchaseStream.forEach(SupplierPurchaseEntity::printTableRow);

         return false;
     }
}
