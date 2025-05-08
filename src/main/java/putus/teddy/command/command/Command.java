package putus.teddy.command.command;

public interface Command {
    enum Result{
        SUCCESS,
        FAILURE,
        EXIT
    }

    Result execute();
}
