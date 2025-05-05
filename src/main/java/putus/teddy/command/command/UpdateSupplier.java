package putus.teddy.command.command;


import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.SupplierEntity;
import putus.teddy.data.parser.InputParser;

import java.util.Map;

public class UpdateSupplier implements Command {

    public boolean execute() {
        System.out.println("Updating supplier information...");
        String supplierId = InputParser.parseString("Supplier ID", true);
        SupplierEntity supplier = supplierRepository.findOne(Map.of("id", supplierId));

        if (supplier == null) {
            System.out.println("Supplier not found.");
            return false;
        }

        supplier.update(QueryBuilder.supplierQuery());

        System.out.println("Supplier information updated successfully.");

        return false;
    }
}
