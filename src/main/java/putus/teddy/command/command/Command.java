package putus.teddy.command.command;

/**
 * Interface for command pattern. Command can is a discrete set of actions.
 * It is associated with a given user input in the command registry.
 * Exit result is only used for actions we want to terminate the application on completion.
 */
public interface Command {
    enum Result {
        SUCCESS,
        FAILURE,
        EXIT
    }

    Result execute();
}
