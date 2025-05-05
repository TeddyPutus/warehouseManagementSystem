package putus.teddy.command.command;

import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.CustomerPurchaseEntity;
import putus.teddy.data.repository.InMemoryRepository;

import java.util.Map;
import java.util.stream.Stream;

public class FindOrders implements Command{
    public boolean execute() {
        System.out.println("Finding Customer Orders, please enter optional filter values...");

        Stream<CustomerPurchaseEntity> customerPurchaseStream = customerPurchaseRepository.findMany(QueryBuilder.customerOrderQuery());

        CustomerPurchaseEntity.printTableHead();
        customerPurchaseStream.forEach(CustomerPurchaseEntity::printTableRow);
        return false;
    }
}
