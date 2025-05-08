package putus.teddy.command.command;

import putus.teddy.command.parser.CommandType;
import putus.teddy.printer.Printer;

import java.util.Arrays;

public class Help implements Command {
    public Result execute() {
        Printer.infoLine("Available commands: " + Arrays.stream(CommandType.values()).filter(value -> value != CommandType.DEFAULT).toList());
        return Result.SUCCESS;
    }
}
