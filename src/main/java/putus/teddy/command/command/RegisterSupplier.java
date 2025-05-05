package putus.teddy.command.command;

import putus.teddy.data.builder.EntityBuilder;
import putus.teddy.data.entity.SupplierEntity;
import putus.teddy.data.repository.InMemoryRepository;

import java.util.Map;

public class RegisterSupplier implements Command {

    @Override
    public boolean execute() {
        System.out.println("Registering supplier...");
        SupplierEntity newSupplier = EntityBuilder.buildSupplierEntity();

        if(supplierRepository.findOne(Map.of("name", newSupplier.getName())) != null) {
            System.out.println("Supplier already exists.");
            return false;
        }

        supplierRepository.create(newSupplier);
        System.out.println("Supplier registered successfully.");
        return false;
    }

}
