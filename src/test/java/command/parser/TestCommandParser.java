package command.parser;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import putus.teddy.command.parser.CommandParser;
import putus.teddy.command.parser.CommandType;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestCommandParser {
    ByteArrayOutputStream outContent;
    static Scanner scannerMock = mock(Scanner.class);

    @BeforeClass
    public static void setUpClass() {
        CommandParser.setScanner(scannerMock);
    }


    @Before
    public void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }


    @Test
    public void testParseCommand() {
        when(scannerMock.nextLine()).thenReturn("help");

        CommandType expectedType = CommandType.HELP;
        CommandType actualType = CommandParser.parseCommand();

        assertEquals(expectedType, actualType);
    }

    @Test
    public void testParseCommandHandlesInvalidCommand() {
        when(scannerMock.nextLine()).thenReturn("invalid_command").thenReturn("help");

        CommandType expectedType = CommandType.HELP;

        CommandType actualType = CommandParser.parseCommand();

        String expectedOutput = "Invalid command. Please try again.\n";
        assertTrue(outContent.toString().contains(expectedOutput));

        assertEquals(expectedType, actualType);
    }

    @Test
    public void testParseCommandCaseInsensitive() {
        when(scannerMock.nextLine()).thenReturn("RegIstER_SUpplIER");

        CommandType expectedType = CommandType.REGISTER_SUPPLIER;
        CommandType actualType = CommandParser.parseCommand();

        assertEquals(expectedType, actualType);
    }

    @Test
    public void testParseCommandHandlesEmptyInput() {
        when(scannerMock.nextLine()).thenReturn("").thenReturn("help");

        CommandType expectedType = CommandType.HELP;
        CommandType actualType = CommandParser.parseCommand();

        assertEquals(expectedType, actualType);

        String expectedOutput = "Invalid command. Please try again.\n";
        assertTrue(outContent.toString().contains(expectedOutput));
    }
}
