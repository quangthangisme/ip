package mightyduck.command.builder;

import mightyduck.command.command.DeleteCommand;
import mightyduck.data.task.TaskManager;
import mightyduck.exception.InvalidCommandException;
import mightyduck.exception.InvalidValueException;
import mightyduck.utils.Messages;

/**
 * A builder class for constructing a {@link DeleteCommand} from user input.
 */
public class DeleteBuilder extends Builder {

    /**
     * The command word used to invoke the "delete" command.
     */
    public static final String COMMAND_WORD = "delete";

    /**
     * The format of the "delete" command.
     */
    public static final String COMMAND_FORMAT = "delete <index>";

    /**
     * Constructs a new {@link DeleteBuilder} with the specified {@link TaskManager}.
     *
     * @param taskManager The {@link TaskManager} instance that manages tasks.
     */
    public DeleteBuilder(TaskManager taskManager) {
        super(taskManager);
    }

    /**
     * Creates a {@code DeleteCommand} from user-provided input.
     *
     * @param input       The user-provided input string, expected to contain the task's index.
     * @return A new {@code DeleteCommand} instance.
     * @throws InvalidCommandException If the input is incorrectly formatted.
     * @throws InvalidValueException If the index is invalid.
     */
    public DeleteCommand fromInput(String input)
            throws InvalidCommandException, InvalidValueException {
        String[] parts = input.trim().split("\\s+");
        if (input.trim().isEmpty() || parts.length != 1) {
            throw new InvalidCommandException(String.format(Messages.WRONG_COMMAND_FORMAT,
                    COMMAND_FORMAT));
        }

        try {
            int index = Integer.parseInt(parts[0]) - 1;
            return new DeleteCommand(taskManager, index);
        } catch (NumberFormatException e) {
            throw new InvalidValueException(Messages.WRONG_NUMBER_FORMAT);
        }
    }
}
