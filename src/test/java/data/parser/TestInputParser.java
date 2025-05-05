package data.parser;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import putus.teddy.data.parser.InputParser;
import putus.teddy.printer.Printer;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

public class TestInputParser {
    static ByteArrayOutputStream outContent;
    static Scanner scannerMock = mock(Scanner.class);

    @BeforeClass
    public static void setUpClass() {
        InputParser.setScanner(scannerMock);
        outContent = new ByteArrayOutputStream();
        Printer.setOutputStream(new PrintStream(outContent));
    }

    @Test
    public void testParseDoubleValidInput() {
        when(scannerMock.nextLine()).thenReturn("10.5");

        Double expectedValue = 10.5;
        Double actualValue = InputParser.parseDouble( "Test Field", true);

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testParseDoubleInvalidInput() {
        when(scannerMock.nextLine()).thenReturn("invalid_input").thenReturn("20.5");

        Double expectedValue = 20.5;
        Double actualValue = InputParser.parseDouble("Test Field", true);

        String expectedOutput = "Invalid input. Please enter a valid double value.";
        assertTrue(outContent.toString().contains(expectedOutput));

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testParseDoubleEmptyInputWhenInputRequired() {
        when(scannerMock.nextLine()).thenReturn("").thenReturn("15.0");

        Double expectedValue = 15.0;
        Double actualValue = InputParser.parseDouble( "Test Field", true);

        String expectedOutput = "Test Field is required. Please enter a value.";
        assertTrue(outContent.toString().contains(expectedOutput));

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testParseDoubleEmptyInputWhenInputNotRequired() {
        when(scannerMock.nextLine()).thenReturn("");

        Double expectedValue = Double.MIN_VALUE;
        Double actualValue = InputParser.parseDouble("Test Field", false);

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testParseIntValidInput() {
        String simulatedInput = "10";
        when(scannerMock.nextLine()).thenReturn(simulatedInput);

        int expectedValue = 10;
        int actualValue = InputParser.parseInt("Test Field", true);

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testParseIntInvalidInput() {
        when(scannerMock.nextLine()).thenReturn("invalid_input").thenReturn("20");

        int expectedValue = 20;
        int actualValue = InputParser.parseInt("Test Field", true);

        String expectedOutput = "Invalid input. Please enter a valid integer value.";
        assertTrue(outContent.toString().contains(expectedOutput));

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testParseIntEmptyInputWhenInputRequired() {
        when(scannerMock.nextLine()).thenReturn("").thenReturn("15");

        int expectedValue = 15;
        int actualValue = InputParser.parseInt("Test Field", true);

        String expectedOutput = "Test Field is required. Please enter a value.";
        assertTrue(outContent.toString().contains(expectedOutput));

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testParseIntEmptyInputWhenInputNotRequired() {
        when(scannerMock.nextLine()).thenReturn("");

        int expectedValue = Integer.MIN_VALUE;
        int actualValue = InputParser.parseInt("Test Field", false);

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testParseStringValidInput() {
        String expectedValue = "Test String";
        when(scannerMock.nextLine()).thenReturn(expectedValue);

        String actualValue = InputParser.parseString("Test Field", true);

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testParseStringEmptyInputWhenInputRequired() {
        String expectedValue = "Test String";
        when(scannerMock.nextLine()).thenReturn("").thenReturn(expectedValue);

        String actualValue = InputParser.parseString( "Test Field", true);

        String expectedOutput = "Test Field is required. Please enter a value.";
        assertTrue(outContent.toString().contains(expectedOutput));

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testParseStringEmptyInputWhenInputNotRequired() {
        String expectedValue = "";
        when(scannerMock.nextLine()).thenReturn(expectedValue);

        String actualValue = InputParser.parseString("Test Field", false);

        assertEquals(expectedValue, actualValue);
    }
}
