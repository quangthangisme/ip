package mightyduck.command.builder;

import mightyduck.command.command.MarkCommand;
import mightyduck.data.task.TaskManager;
import mightyduck.exception.InvalidCommandException;
import mightyduck.exception.InvalidValueException;
import mightyduck.utils.Messages;

/**
 * A builder class for constructing a {@link MarkCommand} from user input.
 */
public class MarkBuilder extends Builder {

    /**
     * The command word used to invoke the "mark" command.
     */
    public static final String COMMAND_WORD = "mark";

    /**
     * The format of the "mark" command.
     */
    public static final String COMMAND_FORMAT = "mark <index>";

    /**
     * Constructs a new {@link MarkBuilder} with the specified {@link TaskManager}.
     *
     * @param taskManager The {@link TaskManager} instance that manages tasks.
     */
    public MarkBuilder(TaskManager taskManager) {
        super(taskManager);
    }

    /**
     * Creates a {@code MarkCommand} from user-provided input.
     *
     * @param input       The user-provided input string, expected to contain the task's index.
     * @return A new {@code MarkCommand} instance.
     * @throws InvalidCommandException If the input is incorrectly formatted.
     * @throws InvalidValueException If the index is invalid.
     */
    public MarkCommand fromInput(String input)
            throws InvalidCommandException, InvalidValueException {
        String[] parts = input.trim().split("\\s+");
        if (input.trim().isEmpty() || parts.length != 1) {
            throw new InvalidCommandException(String.format(Messages.WRONG_COMMAND_FORMAT,
                    COMMAND_FORMAT));
        }

        int index;
        try {
            index = Integer.parseInt(parts[0]) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidValueException(Messages.WRONG_NUMBER_FORMAT);
        }

        return new MarkCommand(taskManager, index);
    }
}
