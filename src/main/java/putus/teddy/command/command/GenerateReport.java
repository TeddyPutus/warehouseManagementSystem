package putus.teddy.command.command;

import putus.teddy.data.entity.FinancialEntity;
import putus.teddy.printer.Printer;

public class GenerateReport implements Command {

    public boolean execute() {
        Printer.info("Generating financial report...");

        double[] totals = {0.0, 0.0};

        Printer.printTable(
                financialRepository.findAll().map(entity -> entity),
                FinancialEntity.getTableHead()
        );

        financialRepository.findAll().forEach( entity -> {
            totals[0] += entity.getTotalRevenue();
            totals[1] += entity.getTotalCost();
        }
        );

        Printer.info("Total Sales: " + totals[0]);
        Printer.info("Total Expenses: " + totals[1]);
        Printer.info("Net Profit: " + (totals[0] - totals[1]));

        return false;
    }
}
