package putus.teddy.command.registry;

import putus.teddy.command.command.*;
import putus.teddy.command.parser.CommandType;
import putus.teddy.data.entity.*;
import putus.teddy.data.repository.InMemoryRepository;
import putus.teddy.data.repository.Repository;
import putus.teddy.printer.Printer;

import java.util.EnumMap;
import java.util.List;

public class CommandRegistry {
    private final EnumMap<CommandType, Command> commandHandlers = new EnumMap<>(CommandType.class);

    Repository<CustomerPurchaseEntity> customerPurchaseRepository = new InMemoryRepository<>();
    Repository<InventoryEntity> inventoryRepository = new InMemoryRepository<>();
    Repository<FinancialEntity> financialRepository = new InMemoryRepository<>();
    Repository<SupplierEntity> supplierRepository = new InMemoryRepository<>();
    Repository<SupplierPurchaseEntity> supplierPurchaseRepository = new InMemoryRepository<>();

    public CommandRegistry() {
        setUpTestData();

        commandHandlers.put(CommandType.HELP, new Help());
        commandHandlers.put(CommandType.EXIT, new Exit());
        commandHandlers.put(CommandType.ORDER_STOCK, new OrderStock(
                supplierPurchaseRepository,
                supplierRepository,
                inventoryRepository
        ));
        commandHandlers.put(CommandType.TAKE_DELIVERY, new TakeDelivery(
                supplierPurchaseRepository,
                inventoryRepository,
                financialRepository
        ));
        commandHandlers.put(CommandType.CUSTOMER_ORDER, new CustomerOrder(
                financialRepository,
                inventoryRepository,
                customerPurchaseRepository
        ));
        commandHandlers.put(CommandType.REGISTER_SUPPLIER, new RegisterSupplier(
                supplierRepository
        ));
        commandHandlers.put(CommandType.REGISTER_ITEM, new RegisterItem(
                financialRepository,
                inventoryRepository
        ));
        commandHandlers.put(CommandType.FIND_ORDERS, new FindOrders(
                customerPurchaseRepository
        ));
        commandHandlers.put(CommandType.FIND_INVENTORY, new FindInventory(
                inventoryRepository
        ));
        commandHandlers.put(CommandType.FIND_STOCK_ORDERS, new FindStockOrders(
                supplierPurchaseRepository
        ));
        commandHandlers.put(CommandType.FIND_SUPPLIERS, new FindSuppliers(
                supplierRepository
        ));
        commandHandlers.put(CommandType.FINANCIAL_REPORT, new GenerateReport(
                financialRepository
        ));
        commandHandlers.put(CommandType.UPDATE_SUPPLIER_INFO, new UpdateSupplier(
                supplierRepository
        ));
    }


    public Command.Result processCommand(CommandType command){
        Command handler = commandHandlers.getOrDefault(command, commandHandlers.get(CommandType.HELP));

        if (handler != null) {
            return handler.execute();
        }

        Printer.error("Unimplemented command: " + command);
        return Command.Result.FAILURE;
    }

    public void setUpTestData(){
        supplierRepository.createMany(List.of(
                new SupplierEntity("Supplier A", "123-456-7890", "supplier_a@email.com"),
                new SupplierEntity("Supplier B", "987-654-3210", "supplier_b@email.com"),
                new SupplierEntity("Supplier C", "555-555-5555", "supplier_c@email.com")));

        inventoryRepository.createMany(List.of(
                new InventoryEntity("item1",  10, 200.0),
                new InventoryEntity("item2", 20, 400.0),
                new InventoryEntity("item3",  30, 60.0),
                new InventoryEntity("item4", 4, 600.0)));

        financialRepository.createMany(List.of(
                new FinancialEntity("item1", 10, 0, 100.0, 0.0),
                new FinancialEntity("item2", 20, 0, 200.0, 0.0),
                new FinancialEntity("item3", 30, 0, 300.0, 0.0),
                new FinancialEntity("item4", 4, 0, 300.0, 0.0)));

    }

}
