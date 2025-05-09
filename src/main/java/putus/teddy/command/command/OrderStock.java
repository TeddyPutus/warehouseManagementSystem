package putus.teddy.command.command;

import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.InventoryEntity;
import putus.teddy.data.entity.SupplierEntity;
import putus.teddy.data.entity.SupplierPurchaseEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.data.repository.Repository;
import putus.teddy.printer.Printer;

import java.time.LocalDate;

public class OrderStock implements Command {

    private final Repository<SupplierPurchaseEntity> supplierPurchaseRepository;
    private final Repository<SupplierEntity> supplierRepository;
    private final Repository<InventoryEntity> inventoryRepository;

    public OrderStock(Repository<SupplierPurchaseEntity> supplierPurchaseRepository,
                      Repository<SupplierEntity> supplierRepository,
                      Repository<InventoryEntity> inventoryRepository) {
        this.supplierPurchaseRepository = supplierPurchaseRepository;
        this.supplierRepository = supplierRepository;
        this.inventoryRepository = inventoryRepository;
    }

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
        if (supplierRepository.findOne(QueryBuilder.searchSupplierByName(supplierName)) == null) {
            throw new Exception("Supplier does not exist. Please register supplier first.");
        }
    }

    private void validateItem(String itemName) throws Exception {
        if (inventoryRepository.findOne(QueryBuilder.searchInventoryByItemName(itemName)) == null) {
            throw new Exception("Item does not exist. Please register item first.");
        }
    }
}
