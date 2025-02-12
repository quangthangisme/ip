package mightyduck.parser;

import java.util.List;

import mightyduck.command.DeleteCommand;
import mightyduck.exception.InvalidCommandException;
import mightyduck.exception.InvalidValueException;
import mightyduck.task.TaskManager;
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
    public static final String COMMAND_FORMAT = "delete <index1> <index2> ...";

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
     * @param input The user-provided input string, expected to contain multiple task indices.
     * @return A new {@code DeleteCommand} instance.
     * @throws InvalidCommandException If the input is incorrectly formatted.
     * @throws InvalidValueException   If any index is invalid.
     */
    public DeleteCommand fromInput(String input)
            throws InvalidCommandException, InvalidValueException {
        String[] parts = input.trim().split("\\s+");
        if (input.trim().isEmpty()) {
            throw new InvalidCommandException(String.format(Messages.WRONG_COMMAND_FORMAT,
                    COMMAND_FORMAT));
        }
        List<Integer> indices = Validation.validateAndParseIndices(List.of(parts));

        return new DeleteCommand(taskManager, indices);
    }
}
