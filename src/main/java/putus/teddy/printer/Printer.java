package putus.teddy.printer;

import putus.teddy.data.entity.DataEntity;

import java.io.PrintStream;
import java.util.stream.Stream;

/**
 * Printer class is responsible for printing messages to the console.
 * It provides methods to print messages in different colors and formats.
 * It also provides a method to print a logo and a table of data entities.
 */
public class Printer {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String logo = """
            __________        ____ ___   __      __         _________     \s
            \\______   \\ ____ |    |   \\ /  \\    /  \\_____  /   _____/     \s
             |    |  _//    \\|    |   / \\   \\/\\/   /     \\ \\_____  \\      \s
             |    |   \\   |  \\    |  /   \\        /  Y Y  \\/        \\     \s
             |______  /___|  /______/     \\__/\\  /|__|_|  /_______  /     \s
                    \\/     \\/                  \\/       \\/        \\/      \s
          ______   ______   ______   ______   ______   ______   ______   ______\s
         /_____/  /_____/  /_____/  /_____/  /_____/  /_____/  /_____/  /_____/\s
                                                                          \s
                                                                          \s""";

    private static PrintStream outputStream = System.out;

    /**
     * Prints a message to the console without new line.
     *
     * @param message   The message to be printed.
     */
    public static void infoInline(String message) {
        outputStream.print(message);
    }

    /**
     * Sets the output stream for the printer.
     * This can be used to redirect the output to a different stream.
     *
     * @param outputStream The output stream to be set.
     */
    public static void setOutputStream(PrintStream outputStream) {
        Printer.outputStream = outputStream;
    }

    /**
     * Prints a message to the console in plain text.
     */
    public static void info(String message) {
        outputStream.println(message);
    }

    /**
     * Prints a message to the console in red. Resets the color after printing.
     */
    public static void error(String message) {
        outputStream.println(RED + message + RESET);
    }

    /**
     * Prints a message to the console in green. Resets the color after printing.
     */
    public static void success(String message) {
        outputStream.println(GREEN + message + RESET);
    }

    /**
     * Prints a message to the console in yellow. Resets the color after printing.
     */
    public static void warning(String message) {
        outputStream.println(YELLOW + message + RESET);
    }

    /**
     * Prints an alert message to the console in red.
     * The alert message is formatted with exclamation marks.
     * Resets the color after printing.
     */
    public static void alert(String message) {
        String alertString = " ALERT ";
        String exclamationLine = "!".repeat((message.length() - alertString.length()) / 2);
        String alertLine = exclamationLine + alertString + exclamationLine;

        outputStream.println(RED + alertLine);
        outputStream.println(RED + message);
        outputStream.println(RED + alertLine + RESET);
    }

    /**
     * Prints a logo to the console in blue. Resets the color after printing.
     */
    public static void logo() {
        outputStream.println(BLUE + logo + RESET);
    }

    /**
     * Prints a formatted table header to the console.
     */
    private static void printTableHead(String tableHead) {
        info("-".repeat(tableHead.length()));
        info(tableHead);
        info("-".repeat(tableHead.length()));
    }

    /**
     * Prints a table of data entities to the console.
     * It first prints the table header and then prints each entity's row.
     *
     * @param dataEntities The stream of data entities to be printed. Can be a stream of any child class of DataEntity.
     * @param tableHead    The header of the table.
     */
    public static void printTable(Stream<? extends DataEntity> dataEntities, String tableHead) {
        printTableHead(tableHead);
        int[] rowCount = {0};
        try{
            dataEntities.forEach(entity -> {
                infoInline(entity.getTableRow());
                rowCount[0]++;
            });
        } catch (Exception e) {
            error("Error printing table: " + e.getMessage());
        }finally{
            if (rowCount[0] == 0) {
                warning("No data found.");
            }
        }

    }
}
