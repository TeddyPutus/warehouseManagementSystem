package putus.teddy.command.command;


import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.SupplierEntity;
import putus.teddy.data.parser.InputParser;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.printer.Printer;

import java.util.Map;

public class UpdateSupplier implements Command {

    public Result execute() {
        Printer.info("Updating supplier information...");
        String supplierId = ValidatedInputParser.parseString("Supplier ID", true, 1, 36);
        SupplierEntity supplier = supplierRepository.findOne(Map.of("id", supplierId));

        if (supplier == null) {
            Printer.warning("Supplier not found.");
            return Result.FAILURE;
        }

        try{
            Map<String, Object> query = getQuery(supplier);
            supplier.update(query);
        }catch(Exception e){
            Printer.error(e.getMessage());
            return Result.FAILURE;
        }

        Printer.success("Supplier information updated successfully.");

        return Result.SUCCESS;
    }

    private Map<String, Object> getQuery(SupplierEntity supplier) throws Exception{
        Map<String, Object> query = QueryBuilder.supplierQuery();
        StringBuilder errorString = new StringBuilder();

        for (Map.Entry<String, Object> entry : query.entrySet()){
            String value = (String) entry.getValue();
            String key = entry.getKey();

            SupplierEntity foundSupplier = supplierRepository.findOne(Map.of(key, value));

            if(!value.isEmpty()
                    && foundSupplier != null
                    && foundSupplier != supplier
            ){
                errorString.append("Invalid ").append(key).append(" update.\n");
            }
        }

        if(!errorString.isEmpty()){
            throw new Exception(errorString + "Supplier already exists.");
        }

        return query;
    }
}
