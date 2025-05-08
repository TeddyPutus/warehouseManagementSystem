package command.command;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import putus.teddy.command.command.Command;
import putus.teddy.command.command.Exit;
import putus.teddy.data.parser.InputParser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import putus.teddy.printer.Printer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.startsWith;

public class TestExit {

    @Test
    public void testExit() {
        try (MockedStatic<Printer> mockPrinter = Mockito.mockStatic(Printer.class)) {
            assertEquals(Command.Result.EXIT, new Exit().execute());
            mockPrinter.verify(() -> Printer.info("Exiting the application..."));
        }
    }
}
