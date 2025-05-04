package command.parser;

import org.junit.Before;
import org.junit.Test;
import putus.teddy.command.parser.CommandParser;
import putus.teddy.command.parser.CommandType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class TestCommandParser {
    ByteArrayOutputStream outContent;

    @Before
    public void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }


    @Test
    public void testParseCommand() {
        String simulatedInput = "help";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        CommandType expectedType = CommandType.HELP;
        CommandType actualType = new CommandParser().parseCommand();

        assertEquals(expectedType, actualType);
    }

    @Test
    public void testParseCommandHandlesInvalidCommand() {
        String simulatedInput = "invalid_command\nhelp";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        CommandType expectedType = CommandType.HELP;

        CommandType actualType = new CommandParser().parseCommand();

        String expectedOutput = "Invalid command. Please try again.\n";
        assertTrue(outContent.toString().contains(expectedOutput));

        assertEquals(expectedType, actualType);
    }

    @Test
    public void testParseCommandCaseInsensitive() {
        String simulatedInput = "ReGiStEr_SuPpLiEr";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        CommandType expectedType = CommandType.REGISTER_SUPPLIER;
        CommandType actualType = new CommandParser().parseCommand();

        assertEquals(expectedType, actualType);
    }

    @Test
    public void testParseCommandHandlesEmptyInput() {
        String simulatedInput = "\nhelp";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        CommandType expectedType = CommandType.HELP;
        CommandType actualType = new CommandParser().parseCommand();

        assertEquals(expectedType, actualType);

        String expectedOutput = "Invalid command. Please try again.\n";
        assertTrue(outContent.toString().contains(expectedOutput));
    }
}
