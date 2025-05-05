package putus.teddy.command.command;

import putus.teddy.data.builder.EntityBuilder;
import putus.teddy.data.entity.SupplierEntity;
import putus.teddy.printer.Printer;

import java.util.Map;

public class RegisterSupplier implements Command {

    @Override
    public boolean execute() {
        Printer.info("Registering supplier...");
        SupplierEntity newSupplier = EntityBuilder.buildSupplierEntity();

        if(supplierRepository.findOne(Map.of("name", newSupplier.getName())) != null) {
            Printer.error("Supplier already exists.");
            return false;
        }

        supplierRepository.create(newSupplier);
        Printer.success("Supplier registered successfully.");
        return false;
    }

}
