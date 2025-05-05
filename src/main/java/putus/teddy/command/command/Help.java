package putus.teddy.command.command;

import putus.teddy.command.parser.CommandType;

import java.util.Arrays;

public class Help implements Command {
    public boolean execute() {
        System.out.println("Available commands: " + Arrays.toString(CommandType.values()));
        return false;
    }
}
