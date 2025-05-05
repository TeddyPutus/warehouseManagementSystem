package command.command;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import putus.teddy.command.command.FindInventory;
import putus.teddy.command.command.GenerateReport;
import putus.teddy.data.entity.CustomerPurchaseEntity;
import putus.teddy.data.entity.FinancialEntity;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;

public class TestGenerateReport {
    ByteArrayOutputStream outContent;
    GenerateReport command = new GenerateReport();

    static FinancialEntity entity1 = new FinancialEntity("item1", 1, 10, 10.0, 20.0);
    static FinancialEntity entity2 = new FinancialEntity("item2", 2, 20, 20.0, 40.0);
    static FinancialEntity entity3 = new FinancialEntity("item3", 3, 10, 30.0, 60.0);

    @BeforeClass
    public static void classSetUp() {
        FindInventory.financialRepository.create(entity1);
        FindInventory.financialRepository.create(entity2);
        FindInventory.financialRepository.create(entity3);
    }

    @Before
    public void testSetUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testGenerateReport() {
        command.execute();

        String output = outContent.toString();

        assertTrue(output.contains("item1"));
        assertTrue(output.contains("item2"));
        assertTrue(output.contains("item3"));

        assertTrue(output.contains("Total Sales: 120.0"));
        assertTrue(output.contains("Total Expenses: 60.0"));
        assertTrue(output.contains("Net Profit: 60.0"));
    }
}
