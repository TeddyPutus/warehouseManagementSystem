package command.registry;

import org.junit.Test;
import putus.teddy.command.parser.CommandType;
import putus.teddy.command.registry.CommandRegistry;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestCommandRegistry {
    CommandRegistry commandRegistry = new CommandRegistry();

    @Test
    public void testProcessCommandInvalid() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        assertFalse(commandRegistry.processCommand(CommandType.DEFAULT));

        String expectedOutput = "Unimplemented command: DEFAULT\n";
        assertTrue(outContent.toString().contains(expectedOutput));
    }

    @Test
    public void testProcessCommand() {
        assertFalse(commandRegistry.processCommand(CommandType.HELP));
        assertTrue(commandRegistry.processCommand(CommandType.EXIT));
    }
}
