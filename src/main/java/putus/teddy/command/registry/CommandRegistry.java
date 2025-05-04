package putus.teddy.command.registry;

import putus.teddy.command.command.Command;
import putus.teddy.command.command.Exit;
import putus.teddy.command.command.Help;
import putus.teddy.command.parser.CommandType;

import java.util.EnumMap;

public class CommandRegistry {
    private final EnumMap<CommandType, Command> commandHandlers = new EnumMap<>(CommandType.class);

    public CommandRegistry() {

        commandHandlers.put(CommandType.HELP, new Help());
        commandHandlers.put(CommandType.EXIT, new Exit());
    }

    public boolean processCommand(CommandType command){
        Command handler = commandHandlers.get(command);
        if (handler != null) {
            return handler.execute();
        } else {
            System.out.println("Unimplemented command: " + command);
        }
        return false;
    }
}
