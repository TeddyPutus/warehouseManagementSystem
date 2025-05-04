package data.entity;

import org.junit.Before;
import org.junit.Test;
import putus.teddy.data.entity.FinancialEntity;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FinancialEntityTest {
    private FinancialEntity financialEntity;

    @Before
    public void setUp() {
        financialEntity = new FinancialEntity("Test Entity", 10,2, 1000.0, 500.0);
    }

    @Test
    public void testIncrementId() {
        int initialId = FinancialEntity.getIdCounter();
        FinancialEntity newEntity = new FinancialEntity("Test Entity", 10,2, 1000.0, 500.0);
        assertEquals(initialId + 1, FinancialEntity.getIdCounter());
        FinancialEntity anotherEntity = new FinancialEntity("Test Entity", 10,2, 1000.0, 500.0);
        assertEquals(initialId, newEntity.getId());
        assertEquals(initialId + 1, anotherEntity.getId());
        assertEquals(initialId + 2, FinancialEntity.getIdCounter());
    }

    @Test
    public void testConstructor() {
        assertEquals(1000.0, financialEntity.getTotalCost(), 0.01);
        assertEquals(500.0, financialEntity.getTotalRevenue(), 0.01);
    }

    @Test
    public void testUpdate() {
        HashMap<String, Object> updates = new HashMap<>();
        updates.put("totalRevenue", 2000.0);
        updates.put("totalCost", 1000.0);

        financialEntity.update(updates);


        assertEquals(2000.0, financialEntity.getTotalRevenue(), 0.01);
        assertEquals(1000.0, financialEntity.getTotalCost(), 0.01);
    }

    @Test
    public void testMatches() {
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("id", financialEntity.getId());
        queryMap.put("totalRevenue", 500.0);
        queryMap.put("totalCost", 1000.0);

        assertTrue(financialEntity.matches(queryMap));
    }

    @Test
    public void testPrintTableHead() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        FinancialEntity.printTableHead();

        String expectedOutput = "--------------------------------------------------------------------------------------------------\n" +
                "| ITEM NAME       | PURCHASED            |        SOLD |   OUTGOINGS |     REVENUE |      PROFIT |\n" +
                "--------------------------------------------------------------------------------------------------\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testPrintTableRow() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        financialEntity.printTableRow();

        String expectedOutput = "| Test Entity     | 10                   |           2 |      1000.0 |       500.0 |      -500.0 |\n";
        assertEquals(expectedOutput, outContent.toString());
    }
}
