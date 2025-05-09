package putus.teddy.data.parser;

import putus.teddy.printer.Printer;

import java.util.Scanner;

/**
 * InputParser is responsible for parsing user input.
 * It provides methods to parse different types of input (String, Integer, Double).
 */
public class InputParser {
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Sets the scanner to be used for input parsing.
     *
     * @param scanner The scanner to be set.
     */
    public static void setScanner(Scanner scanner) {
        InputParser.scanner = scanner;
    }

    /**
     * Parses a double value from user input.
     * If the input is invalid, it will prompt the user again.
     * If the field is required and the input is empty, it will prompt the user again.
     * If the field is not required and the input is empty, it will return Double.MIN_VALUE.
     *
     * @param fieldName The name of the field to be parsed.
     * @param isRequired Indicates if the field is required.
     * @return The parsed double value.
     */
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

    /**
     * Parses an integer value from user input.
     * If the input is invalid, it will prompt the user again.
     * If the field is required and the input is empty, it will prompt the user again.
     * If the field is not required and the input is empty, it will return Integer.MIN_VALUE.
     *
     * @param fieldName The name of the field to be parsed.
     * @param isRequired Indicates if the field is required.
     * @return The parsed integer value.
     */
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

    /**
     * Parses a string value from user input.
     * If the field is required and the input is empty, it will prompt the user again.
     *
     * @param fieldName The name of the field to be parsed.
     * @param isRequired Indicates if the field is required.
     * @return The parsed string value.
     */
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
