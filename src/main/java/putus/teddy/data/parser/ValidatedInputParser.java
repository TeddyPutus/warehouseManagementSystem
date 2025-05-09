package putus.teddy.data.parser;

import putus.teddy.printer.Printer;

/**
 * ValidatedInputParser is a subclass of InputParser that provides additional validation for user input.
 * It includes methods to parse and validate amounts, quantities, and strings with specific length constraints.
 */
public class ValidatedInputParser extends InputParser {

    /**
     * Parses a double value from user input and validates it as an amount.
     * If the input is invalid or negative, it will prompt the user again.
     * If the field is required and the input is empty, it will prompt the user again.
     * If the field is not required and the input is empty, it will return Double.MIN_VALUE.
     *
     * @param fieldName The name of the field to be parsed.
     * @param isRequired Indicates if the field is required.
     * @return The parsed double value as a positive amount.
     */
    public static Double parseAmount(String fieldName, boolean isRequired) {
        Double doubleValue = parseDouble(fieldName, isRequired);
        if (isRequired && (doubleValue < 0)) {
            Printer.warning("Amount cannot be negative. Please enter a valid amount.");
            return parseAmount(fieldName, true);
        }else if((doubleValue != Double.MIN_VALUE && doubleValue < 0)
                || Double.doubleToRawLongBits(doubleValue) == Double.doubleToRawLongBits(-0.0)){
            Printer.warning("Amount cannot be negative. Please enter a valid amount.");
            return parseAmount(fieldName, false);
        }
        return Math.abs(doubleValue);
    }

    /**
     * Parses an integer value from user input and validates it as a quantity.
     * If the input is invalid or less than 1, it will prompt the user again.
     * If the field is required and the input is empty, it will prompt the user again.
     * If the field is not required and the input is empty, it will return Integer.MIN_VALUE.
     *
     * @param fieldName The name of the field to be parsed.
     * @param isRequired Indicates if the field is required.
     * @return The parsed integer value as a positive quantity.
     */
    public static Integer parseQuantity(String fieldName, boolean isRequired) {
        Integer value = parseInt(fieldName, isRequired);
        while (isRequired && (value < 1)) {
            Printer.warning("Quantity must be at least one. Please enter a valid quantity.");
            value = parseInt(fieldName, true);
        }
        return value;
    }

    /**
     * Parses a string value from user input and validates its length.
     * If the input is invalid or does not meet the length constraints, it will prompt the user again.
     * If the field is required and the input is empty, it will prompt the user again.
     * If the field is not required and the input is empty, it will return an empty string.
     *
     * @param fieldName The name of the field to be parsed.
     * @param isRequired Indicates if the field is required.
     * @param minLength The minimum length of the string.
     * @param maxLength The maximum length of the string.
     * @return The parsed string value within the specified length constraints.
     */
    public static String parseString(String fieldName, boolean isRequired, int minLength, int maxLength) {
        String input = InputParser.parseString(fieldName, isRequired);
        if (isRequired && (input.length() < minLength || input.length() > maxLength)) {
            Printer.warning("Input length must be between " + minLength + " and " + maxLength + " characters. Please enter a valid value.");
            return parseString(fieldName, true, minLength, maxLength);
        }
        return input;
    }
}
