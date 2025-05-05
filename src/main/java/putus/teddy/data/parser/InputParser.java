package putus.teddy.data.parser;

import putus.teddy.printer.Printer;

import java.util.Scanner;

public class InputParser {
    private static Scanner scanner = new Scanner(System.in);

    public static void setScanner(Scanner scanner) {
        InputParser.scanner = scanner;
    }

    public static Double parseDouble(String fieldName, boolean isRequired) {
        try {
            Printer.info("Enter " + fieldName + ": ", false);
            String input = scanner.nextLine();
            if (input.isEmpty() && isRequired) {
                Printer.warning(fieldName + " is required. Please enter a value.");
                return parseDouble(fieldName, isRequired);
            } else if (input.isEmpty()) {
                return Double.MIN_VALUE;
            }
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            Printer.error("Invalid input. Please enter a valid double value.");
            return parseDouble(fieldName, isRequired);
        }
    }

    public static Integer parseInt(String fieldName, boolean isRequired) {
        try {
            Printer.info("Enter " + fieldName + ": ", false);
            String input = scanner.nextLine();

            if (input.isEmpty() && isRequired) {
                Printer.warning(fieldName + " is required. Please enter a value.");
                return parseInt(fieldName, isRequired);
            } else if (input.isEmpty()) {
                return Integer.MIN_VALUE;
            }
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            Printer.error("Invalid input. Please enter a valid integer value.");
            return parseInt(fieldName, isRequired);
        }
    }

    public static String parseString(String fieldName, boolean isRequired) {
        Printer.info("Enter " + fieldName + ": ", false);
        String input = scanner.nextLine();
        if (input.isEmpty() && isRequired) {
            Printer.warning(fieldName + " is required. Please enter a value.");
            return parseString(fieldName, isRequired);
        }
        return input;
    }
}
