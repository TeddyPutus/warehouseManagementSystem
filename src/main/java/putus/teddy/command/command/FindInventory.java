package putus.teddy.command.command;

import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.InventoryEntity;
import putus.teddy.data.repository.InMemoryRepository;

import java.util.Map;
import java.util.stream.Stream;

public class FindInventory implements Command {

    public boolean execute() {
        System.out.println("Finding Inventory, please enter optional filter values...");

        Stream<InventoryEntity> inventoryStream = inventoryRepository.findMany(QueryBuilder.supplierQuery());

        InventoryEntity.printTableHead();
        inventoryStream.forEach(InventoryEntity::printTableRow);
        return false;
    }
}
