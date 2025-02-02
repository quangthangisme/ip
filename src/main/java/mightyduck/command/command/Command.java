package mightyduck.command.command;

import mightyduck.command.CommandResult;
import mightyduck.data.task.TaskManager;
import mightyduck.exception.InvalidValueException;

/**
 * Represents an abstract command that interacts with the {@link TaskManager}.
 */
public abstract class Command {

    /**
     * The {@link TaskManager} that this command will interact with.
     */
    protected TaskManager taskManager;

    /**
     * Constructs a {@code Command} with a specified {@link TaskManager}.
     *
     * @param taskManager The {@link TaskManager} instance to be used by this command.
     */
    public Command(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    /**
     * Executes the command, performing the necessary action.
     *
     * @return A {@link CommandResult} containing the result of the execution.
     * @throws InvalidValueException If there is an invalid value encountered during execution.
     */
    public abstract CommandResult execute() throws InvalidValueException;
}
