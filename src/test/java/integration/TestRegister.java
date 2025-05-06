package integration;

import org.junit.Test;

import java.io.IOException;

import static integration.CommonSteps.*;

public class TestRegister extends IntegrationTester{
    @Test
    public void testRegisterSupplier() throws IOException {
        expect.sendLine("register_supplier");
        containsInfo(expect, "Registering supplier...");

        containsInfo(expect, "Enter name:");
        expect.sendLine("");
        containsWarning(expect, "name is required. Please enter a value.");
        containsInfo(expect, "Enter name:");

        expect.sendLine("this supplier name is way too long");
        containsWarning(expect, "Input length must be between 1 and 15 characters. Please enter a valid value.");
        containsInfo(expect, "Enter name:");

        expect.sendLine("Supplier XYZ");

        containsInfo(expect, "Enter phone number:");
        expect.sendLine("");
        containsWarning(expect, "phone number is required. Please enter a value.");


        containsInfo(expect, "Enter phone number:");
        expect.sendLine("1234567891011121314151617181920");
        containsWarning(expect, "Input length must be between 1 and 12 characters. Please enter a valid value.");


        containsInfo(expect, "Enter phone number:");
        expect.sendLine("1234");

        containsInfo(expect, "Enter email:");
        expect.sendLine("");
        containsWarning(expect, "email is required. Please enter a value.");


        expect.sendLine("email@email.com");
        containsSuccess(expect, "Supplier registered successfully.");

        containsInfo(expect, "Enter command:");
        expect.sendLine("find_suppliers");
        containsInfo(expect, "Enter name:");
        expect.sendLine("Supplier XYZ");
        containsInfo(expect, "Enter phone number:");
        expect.sendLine("");
        containsInfo(expect, "Enter email:");
        expect.sendLine("");

        containsInfo(expect,"| Supplier XYZ    |         1234 | email@email.com      |");
    }

    @Test
    public void testRegisterSupplierAlreadyExists() throws IOException {
        expect.sendLine("register_supplier");
        containsInfo(expect, "Registering supplier...");

        containsInfo(expect, "Enter name:");

        expect.sendLine("Supplier A");

        containsInfo(expect, "Enter phone number:");
        expect.sendLine("1234");

        containsInfo(expect, "Enter email:");

        expect.sendLine("email@email.com");
        containsError(expect, "Supplier already exists.");

        containsInfo(expect, "Enter command:");
        expect.sendLine("find_suppliers");

        containsInfo(expect, "Enter name:");
        expect.sendLine("Supplier A");
        expect.sendLine("");
        expect.sendLine("");

        containsInfo(expect,"| Supplier A      | 123-456-7890 | supplier_a@email.com |");
        notContains(expect,"| Supplier A      |         1234 | email@email.com      |");
    }
}
