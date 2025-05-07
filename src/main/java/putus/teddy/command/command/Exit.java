package putus.teddy.command.command;

import putus.teddy.printer.Printer;

public class Exit implements Command {

    public Result execute() {
        Printer.info("Exiting the application...");
        return Result.EXIT;
    }
}
