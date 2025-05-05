package putus.teddy.command.command;


import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.SupplierEntity;
import putus.teddy.data.parser.InputParser;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.printer.Printer;

import java.util.Map;

public class UpdateSupplier implements Command {

    public boolean execute() {
        Printer.info("Updating supplier information...");
        String supplierId = ValidatedInputParser.parseString("Supplier ID", true, 1, 36);
        SupplierEntity supplier = supplierRepository.findOne(Map.of("id", supplierId));

        if (supplier == null) {
            Printer.warning("Supplier not found.");
            return false;
        }

        supplier.update(QueryBuilder.supplierQuery());

        Printer.success("Supplier information updated successfully.");

        return false;
    }
}
