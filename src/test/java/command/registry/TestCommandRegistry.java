package command.registry;

import org.junit.Test;
import putus.teddy.command.command.Command;
import putus.teddy.command.parser.CommandType;
import putus.teddy.command.registry.CommandRegistry;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class TestCommandRegistry {
    CommandRegistry commandRegistry = new CommandRegistry();

    @Test
    public void testProcessCommand() {
        assertEquals(Command.Result.SUCCESS, commandRegistry.processCommand(CommandType.HELP));
        assertEquals(Command.Result.EXIT, commandRegistry.processCommand(CommandType.EXIT));
    }
}
