package putus.teddy.command.command;

import putus.teddy.data.entity.FinancialEntity;
import putus.teddy.data.entity.InventoryEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.printer.Printer;

import java.util.Map;

public class RegisterItem implements Command {
    public boolean execute() {
        Printer.info("Registering item...");

        InventoryEntity newItem = createInventoryEntity();

        if (inventoryRepository.findOne(Map.of("itemName", newItem.getItemName())) != null) {
            Printer.error("Item already exists.");
            return false;
        }

        FinancialEntity financialEntity = new FinancialEntity(newItem.getItemName(), newItem.getQuantity(), 0, 0.0, 0.0);

        inventoryRepository.create(newItem);
        financialRepository.create(financialEntity);

        Printer.success("Item registered successfully.");

        return false;
    }

    private InventoryEntity createInventoryEntity() {
        return new InventoryEntity(
                ValidatedInputParser.parseString("name", true, 1, 15),
                0,
                ValidatedInputParser.parseAmount("price", true)
        );
    }
}
