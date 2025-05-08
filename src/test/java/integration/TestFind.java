package integration;

import net.sf.expectit.Expect;
import net.sf.expectit.ExpectBuilder;
import net.sf.expectit.matcher.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static integration.CommonSteps.*;
import static net.sf.expectit.matcher.Matchers.contains;
import static net.sf.expectit.matcher.Matchers.regexp;

public class TestFind extends IntegrationTester{

    @Test
    public void testFindAllInventory() throws IOException {
        containsInfo(expect,"Welcome to BNU Industries!");
        containsInfo(expect,"Type help to see the list of commands.");

        expect.sendLine("find_inventory");
        containsInfo(expect,"Finding Inventory, please enter optional filter values...");

        containsInfo(expect,"Enter name:");
        expect.sendLine("");

        containsInfo(expect, "Enter quantity:");
        expect.sendLine("");

        containsInfo(expect, "Enter price:");
        expect.sendLine("");

        containsInfo(expect,"| ITEM NAME  | QUANTITY | PRICE/UNIT |");
        containsInfo(expect,"| item1      |       10 |      200.0 |");
        containsInfo(expect,"| item2      |       20 |      400.0 |");
        containsInfo(expect,"| item3      |       30 |       60.0 |");
        containsInfo(expect,"| item4      |        4 |      600.0 |");


    }

    @Test
    public void testFindOneInventory() throws IOException {
        containsInfo(expect,"Welcome to BNU Industries!");
        containsInfo(expect,"Type help to see the list of commands.");

        expect.sendLine("find_inventory");
        containsInfo(expect,"Finding Inventory, please enter optional filter values...");

        containsInfo(expect,"Enter name:");
        expect.sendLine("");

        containsInfo(expect, "Enter quantity:");
        expect.sendLine("");

        containsInfo(expect, "Enter price:");
        expect.sendLine("600");

        containsInfo(expect,"| ITEM NAME  | QUANTITY | PRICE/UNIT |");
        notContains(expect,"| item1      |       10 |      200.0 |");
        notContains(expect,"| item2      |       20 |      400.0 |");
        notContains(expect,"| item3      |       30 |       60.0 |");
        containsInfo(expect,"| item4      |        4 |      600.0 |");
    }

    @Test
    public void testFindNoInventory() throws IOException {
        containsInfo(expect,"Welcome to BNU Industries!");
        containsInfo(expect,"Type help to see the list of commands.");

        expect.sendLine("find_inventory");
        containsInfo(expect,"Finding Inventory, please enter optional filter values...");

        containsInfo(expect,"Enter name:");
        expect.sendLine("itemXYZ");

        containsInfo(expect, "Enter quantity:");
        expect.sendLine("");

        containsInfo(expect, "Enter price:");
        expect.sendLine("");

        containsInfo(expect,"| ITEM NAME  | QUANTITY | PRICE/UNIT |");

        containsWarning(expect,"No data found.");

        notContains(expect,"| item1      |       10 |      200.0 |");
        notContains(expect,"| item2      |       20 |      400.0 |");
        notContains(expect,"| item3      |       30 |       60.0 |");
        notContains(expect,"| item4      |        4 |      600.0 |");
    }

    @Test
    public void testFindAllSuppliers() throws IOException{

        expect.sendLine("find_suppliers");
        containsInfo(expect,"Finding Suppliers, please enter optional filter values...");

        containsInfo(expect,"Enter name:");
        expect.sendLine("");

        containsInfo(expect, "Enter phone number:");
        expect.sendLine("");

        containsInfo(expect, "Enter email:");
        expect.sendLine("");

        //NOTE: UUID generated at run time, we can't know what it is in test

        containsInfo(expect,"| ID                                   | NAME            |        PHONE | EMAIL                |");
        containsInfo(expect,"| Supplier A      | 123-456-7890 | supplier_a@email.com |");
        containsInfo(expect,"| Supplier B      | 987-654-3210 | supplier_b@email.com |");
        containsInfo(expect,"| Supplier C      | 555-555-5555 | supplier_c@email.com |");
    }

    @Test
    public void testFindOneSupplier() throws IOException{

        expect.sendLine("find_suppliers");
        containsInfo(expect,"Finding Suppliers, please enter optional filter values...");

        containsInfo(expect,"Enter name:");
        expect.sendLine("Supplier A");

        containsInfo(expect, "Enter phone number:");
        expect.sendLine("");

        containsInfo(expect, "Enter email:");
        expect.sendLine("");


        containsInfo(expect, "| ID                                   | NAME            |        PHONE | EMAIL                |");
        containsInfo(expect, "| Supplier A      | 123-456-7890 | supplier_a@email.com |");
        notContains(expect, "| Supplier B      | 987-654-3210 | supplier_b@email.com |");
        notContains(expect, "| Supplier C      | 555-555-5555 | supplier_c@email.com |");
    }

    @Test
    public void testFindNoSupplier() throws IOException{

        expect.sendLine("find_suppliers");
        containsInfo(expect,"Finding Suppliers, please enter optional filter values...");

        containsInfo(expect,"Enter name:");
        expect.sendLine("Supplier XYZ");

        containsInfo(expect, "Enter phone number:");
        expect.sendLine("");

        containsInfo(expect, "Enter email:");
        expect.sendLine("");


        containsInfo(expect, "| ID                                   | NAME            |        PHONE | EMAIL                |");
        containsWarning(expect, "No data found.");

        notContains(expect, "| Supplier A      | 123-456-7890 | supplier_a@email.com |");
        notContains(expect, "| Supplier B      | 987-654-3210 | supplier_b@email.com |");
        notContains(expect, "| Supplier C      | 555-555-5555 | supplier_c@email.com |");
    }

    @Test @Ignore
    public void testFindStockOrdersWhenNoneExist() throws IOException{
        expect.sendLine("find_stock_orders");
        containsInfo(expect,"Finding Stock Orders, please enter optional filter values...");

        containsInfo(expect,"Enter supplier name:");
        expect.sendLine("");

        containsInfo(expect, "Enter item name:");
        expect.sendLine("");

        containsInfo(expect, "Enter quantity:");
        expect.sendLine("");

        containsInfo(expect, "Enter price:");
        expect.sendLine("");

        containsInfo(expect, "Enter date:");
        expect.sendLine("");

        containsInfo(expect,"| ID                                   | SUPPLIER    | DATE        | ITEM NAME            | QUANTITY | PRICE/UNIT | TOTAL PRICE |");
        containsWarning(expect,"No data found.");
    }

}
