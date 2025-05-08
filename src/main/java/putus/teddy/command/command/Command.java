package putus.teddy.command.command;

import putus.teddy.data.entity.*;
import putus.teddy.data.repository.InMemoryRepository;
import putus.teddy.data.repository.Repository;

public interface Command {
    enum Result{
        SUCCESS,
        FAILURE,
        EXIT
    }

    Repository<CustomerPurchaseEntity> customerPurchaseRepository = new InMemoryRepository<>();
    Repository<InventoryEntity> inventoryRepository = new InMemoryRepository<>();
    Repository<FinancialEntity> financialRepository = new InMemoryRepository<>();
    Repository<SupplierEntity> supplierRepository = new InMemoryRepository<>();
    Repository<SupplierPurchaseEntity> supplierPurchaseRepository = new InMemoryRepository<>();

    Result execute();
}
