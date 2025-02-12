package mightyduck.parser;

import java.util.List;

import mightyduck.command.MarkCommand;
import mightyduck.exception.InvalidCommandException;
import mightyduck.exception.InvalidValueException;
import mightyduck.task.TaskManager;
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
    public static final String COMMAND_FORMAT = "mark <index1> <index2> ...";

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
     * @param input The user-provided input string, expected to contain multiple task indices.
     * @return A new {@code MarkCommand} instance.
     * @throws InvalidCommandException If the input is incorrectly formatted.
     * @throws InvalidValueException   If any index is invalid.
     */
    public MarkCommand fromInput(String input)
            throws InvalidCommandException, InvalidValueException {
        String[] parts = input.trim().split("\\s+");
        if (input.trim().isEmpty()) {
            throw new InvalidCommandException(String.format(Messages.WRONG_COMMAND_FORMAT,
                    COMMAND_FORMAT));
        }
        List<Integer> indices = Validation.validateAndParseIndices(List.of(parts));

        return new MarkCommand(taskManager, indices);
    }
}
