package putus.teddy.command.parser;

/**
 * Enum representing all valid commands.
 * Each command type corresponds to a specific action that can be performed.
 * They are mapped to Commands in the command registry.
 */
public enum CommandType {
    HELP,
    EXIT,
    ORDER_STOCK,
    TAKE_DELIVERY,
    CUSTOMER_ORDER,
    REGISTER_SUPPLIER,
    REGISTER_ITEM,
    FIND_ORDERS,
    FIND_INVENTORY,
    FIND_SUPPLIERS,
    FIND_STOCK_ORDERS,
    FINANCIAL_REPORT,
    UPDATE_SUPPLIER_INFO,
    DEFAULT,
}
