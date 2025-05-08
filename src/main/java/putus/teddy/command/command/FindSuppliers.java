package putus.teddy.command.command;

import putus.teddy.data.builder.QueryBuilder;
import putus.teddy.data.entity.DataEntity;
import putus.teddy.data.entity.SupplierEntity;
import putus.teddy.data.parser.ValidatedInputParser;
import putus.teddy.printer.Printer;

public class FindSuppliers implements Command {
     public Result execute() {
         Printer.infoLine("Finding Suppliers, please enter optional filter values...");

         Printer.printTable(
                 supplierRepository.findMany(QueryBuilder.searchSupplier(
                            ValidatedInputParser.parseString("name", false, 1, 15),
                            ValidatedInputParser.parseString("phone number", false, 1, 12),
                            ValidatedInputParser.parseString("email", false, 1, 20)
                 )),
                 SupplierEntity.getTableHead()
         );

         return Result.SUCCESS;
     }
}
