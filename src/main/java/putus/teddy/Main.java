package putus.teddy;

import putus.teddy.command.parser.CommandParser;

import putus.teddy.command.registry.CommandRegistry;

public class Main {
    public static void main(String[] args) {
        CommandRegistry commandRegistry = new CommandRegistry();

        boolean finished = false;

        System.out.println("Welcome to BNU Industries!");
        System.out.println("Type help to see the list of commands.");

        while (!finished) {
            finished = commandRegistry.processCommand(CommandParser.parseCommand());
        }

    }
}