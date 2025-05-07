package command.command;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import putus.teddy.command.command.Command;
import putus.teddy.command.command.Help;
import putus.teddy.printer.Printer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class TestHelp {
    static ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeClass
    public static void setUp() {
        outContent = new ByteArrayOutputStream();
        Printer.setOutputStream(new PrintStream(outContent));
    }

    @Test
    public void testHelp() {
        assertEquals(Command.Result.SUCCESS, new Help().execute());

        assertTrue(outContent.toString().contains("Available commands:"));
    }
}
