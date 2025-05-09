package putus.teddy.command.parser;

import putus.teddy.printer.Printer;

import java.util.Scanner;

/**
 * CommandParser is responsible for parsing user input commands.
 * It uses a Scanner to read input from the console and validates the command.
 */
public class CommandParser {
    private static Scanner scanner = new Scanner(System.in);

    public static void setScanner(Scanner scanner) {
        CommandParser.scanner = scanner;
    }

    /**
     * Parses the command entered by the user.
     * It prompts the user for input and validates the command.
     * If the command is invalid, it will prompt the user again.
     *
     * @return The parsed CommandType.
     */
    public static CommandType parseCommand() {
        Printer.info("Enter command: ", false);

        while (!scanner.hasNextLine()) {}

        String input = scanner.nextLine();

        try {
            return CommandType.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            Printer.warning("Invalid command. Please try again.");
            return parseCommand();
        }
    }
}