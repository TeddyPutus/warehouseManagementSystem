package command.command;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import putus.teddy.command.command.Help;
import putus.teddy.printer.Printer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestHelp {
    static ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeClass
    public static void setUp() {
        outContent = new ByteArrayOutputStream();
        Printer.setOutputStream(new PrintStream(outContent));
    }

    @Test
    public void testHelp() {
        assertFalse(new Help().execute());

        assertTrue(outContent.toString().contains("Available commands:"));
    }
}
