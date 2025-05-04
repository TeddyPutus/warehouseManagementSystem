package putus.teddy.command.parser;

import java.util.Scanner;

public class CommandParser {
    private final Scanner scanner;

    public CommandParser() {
        this.scanner = new Scanner(System.in);
    }

    public CommandParser(Scanner scanner) {
        this.scanner = scanner;
    }

    public CommandType parseCommand() {
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