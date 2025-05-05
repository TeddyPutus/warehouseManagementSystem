package putus.teddy;

import putus.teddy.command.command.Command;
import putus.teddy.command.parser.CommandParser;
import putus.teddy.printer.Printer;
import putus.teddy.data.entity.*;
import putus.teddy.command.registry.CommandRegistry;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        setUpTestData();

        CommandRegistry commandRegistry = new CommandRegistry();

        boolean finished = false;

        Printer.logo();

        Printer.success("Welcome to BNU Industries!");
        Printer.success("Type help to see the list of commands.");

        while (!finished) {
            finished = commandRegistry.processCommand(CommandParser.parseCommand());
        }
    }

    public static void setUpTestData(){
        Command.supplierRepository.importData(List.of(
                new SupplierEntity("Supplier A", "123-456-7890", "supplier_a@email.com"),
                new SupplierEntity("Supplier B", "987-654-3210", "supplier_b@email.com"),
                new SupplierEntity("Supplier C", "555-555-5555", "supplier_c@email.com")));

        Command.inventoryRepository.importData(List.of(
                new InventoryEntity("item1",  10, 200.0),
                new InventoryEntity("item2", 20, 400.0),
                new InventoryEntity("item3",  30, 60.0),
                new InventoryEntity("item4", 4, 600.0)));

        Command.financialRepository.importData(List.of(
                new FinancialEntity("item1", 10, 0, 100.0, 0.0),
                new FinancialEntity("item2", 20, 0, 200.0, 0.0),
                new FinancialEntity("item3", 30, 0, 300.0, 0.0),
                new FinancialEntity("item4", 4, 0, 300.0, 0.0)));

    }
}