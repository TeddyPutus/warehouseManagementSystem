package printer;

import org.junit.BeforeClass;
import org.junit.Test;
import putus.teddy.data.entity.SupplierEntity;
import putus.teddy.printer.Printer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

public class TestPrinter {
    static ByteArrayOutputStream outContent;

    @BeforeClass
    public static void setUpClass() {
        outContent = new ByteArrayOutputStream();
        Printer.setOutputStream(new PrintStream(outContent));
    }

    @Test
    public void testInfo() {
        String message = "This is an info message.";
        Printer.info(message);
        assertTrue(outContent.toString().contains(message));
    }

    @Test
    public void testError() {
        String message = "This is an error message.";
        Printer.error(message);
        assertTrue(outContent.toString().contains(Printer.RED + message + Printer.RESET));
    }

    @Test
    public void testSuccess() {
        String message = "This is a success message.";
        Printer.success(message);
        assertTrue(outContent.toString().contains(Printer.GREEN + message + Printer.RESET));
    }

    @Test
    public void testWarning() {
        String message = "This is a warning message.";
        Printer.warning(message);
        assertTrue(outContent.toString().contains(Printer.YELLOW + message + Printer.RESET));
    }

    @Test
    public void testAlert() {
        String message = "This is an alert message.";
        Printer.alert(message);
        assertTrue(outContent.toString().contains(Printer.RED + message));
        assertTrue(outContent.toString().contains("ALERT"));
    }

    @Test
    public void testPrintTable(){
        List<SupplierEntity> suppliers = List.of(
                new SupplierEntity("Supplier A", "01234", "supplierA@email.com"),
                new SupplierEntity("Supplier B", "45678", "supplierB@email.com"),
                new SupplierEntity("Supplier C", "89101", "supplierB@email.com")
        );

        Printer.printTable(suppliers.stream(), SupplierEntity.getTableHead());

        assertTrue(outContent.toString().contains(suppliers.get(0).getTableRow()));
        assertTrue(outContent.toString().contains(suppliers.get(1).getTableRow()));
        assertTrue(outContent.toString().contains(suppliers.get(2).getTableRow()));
        assertTrue(outContent.toString().contains(SupplierEntity.getTableHead()));
    }

    @Test
    public void testPrintTableEmptyList(){
        List<SupplierEntity> suppliers = List.of();

        Printer.printTable(suppliers.stream(), SupplierEntity.getTableHead());

        assertTrue(outContent.toString().contains("No data found."));
    }

    @Test
    public void testPrintTableHandlesExceptionThrownInPredicate(){
        List<SupplierEntity> suppliers = List.of(
                new SupplierEntity("Supplier A", "01234", "supplierA@email.com"),
                new SupplierEntity("Supplier B", "45678", "supplierB@email.com"),
                new SupplierEntity("Supplier C", "89101", "supplierB@email.com")
        );

        Stream<SupplierEntity> suppliersStream = suppliers.stream()
                .filter(supplier -> {
                    throw new RuntimeException("Test exception");
                });

        Printer.printTable(suppliersStream, SupplierEntity.getTableHead());
        assertTrue(outContent.toString().contains("Error printing table: Test exception"));
    }

    @Test
    public void testLogo(){
        Printer.logo();
        assertTrue(outContent.toString().contains(Printer.logo));
    }
}
