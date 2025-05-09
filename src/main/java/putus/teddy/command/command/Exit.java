package putus.teddy.command.command;

import putus.teddy.printer.Printer;

/**
 * Command to exit the application.
 */
public class Exit implements Command {

    /**
     * Main method of the command.
     * Prints an exit message.
     *
     * @return EXIT result.
     */
    public Result execute() {
        Printer.info("Exiting the application...");
        return Result.EXIT;
    }
}
