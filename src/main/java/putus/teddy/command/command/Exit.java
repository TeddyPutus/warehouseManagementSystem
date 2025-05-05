package putus.teddy.command.command;

public class Exit implements Command {

    public boolean execute() {
        System.out.println("Exiting the application...");
        return true;
    }
}
