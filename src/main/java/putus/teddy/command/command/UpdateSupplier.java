package putus.teddy.command.command;


import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.SupplierEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.data.repository.Repository;
import putus.teddy.printer.Printer;

public class UpdateSupplier implements Command {

    private final Repository<SupplierEntity> supplierRepository;

    public UpdateSupplier(Repository<SupplierEntity> supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public Result execute() {
        Printer.info("Updating supplier information...");
        String supplierId = ValidatedInputParser.parseString("Supplier ID", true, 1, 36);
        SupplierEntity supplier = supplierRepository.findOne(QueryBuilder.searchSupplierById(supplierId));

        if (supplier == null) {
            Printer.warning("Supplier not found.");
            return Result.FAILURE;
        }

        try{
            updateSupplier(supplier);
        }catch(Exception e){
            Printer.error(e.getMessage());
            return Result.FAILURE;
        }

        Printer.success("Supplier information updated successfully.");

        return Result.SUCCESS;
    }

    private void updateSupplier(SupplierEntity supplier) throws Exception{
        StringBuilder errorString = new StringBuilder();

        String name = ValidatedInputParser.parseString("name", false, 1, 15);
        String phoneNumber = ValidatedInputParser.parseString("phone number", false, 1, 12);
        String email = ValidatedInputParser.parseString("email", false, 1, 20);

        SupplierEntity nameSupplier = supplierRepository.findOne(QueryBuilder.searchSupplier(name, null, null));
        SupplierEntity phoneSupplier = supplierRepository.findOne(QueryBuilder.searchSupplier(null, phoneNumber, null));
        SupplierEntity emailSupplier = supplierRepository.findOne(QueryBuilder.searchSupplier(null, null, email));

        if(!name.isEmpty() && nameSupplier != supplier) errorString.append("Invalid name update.\n");
        if(!name.isEmpty() && phoneSupplier != supplier) errorString.append("Invalid phone number update.\n");
        if(!name.isEmpty() && emailSupplier != supplier) errorString.append("Invalid email update.\n");

        if(!errorString.isEmpty()){
            throw new Exception(errorString + "Supplier already exists.");
        }

        if (!name.isEmpty()) supplier.setName(name);
        if (!phoneNumber.isEmpty()) supplier.setPhoneNumber(phoneNumber);
        if (!email.isEmpty()) supplier.setEmail(email);
    }
}
