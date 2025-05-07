package command.command;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;
import putus.teddy.command.command.Command;
import putus.teddy.command.command.FindInventory;
import putus.teddy.command.command.FindSuppliers;
import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.SupplierEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.printer.Printer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;

public class TestFindSupplier {
    static ByteArrayOutputStream outContent;
    FindSuppliers command = new FindSuppliers();

    static SupplierEntity entity1 = new SupplierEntity("supplier1", "1234", "email");
    static SupplierEntity entity2 = new SupplierEntity("supplier2", "5678", "email2");
    static SupplierEntity entity3 = new SupplierEntity("supplier3", "1234", "email3");

    @BeforeClass
    public static void classSetUp() {
        FindInventory.supplierRepository.deleteMany(List.of(entity -> true));
        FindInventory.supplierRepository.create(entity1);
        FindInventory.supplierRepository.create(entity2);
        FindInventory.supplierRepository.create(entity3);
        outContent = new ByteArrayOutputStream();
        Printer.setOutputStream(new PrintStream(outContent));
    }

    @Test
    public void testFindSupplier() {

        try (MockedStatic<ValidatedInputParser> mockParser = org.mockito.Mockito.mockStatic(ValidatedInputParser.class)) {
            mockParser.when(()-> ValidatedInputParser.parseString("name", false,1,15)).thenReturn("");
            mockParser.when(()-> ValidatedInputParser.parseString("phone number", false,1,12)).thenReturn("1234");
            mockParser.when(()-> ValidatedInputParser.parseString("email", false,1,20)).thenReturn("");

            Command.Result result = command.execute();
            assertEquals(Command.Result.SUCCESS, result);

            String output = outContent.toString();

            assertTrue(output.contains(SupplierEntity.getTableHead()));
            assertTrue(output.contains("supplier1"));
            assertTrue(output.contains("supplier3"));


            assertFalse(output.contains("supplier2"));
        }
    }

    @Test
    public void testFindInventoryWithNoResults() {
        try (MockedStatic<ValidatedInputParser> mockParser = org.mockito.Mockito.mockStatic(ValidatedInputParser.class)) {
            mockParser.when(()-> ValidatedInputParser.parseString("name", false,1,15)).thenReturn("");
            mockParser.when(()-> ValidatedInputParser.parseString("phone number", false,1,12)).thenReturn("9999");
            mockParser.when(()-> ValidatedInputParser.parseString("email", false,1,20)).thenReturn("");
            mockParser.when(()-> ValidatedInputParser.parseAmount(anyString(),anyBoolean())).thenReturn(Double.MIN_VALUE);
            mockParser.when(()-> ValidatedInputParser.parseQuantity(anyString(),anyBoolean())).thenReturn(10);

            Command.Result result = command.execute();
            assertEquals(Command.Result.SUCCESS, result);

            assertFalse(outContent.toString().contains("supplier1"));
            assertFalse(outContent.toString().contains("supplier2"));
            assertFalse(outContent.toString().contains("supplier3"));
        }
    }
}
