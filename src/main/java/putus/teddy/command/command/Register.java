package putus.teddy.command.command;

import putus.teddy.data.builder.EntityBuilder;
import putus.teddy.data.entity.FinancialEntity;
import putus.teddy.data.entity.InventoryEntity;
import putus.teddy.data.entity.SupplierEntity;
import putus.teddy.data.parser.InputParser;

import java.util.Arrays;
import java.util.Map;

public class Register implements Command {
    private enum RepositoryType {
        SUPPLIER,
        INVENTORY,
    }

    public boolean execute() {
        RepositoryType repoName = getRepoName();

        switch (repoName) {
            case SUPPLIER -> registerSupplier();
            case INVENTORY -> registerInventory();
            default -> System.out.println("Unrecognised repository name: " + repoName);
        }

        return false;
    }

    public void registerInventory(){
        System.out.println("Registering item...");
        InventoryEntity newItem = EntityBuilder.buildInventoryEntity();

        if(inventoryRepository.findOne(Map.of("itemName", newItem.getItemName())) != null) {
            System.out.println("Item already exists.");
            return;
        }

        FinancialEntity financialEntity = new FinancialEntity(newItem.getItemName(), newItem.getQuantity(), 0, 0.0,0.0);

        inventoryRepository.create(newItem);
        financialRepository.create(financialEntity);

        System.out.println("Item registered successfully.");
    }

    public void registerSupplier(){
        System.out.println("Registering supplier...");
        SupplierEntity newSupplier = EntityBuilder.buildSupplierEntity();

        if(supplierRepository.findOne(Map.of("name", newSupplier.getName())) != null) {
            System.out.println("Supplier already exists.");
            return;
        }

        supplierRepository.create(newSupplier);
        System.out.println("Supplier registered successfully.");
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
