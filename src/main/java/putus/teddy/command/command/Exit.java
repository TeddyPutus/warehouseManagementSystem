package putus.teddy.command.command;

import putus.teddy.printer.Printer;

public class Exit implements Command {

    public boolean execute() {
        Printer.info("Exiting the application...");
        return true;
    }
}
