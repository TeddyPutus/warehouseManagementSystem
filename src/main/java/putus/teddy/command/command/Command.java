package putus.teddy.command.command;

/**
 * Interface for command pattern. Command can is a discrete set of actions.
 * It is associated with a given user input in the command registry.
 * Exit result is only used for actions we want to terminate the application on completion.
 */
public interface Command {
    /**
     * Enum representing the result of a command execution.
     * EXIT result is only used if you want the command to close the application on completion.
     */
    enum Result {
        SUCCESS,
        FAILURE,
        EXIT
    }

    Result execute();
}
