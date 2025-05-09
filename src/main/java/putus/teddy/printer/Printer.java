package putus.teddy.printer;

import putus.teddy.data.entity.DataEntity;

import java.io.PrintStream;
import java.util.stream.Stream;

public class Printer {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";

    private static PrintStream outputStream = System.out;

    public static void info(String message, boolean isNewLine) {
        if(isNewLine) {
            outputStream.println(message);
        } else {
            outputStream.print(message);
        }
    }

    public static void setOutputStream(PrintStream outputStream) {
        Printer.outputStream = outputStream;
    }

    public static void info(String message) {
        outputStream.println(message);
    }

    public static void error(String message) {
        outputStream.println(RED + message + RESET);
    }

    public static void success(String message) {
        outputStream.println(GREEN + message + RESET);
    }

    public static void warning(String message) {
        outputStream.println(YELLOW + message + RESET);
    }

    public static void alert(String message) {
        String alertString = " ALERT ";
        String exclamationLine = "!".repeat((message.length() - alertString.length()) / 2);
        String alertLine = exclamationLine + alertString + exclamationLine;

        outputStream.println(RED + alertLine);
        outputStream.println(RED + message);
        outputStream.println(RED + alertLine + RESET);
    }

    public static void logo(){
        String logo = """
                __________        ____ ___   __      __         _________     \s
                \\______   \\ ____ |    |   \\ /  \\    /  \\_____  /   _____/     \s
                 |    |  _//    \\|    |   / \\   \\/\\/   /     \\ \\_____  \\      \s
                 |    |   \\   |  \\    |  /   \\        /  Y Y  \\/        \\     \s
                 |______  /___|  /______/     \\__/\\  /|__|_|  /_______  /     \s
                        \\/     \\/                  \\/       \\/        \\/      \s
                  ______   ______   ______   ______   ______   ______   ______\s
                 /_____/  /_____/  /_____/  /_____/  /_____/  /_____/  /_____/\s
                                                                              \s
                                                                              \s""";
        outputStream.println(BLUE + logo + RESET);
    }

    private static void printTableHead(String tableHead) {
        info("-".repeat(tableHead.length()));
        info(tableHead);
        info("-".repeat(tableHead.length()));
    }

    public static void printTable(Stream<? extends DataEntity> dataEntities, String tableHead) {
        printTableHead(tableHead);
        int[] rowCount = {0};
        dataEntities.forEach(entity -> {
            info(entity.getTableRow(), false);
            rowCount[0]++;
        });

        if (rowCount[0] == 0) {
            warning("No data found.");
        }
    }
}
