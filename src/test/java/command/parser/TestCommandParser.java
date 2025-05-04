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
    public void testGetUserInputValidInput() {
        String simulatedInput = "help";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        CommandType expectedType = CommandType.HELP;
        CommandType actualType = new CommandParser().getUserInput();

        assertEquals(expectedType, actualType);
    }

    @Test
    public void testGetUserInputInvalidInput() {
        String simulatedInput = "invalid_command\nhelp";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        CommandType expectedType = CommandType.HELP;

        CommandType actualType = new CommandParser().getUserInput();

        String expectedOutput = "Invalid command. Please try again.\n";
        assertTrue(outContent.toString().contains(expectedOutput));

        assertEquals(expectedType, actualType);
    }

    @Test
    public void testGetUserInputCaseInsensitive() {
        String simulatedInput = "ReGiStEr_SuPpLiEr";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        CommandType expectedType = CommandType.REGISTER_SUPPLIER;
        CommandType actualType = new CommandParser().getUserInput();

        assertEquals(expectedType, actualType);
    }

    @Test
    public void testGetUserInputEmptyInput() {
        String simulatedInput = "\nhelp";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        CommandType expectedType = CommandType.HELP;
        CommandType actualType = new CommandParser().getUserInput();

        assertEquals(expectedType, actualType);
    }

    @Test
    public void testParseDoubleValidInput() {
        String simulatedInput = "10.5";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Double expectedValue = 10.5;
        Double actualValue = new CommandParser().parseDouble("Test Field", true);

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testParseDoubleInvalidInput() {
        String simulatedInput = "invalid_input\n20.5";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Double expectedValue = 20.5;
        Double actualValue = new CommandParser().parseDouble("Test Field", true);

        String expectedOutput = "Invalid input. Please enter a valid double value.\n";
        assertTrue(outContent.toString().contains(expectedOutput));

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testParseDoubleEmptyInputWhenInputRequired() {
        String simulatedInput = "\n15.0";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Double expectedValue = 15.0;
        Double actualValue = new CommandParser().parseDouble("Test Field", true);

        String expectedOutput = "Test Field is required. Please enter a value.\n";
        assertTrue(outContent.toString().contains(expectedOutput));

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testParseDoubleEmptyInputWhenInputNotRequired() {
        String simulatedInput = "\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Double expectedValue = -1.0;
        Double actualValue = new CommandParser().parseDouble("Test Field", false);

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testParseIntValidInput() {
        String simulatedInput = "10";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        int expectedValue = 10;
        int actualValue = new CommandParser().parseInt("Test Field", true);

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testParseIntInvalidInput() {
        String simulatedInput = "invalid_input\n20";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        int expectedValue = 20;
        int actualValue = new CommandParser().parseInt("Test Field", true);

        String expectedOutput = "Invalid input. Please enter a valid integer value.\n";
        assertTrue(outContent.toString().contains(expectedOutput));

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testParseIntEmptyInputWhenInputRequired() {
        String simulatedInput = "\n15";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        int expectedValue = 15;
        int actualValue = new CommandParser().parseInt("Test Field", true);

        String expectedOutput = "Test Field is required. Please enter a value.\n";
        assertTrue(outContent.toString().contains(expectedOutput));

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testParseIntEmptyInputWhenInputNotRequired() {
        String simulatedInput = "\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        int expectedValue = -1;
        int actualValue = new CommandParser().parseInt("Test Field", false);

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testParseStringValidInput() {
        String simulatedInput = "Test String";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        String expectedValue = "Test String";
        String actualValue = new CommandParser().parseString("Test Field", true);

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testParseStringEmptyInputWhenInputRequired() {
        String simulatedInput = "\nTest String";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        String expectedValue = "Test String";
        String actualValue = new CommandParser().parseString("Test Field", true);

        String expectedOutput = "Test Field is required. Please enter a value.\n";
        assertTrue(outContent.toString().contains(expectedOutput));

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testParseStringEmptyInputWhenInputNotRequired() {
        String simulatedInput = "\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        String expectedValue = "";
        String actualValue = new CommandParser().parseString("Test Field", false);

        assertEquals(expectedValue, actualValue);
    }
}
