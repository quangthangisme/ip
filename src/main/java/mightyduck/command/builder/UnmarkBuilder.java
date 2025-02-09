package mightyduck.command.builder;

import mightyduck.command.command.UnmarkCommand;
import mightyduck.data.task.TaskManager;
import mightyduck.exception.InvalidCommandException;
import mightyduck.exception.InvalidValueException;
import mightyduck.utils.Messages;

/**
 * A builder class for constructing a {@link UnmarkCommand} from user input.
 */
public class UnmarkBuilder extends Builder {

    /**
     * The command word used to invoke the "unmark" command.
     */
    public static final String COMMAND_WORD = "unmark";

    /**
     * The format of the "unmark" command.
     */
    public static final String COMMAND_FORMAT = "unmark <index>";

    /**
     * Constructs a new {@link UnmarkBuilder} with the specified {@link TaskManager}.
     *
     * @param taskManager The {@link TaskManager} instance that manages tasks.
     */
    public UnmarkBuilder(TaskManager taskManager) {
        super(taskManager);
    }

    /**
     * Creates a {@code UnmarkCommand} from user-provided input.
     *
     * @param input       The user-provided input string, expected to contain the task's index.
     * @return A new {@code UnmarkCommand} instance.
     * @throws InvalidCommandException If the input is incorrectly formatted.
     * @throws InvalidValueException If the index is invalid.
     */
    public UnmarkCommand fromInput(String input)
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

        return new UnmarkCommand(taskManager, index);
    }
}
