package putus.teddy.command.command;

import putus.teddy.data.entity.SupplierPurchaseEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.printer.Printer;

import java.time.LocalDate;
import java.util.Map;

public class OrderStock implements Command {
    public Result execute() {
        Printer.info("Ordering stock...");

        SupplierPurchaseEntity newOrder = createSupplierPurchaseEntity();

        try{
            validateItem(newOrder.getItemName());
            validateSupplier(newOrder.getSupplierName());
        }catch (Exception e){
            Printer.error(e.getMessage());
            return Result.FAILURE;
        }

        supplierPurchaseRepository.create(newOrder);
        Printer.success("Order placed successfully. Order ID is " + newOrder.getId());

        return Result.SUCCESS;
    }

    private SupplierPurchaseEntity createSupplierPurchaseEntity() {
        return new SupplierPurchaseEntity(
                ValidatedInputParser.parseString("supplier name", true, 1, 15),
                LocalDate.now().toString(),
                ValidatedInputParser.parseString("item name", true, 1, 15),
                ValidatedInputParser.parseQuantity("quantity", true),
                ValidatedInputParser.parseAmount("price", true)
        );
    }

    private void validateSupplier(String supplierName) throws Exception {
        if (supplierRepository.findOne(Map.of("name", supplierName)) == null) {
            throw new Exception("Supplier does not exist. Please register supplier first.");
        }
    }

    private void validateItem(String itemName) throws Exception {
        if (inventoryRepository.findOne(Map.of("itemName", itemName)) == null) {
            throw new Exception("Item does not exist. Please register item first.");
        }
    }
}
