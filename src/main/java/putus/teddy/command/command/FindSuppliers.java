package putus.teddy.command.command;

import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.SupplierEntity;
import putus.teddy.data.repository.InMemoryRepository;

import java.util.Map;
import java.util.stream.Stream;

public class FindSuppliers implements Command {
     public boolean execute() {
         System.out.println("Finding Suppliers, please enter optional filter values...");

         Stream<SupplierEntity> supplierStream = supplierRepository.findMany(QueryBuilder.supplierQuery());

         SupplierEntity.printTableHead();
         supplierStream.forEach(SupplierEntity::printTableRow);

         return false;
     }
}
