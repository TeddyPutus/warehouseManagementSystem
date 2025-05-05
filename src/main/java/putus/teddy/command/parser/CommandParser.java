package putus.teddy.command.parser;

import putus.teddy.printer.Printer;

import java.util.Scanner;

public class CommandParser {
    private static Scanner scanner = new Scanner(System.in);

    public static void setScanner(Scanner scanner) {
        CommandParser.scanner = scanner;
    }

    public static CommandType parseCommand() {
        Printer.info("Enter command: ", false);
        String input = scanner.nextLine();

        try {
            return CommandType.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            Printer.warning("Invalid command. Please try again.");
            return parseCommand();
        }
    }
}