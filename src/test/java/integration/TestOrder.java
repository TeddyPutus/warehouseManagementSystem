package integration;


import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static integration.CommonSteps.*;
import static integration.CommonSteps.containsInfo;

public class TestOrder extends IntegrationTester{
    @Test @Ignore
    public void testOrderStockAndTakeDelivery() throws IOException {
        expect.sendLine("order_stock");

        containsInfo(expect, "Enter supplier name:");
        expect.sendLine("Supplier A");
        containsInfo(expect, "Enter item name:");
        expect.sendLine("item1");
        containsInfo(expect, "Enter quantity:");
        expect.sendLine("1");
        containsInfo(expect, "Enter price:");
        expect.sendLine("2");
        containsSuccess(expect, "Order placed successfully. Order ID is");

        String orderId = getUuid(wholeBuffer.toString());

        containsInfo(expect, "Enter command:");
        expect.sendLine("find_stock_orders");

        containsInfo(expect, "Enter supplier name:");
        expect.sendLine("");
        containsInfo(expect, "Enter item name:");
        expect.sendLine("");
        containsInfo(expect, "Enter quantity:");
        expect.sendLine("");
        containsInfo(expect, "Enter price:");
        expect.sendLine("");
        containsInfo(expect, "Enter date:");
        expect.sendLine("");

        containsInfo(expect, "| " + orderId + " | Supplier A  | 2025-05-07  | item1                |        1 |        2.0 |         2.0 |");

        containsInfo(expect, "Enter command:");
        expect.sendLine("financial_report");

        containsInfo(expect, "| item1           | 10                   |           0 |       100.0 |         0.0 |      -100.0 |");

        containsInfo(expect, "Enter command:");
        expect.sendLine("take_delivery");

        containsInfo(expect, "Enter Order ID:");
        expect.sendLine(orderId);
        containsSuccess(expect, "Delivery taken successfully.");

        containsInfo(expect, "Enter command:");
        expect.sendLine("financial_report");

        containsInfo(expect, "| item1           | 11                   |           0 |       102.0 |         0.0 |      -102.0 |");

    }
}
