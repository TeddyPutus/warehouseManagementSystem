package data.parser;

import org.junit.BeforeClass;
import org.junit.Test;
import putus.teddy.data.parser.InputParser;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.printer.Printer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class TestValidatedInputParser {
    static ByteArrayOutputStream outContent;
    static Scanner scannerMock = mock(Scanner.class);

    @BeforeClass
    public static void setUpClass() {
        InputParser.setScanner(scannerMock);
        outContent = new ByteArrayOutputStream();
        Printer.setOutputStream(new PrintStream(outContent));
    }

    @Test
    public void testParseAmountValidInput() {
        when(scannerMock.nextLine()).thenReturn("10.5");

        Double expectedValue = 10.5;
        Double actualValue = ValidatedInputParser.parseAmount("Test Field", true);

        assertEquals(expectedValue, actualValue, 0.001);
    }

    @Test
    public void testParseAmountNegativeInput() {
        when(scannerMock.nextLine()).thenReturn("-10.5").thenReturn("20.5");

        Double expectedValue = 20.5;
        Double actualValue = ValidatedInputParser.parseAmount("Test Field", true);

        String expectedOutput = "Amount cannot be negative. Please enter a valid amount.";
        assertTrue(outContent.toString().contains(expectedOutput));

        assertEquals(expectedValue, actualValue, 0.001);
    }

    @Test
    public void testParseAmountEmptyInputWhenInputRequired() {
        when(scannerMock.nextLine()).thenReturn("").thenReturn("15.0");

        Double expectedValue = 15.0;
        Double actualValue = ValidatedInputParser.parseAmount("Test Field", true);

        String expectedOutput = "Test Field is required. Please enter a value.";
        assertTrue(outContent.toString().contains(expectedOutput));

        assertEquals(expectedValue, actualValue, 0.001);
    }

    @Test
    public void testParseAmountEmptyInputWhenInputNotRequired() {
        when(scannerMock.nextLine()).thenReturn("");

        Double actualValue = ValidatedInputParser.parseAmount("Test Field", false);

        assertEquals(Double.MIN_VALUE, actualValue, 0.001);
    }

    @Test
    public void testParseQuantityValidInput() {
        when(scannerMock.nextLine()).thenReturn("5");

        Integer expectedValue = 5;
        Integer actualValue = ValidatedInputParser.parseQuantity("Test Field", true);

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testParseQuantityNegativeInput() {
        when(scannerMock.nextLine()).thenReturn("-5").thenReturn("10");

        Integer expectedValue = 10;
        Integer actualValue = ValidatedInputParser.parseQuantity("Test Field", true);

        String expectedOutput = "Quantity must be at least one. Please enter a valid quantity.";
        assertTrue(outContent.toString().contains(expectedOutput));

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testParseQuantityEmptyInputWhenInputRequired() {
        when(scannerMock.nextLine()).thenReturn("").thenReturn("3");

        Integer expectedValue = 3;
        Integer actualValue = ValidatedInputParser.parseQuantity("Test Field", true);

        String expectedOutput = "Test Field is required. Please enter a value.";
        assertTrue(outContent.toString().contains(expectedOutput));

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testParseQuantityEmptyInputWhenInputNotRequired() {
        when(scannerMock.nextLine()).thenReturn("");

        Integer expectedValue = Integer.MIN_VALUE;
        Integer actualValue = ValidatedInputParser.parseQuantity("Test Field", false);

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testParseStringValidInput() {
        when(scannerMock.nextLine()).thenReturn("Valid String");

        String expectedValue = "Valid String";
        String actualValue = ValidatedInputParser.parseString("Test Field", true, 5, 20);

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testParseStringInvalidLengthInput() {
        when(scannerMock.nextLine()).thenReturn("Short").thenReturn("Valid String");

        String expectedValue = "Valid String";
        String actualValue = ValidatedInputParser.parseString("Test Field", true, 10, 20);

        String expectedOutput = "Input length must be between 10 and 20 characters. Please enter a valid value.";
        assertTrue(outContent.toString().contains(expectedOutput));

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testParseStringEmptyInputWhenInputRequired() {
        when(scannerMock.nextLine()).thenReturn("").thenReturn("Valid String");

        String expectedValue = "Valid String";
        String actualValue = ValidatedInputParser.parseString("Test Field", true, 5, 20);

        String expectedOutput = "Test Field is required. Please enter a value.";
        assertTrue(outContent.toString().contains(expectedOutput));

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testParseStringEmptyInputWhenInputNotRequired() {
        when(scannerMock.nextLine()).thenReturn("");

        String expectedValue = "";
        String actualValue = ValidatedInputParser.parseString("Test Field", false, 5, 20);

        assertEquals(expectedValue, actualValue);
    }

}
