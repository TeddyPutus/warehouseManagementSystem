package putus.teddy.command.command;

import putus.teddy.command.parser.CommandType;
import putus.teddy.printer.Printer;

import java.util.Arrays;

/**
 * Command to display available commands.
 */
public class Help implements Command {
    /**
     * Main method of the command.
     * Displays all available commands except the "default" one.
     *
     * @return Success.
     */
    public Result execute() {
        Printer.info("Available commands: " + Arrays.stream(CommandType.values()).filter(value -> value != CommandType.DEFAULT).toList());
        return Result.SUCCESS;
    }
}
