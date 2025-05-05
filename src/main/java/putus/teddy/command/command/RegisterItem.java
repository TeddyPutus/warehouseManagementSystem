package putus.teddy.command.command;

import putus.teddy.data.builder.EntityBuilder;
import putus.teddy.data.entity.FinancialEntity;
import putus.teddy.data.entity.InventoryEntity;
import putus.teddy.printer.Printer;

import java.util.Map;

public class RegisterItem implements Command {
    public boolean execute() {
        Printer.info("Registering item...");
        InventoryEntity newItem = EntityBuilder.buildInventoryEntity();

        if(inventoryRepository.findOne(Map.of("itemName", newItem.getItemName())) != null) {
            Printer.error("Item already exists.");
            return false;
        }

        FinancialEntity financialEntity = new FinancialEntity(newItem.getItemName(), newItem.getQuantity(), 0, 0.0,0.0);

        inventoryRepository.create(newItem);
        financialRepository.create(financialEntity);

        Printer.success("Item registered successfully.");

        return false;
    }
}
