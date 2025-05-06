package integration;

import net.sf.expectit.Expect;
import net.sf.expectit.ExpectBuilder;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class IntegrationTester {
    static Process process;
    static Expect expect;
    static StringBuilder wholeBuffer = new StringBuilder();
    static Path absolutePath = Paths.get("target/warehouseManagementSystem-1.0-SNAPSHOT.jar").toAbsolutePath();

    @Before
    public void setUp() throws IOException, InterruptedException {
        process = Runtime.getRuntime().exec("java -jar " + absolutePath);
        expect = new ExpectBuilder().withInputs(process.getInputStream())
                .withOutput(process.getOutputStream())
                .withEchoOutput(wholeBuffer)
                .withEchoInput(wholeBuffer)
                .withTimeout(500, TimeUnit.MILLISECONDS)
                .build();

        TimeUnit.SECONDS.sleep(1);
    }

    @After
    public void tearDown() throws IOException {
        expect.close();
        process.destroy();
    }
}
