package mightyduck.parser;

import mightyduck.command.Command;
import mightyduck.exception.InvalidCommandException;
import mightyduck.exception.InvalidValueException;
import mightyduck.task.TaskManager;

/**
 * Abstract base class for builders that create {@link Command} objects from user input.
 */
public abstract class Builder {

    /**
     * The {@link TaskManager} instance that will be used to manage tasks.
     */
    protected final TaskManager taskManager;

    /**
     * Constructs a new {@link Builder} with the specified {@link TaskManager}.
     *
     * @param taskManager The {@link TaskManager} instance that will be used to manage tasks.
     */
    public Builder(TaskManager taskManager) {
        assert taskManager != null : "The TaskManager should not be null";
        this.taskManager = taskManager;
    }

    /**
     * Parses the user input and returns a {@link Command} object based on the input.
     *
     * @param input The user input string that will be parsed.
     * @return The corresponding {@link Command} object created from the input.
     * @throws InvalidValueException   If there is an invalid value in the input.
     * @throws InvalidCommandException If the input command format is invalid.
     */
    public abstract Command fromInput(String input)
            throws InvalidValueException, InvalidCommandException;
}
