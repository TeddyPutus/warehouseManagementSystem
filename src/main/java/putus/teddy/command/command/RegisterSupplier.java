package putus.teddy.command.command;


import putus.teddy.data.entity.SupplierEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.printer.Printer;

import java.util.Map;

public class RegisterSupplier implements Command {

    @Override
    public boolean execute() {
        Printer.info("Registering supplier...");

        SupplierEntity newSupplier = createSupplierEntity();

        if(supplierRepository.findOne(Map.of("name", newSupplier.getName())) != null) {
            Printer.error("Supplier already exists.");
            return false;
        }

        supplierRepository.create(newSupplier);
        Printer.success("Supplier registered successfully.");
        return false;
    }

    private SupplierEntity createSupplierEntity() {
        return new SupplierEntity(
                ValidatedInputParser.parseString("name", true, 1, 15),
                ValidatedInputParser.parseString("phone number", true, 1, 12),
                ValidatedInputParser.parseString("email", true, 1, 20)
        );
    }

}
