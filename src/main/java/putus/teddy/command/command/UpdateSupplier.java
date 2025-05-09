package putus.teddy.command.command;


import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.SupplierEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.data.repository.Repository;
import putus.teddy.printer.Printer;

/**
 * Command to update an existing supplier's information in the system.
 * This class handles the retrieval of the supplier entity,
 * validates the new information, and updates it in the repository.
 */
public class UpdateSupplier implements Command {

    private final Repository<SupplierEntity> supplierRepository;
    private SupplierEntity supplier;

    public UpdateSupplier(Repository<SupplierEntity> supplierRepository) {
        this.supplierRepository = supplierRepository;
    }


    /**
     * Main method of the command.
     * Retrieves the supplier ID from user input,
     * validates the new information, and updates the supplier entity.
     *
     * @return Success or Failure.
     */
    public Result execute() {
        Printer.info("Updating supplier information...");
        String supplierId = ValidatedInputParser.parseString("Supplier ID", true, 1, 36);
        supplier = supplierRepository.findOne(QueryBuilder.searchSupplierById(supplierId));

        if (supplier == null) {
            Printer.warning("Supplier not found.");
            return Result.FAILURE;
        }

        try {
            updateSupplier(supplier);
        } catch (Exception e) {
            Printer.error(e.getMessage());
            return Result.FAILURE;
        }

        Printer.success("Supplier information updated successfully.");

        return Result.SUCCESS;
    }

    /**
     * Updates the supplier entity with new information.
     *
     * @param supplier The supplier entity to be updated.
     * @throws Exception If validation fails or if the supplier already exists.
     */
    private void updateSupplier(SupplierEntity supplier) throws Exception {
        String name = ValidatedInputParser.parseString("name", false, 1, 15);
        String phoneNumber = ValidatedInputParser.parseString("phone number", false, 1, 12);
        String email = ValidatedInputParser.parseString("email", false, 1, 20);

        validateSupplier(name, phoneNumber, email);

        if (!name.isEmpty()) supplier.setName(name);
        if (!phoneNumber.isEmpty()) supplier.setPhoneNumber(phoneNumber);
        if (!email.isEmpty()) supplier.setEmail(email);
    }

    /**
     * Validates the new supplier information.
     *
     * @param name        The new name of the supplier.
     * @param phoneNumber The new phone number of the supplier.
     * @param email       The new email of the supplier.
     * @throws Exception If validation fails or if the supplier name, phone number or email already exists.
     */
    private void validateSupplier(String name, String phoneNumber, String email) throws Exception {
        StringBuilder errorString = new StringBuilder();

        supplierRepository.findAny(QueryBuilder.searchSupplier(name, phoneNumber, email)).forEach(existingSupplier -> {
            if (existingSupplier != null && existingSupplier != supplier) {
                if (existingSupplier.getName().equals(name)) errorString.append("Name already exists.\n");
                if (existingSupplier.getPhoneNumber().equals(phoneNumber))
                    errorString.append("Phone number already exists.\n");
                if (existingSupplier.getEmail().equals(email)) errorString.append("Email already exists.\n");
            }
        });

        if (!errorString.isEmpty()) {
            throw new Exception(errorString + "Supplier already exists.");
        }
    }
}
