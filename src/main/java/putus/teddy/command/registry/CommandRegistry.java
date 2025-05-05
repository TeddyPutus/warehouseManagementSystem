package putus.teddy.command.registry;

import putus.teddy.command.command.*;
import putus.teddy.command.parser.CommandType;
import putus.teddy.printer.Printer;

import java.util.EnumMap;

public class CommandRegistry {
    private final EnumMap<CommandType, Command> commandHandlers = new EnumMap<>(CommandType.class);

    public CommandRegistry() {
        commandHandlers.put(CommandType.HELP, new Help());
        commandHandlers.put(CommandType.EXIT, new Exit());
        commandHandlers.put(CommandType.ORDER_STOCK, new OrderStock());
        commandHandlers.put(CommandType.TAKE_DELIVERY, new TakeDelivery());
        commandHandlers.put(CommandType.CUSTOMER_ORDER, new CustomerOrder());
        commandHandlers.put(CommandType.REGISTER_SUPPLIER, new RegisterSupplier());
        commandHandlers.put(CommandType.REGISTER_ITEM, new RegisterItem());
        commandHandlers.put(CommandType.FIND_ORDERS, new FindOrders());
        commandHandlers.put(CommandType.FIND_INVENTORY, new FindInventory());
        commandHandlers.put(CommandType.FIND_STOCK_ORDERS, new FindStockOrders());
        commandHandlers.put(CommandType.FIND_SUPPLIERS, new FindSuppliers());
        commandHandlers.put(CommandType.FINANCIAL_REPORT, new GenerateReport());
        commandHandlers.put(CommandType.UPDATE_SUPPLIER_INFO, new UpdateSupplier());
    }


    public boolean processCommand(CommandType command){
        Command handler = commandHandlers.getOrDefault(command, commandHandlers.get(CommandType.HELP));
        if (handler != null) {
            return handler.execute();
        } else {
            Printer.error("Unimplemented command: " + command);
        }
        return false;
    }
}
