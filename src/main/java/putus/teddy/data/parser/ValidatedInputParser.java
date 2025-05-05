package putus.teddy.data.parser;

import putus.teddy.printer.Printer;

public class ValidatedInputParser extends InputParser {

    public static Double parseAmount(String fieldName, boolean isRequired) {
        Double doubleValue = parseDouble(fieldName, isRequired);
        if (isRequired && (doubleValue < 0)) {
            Printer.warning("Amount cannot be negative. Please enter a valid amount.");
            return parseAmount(fieldName, true);
        }else if(doubleValue != Double.MIN_VALUE && doubleValue < 0){
            Printer.warning("Amount cannot be negative. Please enter a valid amount.");
            return parseAmount(fieldName, false);
        }
        return Math.abs(doubleValue);
    }

    public static Integer parseQuantity(String fieldName, boolean isRequired) {
        Integer value = parseInt(fieldName, isRequired);
        while (isRequired && (value < 1)) {
            Printer.warning("Quantity must be at least one. Please enter a valid quantity.");
            value = parseInt(fieldName, true);
        }
        return value;
    }

    public static String parseString(String fieldName, boolean isRequired, int minLength, int maxLength) {
        String input = InputParser.parseString(fieldName, isRequired);
        if (isRequired && (input.length() < minLength || input.length() > maxLength)) {
            Printer.warning("Input length must be between " + minLength + " and " + maxLength + " characters. Please enter a valid value.");
            return parseString(fieldName, true, minLength, maxLength);
        }
        return input;
    }
}
