package putus.teddy;

import putus.teddy.command.command.Command;
import putus.teddy.command.parser.CommandParser;
import putus.teddy.printer.Printer;
import putus.teddy.command.registry.CommandRegistry;

public class Main {

    public static void main(String[] args) {

        CommandRegistry commandRegistry = new CommandRegistry();

        Command.Result finished = Command.Result.SUCCESS;

        Printer.logo();

        Printer.success("Welcome to BNU Industries!");
        Printer.success("Type help to see the list of commands.");

        while (finished != Command.Result.EXIT) {
            finished = commandRegistry.processCommand(CommandParser.parseCommand());
        }
    }
}