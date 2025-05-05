package putus.teddy.command.command;

import putus.teddy.data.entity.FinancialEntity;
import putus.teddy.data.repository.InMemoryRepository;

import java.util.HashMap;
import java.util.stream.Stream;

public class GenerateReport implements Command {

    public boolean execute() {
        System.out.println("Generating financial report...");

        double[] totals = {0.0, 0.0};

        FinancialEntity.printTableHead();
        Stream<FinancialEntity> financialEntities = financialRepository.findAll();

        financialEntities.forEach( entity -> {
            totals[0] += entity.getTotalRevenue();
            totals[1] += entity.getTotalCost();
            entity.printTableRow();
        }
        );

        System.out.println("Total Sales: " + totals[0]);
        System.out.println("Total Expenses: " + totals[1]);
        System.out.println("Net Profit: " + (totals[0] - totals[1]));

        return false;
    }
}
