package command.command;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import putus.teddy.command.command.Command;
import putus.teddy.command.command.CustomerOrder;
import putus.teddy.data.builder.EntityBuilder;
import putus.teddy.data.entity.CustomerPurchaseEntity;
import putus.teddy.data.entity.FinancialEntity;
import putus.teddy.data.entity.InventoryEntity;
import putus.teddy.data.repository.InMemoryRepository;

import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestCustomerOrder {

    @Test
    public void testCustomerOrder() {
        // Mock the static method
        try (MockedStatic<EntityBuilder> mockedBuilder = org.mockito.Mockito.mockStatic(EntityBuilder.class)) {



            // Call the static method
            boolean result = new CustomerOrder().execute();

            // Verify the result
            assertTrue(result);
        }
    }
}
