package putus.teddy.command.parser;

import java.util.Scanner;

public class InputParser {
    private final Scanner scanner;

    public InputParser() {
        this.scanner = new Scanner(System.in);
    }

    public InputParser(Scanner scanner) {
        this.scanner = scanner;
    }

    public Double parseDouble(String fieldName, boolean isRequired) {
        try {
            System.out.print("Enter " + fieldName + ": ");
            String input = scanner.nextLine();
            if (input.isEmpty() && isRequired) {
                System.out.println(fieldName + " is required. Please enter a value.");
                return parseDouble(fieldName, isRequired);
            } else if (input.isEmpty()) {
                return -1.0;
            }
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid double value.");
            return parseDouble(fieldName, isRequired);
        }
    }

    public int parseInt(String fieldName, boolean isRequired) {
        try {
            System.out.print("Enter " + fieldName + ": ");
            String input = scanner.nextLine();

            if (input.isEmpty() && isRequired) {
                System.out.println(fieldName + " is required. Please enter a value.");
                return parseInt(fieldName, isRequired);
            } else if (input.isEmpty()) {
                return -1;
            }
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid integer value.");
            return parseInt(fieldName, isRequired);
        }
    }

    public String parseString(String fieldName, boolean isRequired) {
        System.out.print("Enter " + fieldName + ": ");
        String input = scanner.nextLine();
        if (input.isEmpty() && isRequired) {
            System.out.println(fieldName + " is required. Please enter a value.");
            return parseString(fieldName, isRequired);
        }
        return input;
    }
}
