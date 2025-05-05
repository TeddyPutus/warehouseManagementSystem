package command.command;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import putus.teddy.command.command.Exit;
import putus.teddy.data.parser.InputParser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import putus.teddy.printer.Printer;

import static org.junit.Assert.assertTrue;

public class TestExit {

    @Test
    public void testExit() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        Printer.setOutputStream(new PrintStream(outContent));
        assertTrue(new Exit().execute());

        assertTrue(outContent.toString().contains("Exiting the application..."));
    }
}
