package command.command;

import org.junit.Test;
import putus.teddy.command.command.Exit;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;

public class TestExit {
    @Test
    public void testExit() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        assertTrue(new Exit().execute());

        assertTrue(outContent.toString().contains("Exiting the application..."));
    }
}
