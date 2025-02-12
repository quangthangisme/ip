package mightyduck.command.builder;

import java.util.Arrays;
import java.util.List;

import mightyduck.command.command.TagCommand;
import mightyduck.data.task.TaskManager;
import mightyduck.exception.InvalidCommandException;
import mightyduck.exception.InvalidValueException;
import mightyduck.utils.Messages;

/**
 * A builder class for constructing a {@link TagCommand} from user input.
 */
public class TagBuilder extends Builder {

    /**
     * The command word used to invoke the "tag" command.
     */
    public static final String COMMAND_WORD = "tag";

    /**
     * The format of the "tag" command.
     */
    public static final String COMMAND_FORMAT = "tag <index> <tag1> <tag2> ...";

    /**
     * Constructs a new {@link TagBuilder} with the specified {@link TaskManager}.
     *
     * @param taskManager The {@link TaskManager} instance that manages tasks.
     */
    public TagBuilder(TaskManager taskManager) {
        super(taskManager);
    }

    /**
     * Creates a {@code TagCommand} from user-provided input.
     *
     * @param input The user-provided input string, expected to contain the task's index and tags.
     * @return A new {@code TagCommand} instance.
     * @throws InvalidCommandException If the input is incorrectly formatted.
     * @throws InvalidValueException   If the index is invalid.
     */
    public TagCommand fromInput(String input) throws InvalidCommandException,
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
        return new TagCommand(taskManager, index, tags);
    }
}
