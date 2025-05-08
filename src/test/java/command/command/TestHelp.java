package command.command;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import putus.teddy.command.command.Command;
import putus.teddy.command.command.Exit;
import putus.teddy.command.command.Help;
import putus.teddy.printer.Printer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.startsWith;

public class TestHelp {
    @Test
    public void testHelp() {
        try (MockedStatic<Printer> mockPrinter = Mockito.mockStatic(Printer.class)) {
            assertEquals(Command.Result.SUCCESS, new Help().execute());
            mockPrinter.verify(() -> Printer.info(startsWith("Available commands:")));
        }
    }
}
