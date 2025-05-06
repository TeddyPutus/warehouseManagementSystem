package integration;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static integration.CommonSteps.*;
import static integration.CommonSteps.containsInfo;

public class TestUpdate extends IntegrationTester{
    @Test
    public void testUpdateSupplierInvalidId() throws IOException {
        expect.sendLine("update_supplier_info");
        containsInfo(expect, "Enter Supplier ID:");
        expect.sendLine("someRandomId");
        containsWarning(expect, "Supplier not found.");
    }

    @Test @Ignore
    public void testUpdateSupplierAlreadyExists() throws IOException {
        expect.sendLine("find_suppliers");

        containsInfo(expect, "Enter name:");
        expect.sendLine("Supplier A");
        containsInfo(expect, "Enter phone number:");
        expect.sendLine("");
        containsInfo(expect, "Enter email:");
        expect.sendLine("");
        containsInfo(expect,"| Supplier A      | 123-456-7890 | supplier_a@email.com |");

        String uuid = CommonSteps.getUuid(wholeBuffer.toString());

        containsInfo(expect, "Enter command:");
        expect.sendLine("update_supplier_info");
        containsInfo(expect, "Enter Supplier ID:");
        expect.sendLine(uuid);

        containsInfo(expect, "Enter name:");
        expect.sendLine("Supplier B");
        containsInfo(expect, "Enter phone number:");
        expect.sendLine("555-555-5555");
        containsInfo(expect, "Enter email:");
        expect.sendLine("supplier_c@email.com");

        containsError(expect, "Invalid name update.");
        // Multi line error message.
        containsInfo(expect, "Invalid email update.");
        containsInfo(expect, "Invalid phoneNumber update.");
        containsInfo(expect, "Supplier already exists.");
        containsInfo(expect, "Enter command:");

        expect.sendLine("find_suppliers");

        containsInfo(expect, "Enter name:");
        expect.sendLine("");
        containsInfo(expect, "Enter phone number:");
        expect.sendLine("");
        containsInfo(expect, "Enter email:");
        expect.sendLine("");
        containsInfo(expect,"| Supplier A      | 123-456-7890 | supplier_a@email.com |");
        containsInfo(expect,"| Supplier B      | 987-654-3210 | supplier_b@email.com |");
        containsInfo(expect,"| Supplier C      | 555-555-5555 | supplier_c@email.com |");

    }

    @Test @Ignore
    public void testUpdateSupplier() throws IOException {
        expect.sendLine("find_suppliers");

        containsInfo(expect, "Enter name:");
        expect.sendLine("Supplier A");
        containsInfo(expect, "Enter phone number:");
        expect.sendLine("");
        containsInfo(expect, "Enter email:");
        expect.sendLine("");
        containsInfo(expect,"| Supplier A      | 123-456-7890 | supplier_a@email.com |");

        String uuid = CommonSteps.getUuid(wholeBuffer.toString());

        containsInfo(expect, "Enter command:");
        expect.sendLine("update_supplier_info");
        containsInfo(expect, "Enter Supplier ID:");
        expect.sendLine(uuid);

        containsInfo(expect, "Enter name:");
        expect.sendLine("");
        containsInfo(expect, "Enter phone number:");
        expect.sendLine("999");
        containsInfo(expect, "Enter email:");
        expect.sendLine("supplier_F@email.com");

        containsInfo(expect,"Supplier information updated successfully.");


        containsInfo(expect, "Enter command:");
        expect.sendLine("find_suppliers");

        containsInfo(expect, "Enter name:");
        expect.sendLine("");
        containsInfo(expect, "Enter phone number:");
        expect.sendLine("");
        containsInfo(expect, "Enter email:");
        expect.sendLine("");
        containsInfo(expect,"| Supplier A      |          999 | supplier_F@email.com |");
        containsInfo(expect,"| Supplier B      | 987-654-3210 | supplier_b@email.com |");
        containsInfo(expect,"| Supplier C      | 555-555-5555 | supplier_c@email.com |");

    }
}
