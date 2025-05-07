package putus.teddy.command.command;

import putus.teddy.data.entity.*;
import putus.teddy.data.repository.InMemoryRepository;

public interface Command {
    public static enum Result{
        SUCCESS,
        FAILURE,
        EXIT
    }

    InMemoryRepository<CustomerPurchaseEntity> customerPurchaseRepository = new InMemoryRepository<>();
    InMemoryRepository<InventoryEntity> inventoryRepository = new InMemoryRepository<>();
    InMemoryRepository<FinancialEntity> financialRepository = new InMemoryRepository<>();
    InMemoryRepository<SupplierEntity> supplierRepository = new InMemoryRepository<>();
    InMemoryRepository<SupplierPurchaseEntity> supplierPurchaseRepository = new InMemoryRepository<>();

    Result execute();
}
