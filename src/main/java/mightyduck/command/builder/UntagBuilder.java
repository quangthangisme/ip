package mightyduck.command.builder;

import java.util.Arrays;
import java.util.List;

import mightyduck.command.command.UntagCommand;
import mightyduck.data.task.TaskManager;
import mightyduck.exception.InvalidCommandException;
import mightyduck.exception.InvalidValueException;
import mightyduck.utils.Messages;

/**
 * A builder class for constructing a {@link UntagBuilder} from user input.
 */
public class UntagBuilder extends Builder {

    /**
     * The command word used to invoke the "untag" command.
     */
    public static final String COMMAND_WORD = "untag";

    /**
     * The format of the "untag" command.
     */
    public static final String COMMAND_FORMAT = "untag <index> <tag1> <tag2> ...";

    /**
     * Constructs a new {@link UntagBuilder} with the specified {@link TaskManager}.
     *
     * @param taskManager The {@link TaskManager} instance that manages tasks.
     */
    public UntagBuilder(TaskManager taskManager) {
        super(taskManager);
    }

    /**
     * Creates a {@code UntagBuilder} from user-provided input.
     *
     * @param input The user-provided input string, expected to contain the task's index and tags.
     * @return A new {@code UntagBuilder} instance.
     * @throws InvalidCommandException If the input is incorrectly formatted.
     * @throws InvalidValueException   If the index is invalid.
     */
    public UntagCommand fromInput(String input) throws InvalidCommandException,
            InvalidValueException {
        String[] parts = input.trim().split("\\s+");
        if (parts.length < 2) {
            throw new InvalidCommandException(String.format(Messages.WRONG_COMMAND_FORMAT,
                    COMMAND_FORMAT));
        }

        int index;
        try {
            index = Integer.parseInt(parts[0]) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidValueException(Messages.WRONG_NUMBER_FORMAT);
        }

        List<String> tags = Arrays.asList(Arrays.copyOfRange(parts, 1, parts.length));
        return new UntagCommand(taskManager, index, tags);
    }
}
