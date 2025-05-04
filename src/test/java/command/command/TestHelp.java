package command.command;

import org.junit.Test;
import putus.teddy.command.command.Help;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestHelp {
    @Test
    public void testHelp() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        assertFalse(new Help().execute());

        assertTrue(outContent.toString().contains("Available commands:"));
    }
}
