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
        queryMap.put("totalRevenue", 500.0);
        queryMap.put("totalCost", 1000.0);

        assertTrue(financialEntity.matches(queryMap));
    }

    @Test
    public void testPrintTableHead() {
        String expectedOutput = "| ITEM NAME       | PURCHASED            |        SOLD |   OUTGOINGS |     REVENUE |      PROFIT |";
        assertEquals(expectedOutput, FinancialEntity.getTableHead());
    }

    @Test
    public void testPrintTableRow() {
        String expectedOutput = "| Test Entity     | 10                   |           2 |      1000.0 |       500.0 |      -500.0 |\n";
        assertEquals(expectedOutput, financialEntity.getTableRow());
    }
}
