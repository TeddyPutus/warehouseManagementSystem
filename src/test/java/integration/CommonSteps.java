package integration;

import net.sf.expectit.Expect;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.sf.expectit.matcher.Matchers.contains;
import static org.junit.Assert.assertFalse;
import static putus.teddy.printer.Printer.*;

public class CommonSteps {

    public static void containsInfo(Expect expect, String expected) throws IOException {
        assert expect.expect(contains(expected)).isSuccessful();
    }

    public static void containsWarning(Expect expect, String expected) throws IOException {
        assert expect.expect(contains(YELLOW + expected)).isSuccessful();
    }

    public static void containsError(Expect expect, String expected) throws IOException {
        assert expect.expect(contains(RED + expected)).isSuccessful();
    }

    public static void containsSuccess(Expect expect, String expected) throws IOException {
        assert expect.expect(contains(GREEN + expected)).isSuccessful();
    }

    public static void containsAlert(Expect expect, String expected) throws IOException {
        assert expect.expect(contains("ALERT")).isSuccessful();
        assert expect.expect(contains(RED + expected)).isSuccessful();
    }

    public static void notContains(Expect expect, String expected) throws IOException {
        assert !expect.expect(contains(expected)).isSuccessful();
    }

    public static String getUuid(String response){
        Pattern pattern = Pattern.compile("[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}");
        Matcher matcher = pattern.matcher(response);
        matcher.find();
        return matcher.group(0);
    }
}
