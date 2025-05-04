package putus.teddy.command.parser;

import java.util.Scanner;

public class CommandParser {
    private static Scanner scanner = new Scanner(System.in);

    public static void setScanner(Scanner scanner) {
        CommandParser.scanner = scanner;
    }

    public static CommandType parseCommand() {
        System.out.print("Enter command: ");
        String input = scanner.nextLine();

        try {
            return CommandType.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid command. Please try again.");
            return parseCommand();
        }
    }
}