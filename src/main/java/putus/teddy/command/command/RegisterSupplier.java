package putus.teddy.command.command;


import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.SupplierEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.data.repository.Repository;
import putus.teddy.printer.Printer;

public class RegisterSupplier implements Command {

    private final Repository<SupplierEntity> supplierRepository;

    public RegisterSupplier(Repository<SupplierEntity> supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Result execute() {
        Printer.info("Registering supplier...");

        SupplierEntity newSupplier = createSupplierEntity();

        if(supplierRepository.findOne(QueryBuilder.searchSupplierByName(newSupplier.getName())) != null) {
            Printer.error("Supplier already exists.");
            return Result.FAILURE;
        }

        supplierRepository.create(newSupplier);
        Printer.success("Supplier registered successfully.");
        return Result.SUCCESS;
    }

    private SupplierEntity createSupplierEntity() {
        return new SupplierEntity(
                ValidatedInputParser.parseString("name", true, 1, 15),
                ValidatedInputParser.parseString("phone number", true, 1, 12),
                ValidatedInputParser.parseString("email", true, 1, 20)
        );
    }

}
