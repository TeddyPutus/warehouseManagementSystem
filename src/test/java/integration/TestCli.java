package integration;

import org.junit.*;

import java.io.IOException;

import static integration.CommonSteps.containsInfo;
import static net.sf.expectit.matcher.Matchers.contains;

public class TestCli extends IntegrationTester{

    @Test
    public void testHelp() throws IOException {
        containsInfo(expect, "Welcome to BNU Industries!");
        containsInfo(expect,"Type help to see the list of commands.");

        expect.sendLine("help");

        expect.expect(contains("Available commands:"));
    }
}
