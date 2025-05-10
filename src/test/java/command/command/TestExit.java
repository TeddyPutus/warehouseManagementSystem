package command.command;

import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import putus.teddy.command.command.Command;
import putus.teddy.command.command.Exit;

import putus.teddy.printer.Printer;

import static org.junit.Assert.assertEquals;

public class TestExit {

    @Test
    public void testExit() {
        try (MockedStatic<Printer> mockPrinter = Mockito.mockStatic(Printer.class)) {
            assertEquals(Command.Result.EXIT, new Exit().execute());
            mockPrinter.verify(() -> Printer.info("Exiting the application..."));
        }
    }
}
