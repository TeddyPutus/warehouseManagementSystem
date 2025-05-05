package putus.teddy.command.command;

import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.CustomerPurchaseEntity;
import putus.teddy.data.entity.InventoryEntity;
import putus.teddy.data.entity.SupplierEntity;
import putus.teddy.data.entity.SupplierPurchaseEntity;
import putus.teddy.data.parser.InputParser;

import java.util.Arrays;
import java.util.stream.Stream;

public class Find implements Command {
    private enum RepositoryType {
        SUPPLIER,
        INVENTORY,
        CUSTOMER_PURCHASE,
        SUPPLIER_PURCHASE
    }

    public boolean execute(){
        RepositoryType repoName = getRepoName();

        switch (repoName) {
            case SUPPLIER -> findSupplier();
            case INVENTORY -> findInventory();
            case CUSTOMER_PURCHASE -> findCustomerOrders();
            case SUPPLIER_PURCHASE -> findSupplierOrders();
            default -> System.out.println("Unrecognised repository name: " + repoName);
        };

        return false;
    }

    private void findSupplier() {
        System.out.println("Finding Suppliers, please enter optional filter values...");

        Stream<SupplierEntity> supplierStream = supplierRepository.findMany(QueryBuilder.supplierQuery());

        SupplierEntity.printTableHead();
        supplierStream.forEach(SupplierEntity::printTableRow);

    }

    private void findInventory() {
        System.out.println("Finding Inventory, please enter optional filter values...");

        Stream<InventoryEntity> inventoryStream = inventoryRepository.findMany(QueryBuilder.supplierQuery());

        InventoryEntity.printTableHead();
        inventoryStream.forEach(InventoryEntity::printTableRow);
    }

    private void findCustomerOrders() {
        System.out.println("Finding Customer Orders, please enter optional filter values...");

        Stream<CustomerPurchaseEntity> customerPurchaseStream = customerPurchaseRepository.findMany(QueryBuilder.customerOrderQuery());

        CustomerPurchaseEntity.printTableHead();
        customerPurchaseStream.forEach(CustomerPurchaseEntity::printTableRow);
    }

    private void findSupplierOrders() {
        System.out.println("Finding Supplier Orders, please enter optional filter values...");

        Stream<SupplierPurchaseEntity> supplierPurchaseStream = supplierPurchaseRepository.findMany(QueryBuilder.supplierPurchaseQuery());

        SupplierPurchaseEntity.printTableHead();
        supplierPurchaseStream.forEach(SupplierPurchaseEntity::printTableRow);
    }

    private RepositoryType getRepoName() {
        System.out.println("Available repositories: " + Arrays.toString(RepositoryType.values()));

        String repoName = InputParser.parseString("repository name", true).toLowerCase();

        try {
            return RepositoryType.valueOf(repoName.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid repository name. Please enter a valid repository name.");
            return getRepoName();
        }
    }
}
