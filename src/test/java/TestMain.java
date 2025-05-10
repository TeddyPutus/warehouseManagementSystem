import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import putus.teddy.Main;
import putus.teddy.command.parser.CommandParser;
import putus.teddy.command.parser.CommandType;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.printer.Printer;

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.times;

public class TestMain {

    MockedStatic<Printer> mockPrinter;
    MockedStatic<ValidatedInputParser> mockParser;
    MockedStatic<CommandParser> mockCommandParser;

    @Before
    public void setUp() {
        mockPrinter = Mockito.mockStatic(Printer.class);
        mockParser = Mockito.mockStatic(ValidatedInputParser.class);
        mockCommandParser = Mockito.mockStatic(CommandParser.class);
    }

    @After
    public void tearDown() {
        mockPrinter.close();
        mockParser.close();
        mockCommandParser.close();
    }

    @Test
    public void testMain() {
        mockCommandParser.when(CommandParser::parseCommand).thenReturn(CommandType.HELP).thenReturn(CommandType.EXIT);

        Main.main(new String[]{});

        mockPrinter.verify(Printer::logo);
        mockPrinter.verify(() -> Printer.success("Welcome to BNU Industries!"));
        mockPrinter.verify(() -> Printer.success("Type help to see the list of commands."));

        mockPrinter.verify(() -> Printer.info(contains("Available commands: ")));
        mockPrinter.verify(() -> Printer.info(contains("Exiting the application...")));
    }
}