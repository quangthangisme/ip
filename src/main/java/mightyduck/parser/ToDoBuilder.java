package mightyduck.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mightyduck.command.ToDoCommand;
import mightyduck.exception.InvalidCommandException;
import mightyduck.exception.InvalidValueException;
import mightyduck.task.TaskManager;
import mightyduck.utils.Messages;

/**
 * A builder class for constructing a {@link ToDoCommand} from user input.
 */
public class ToDoBuilder extends Builder {

    /**
     * The command word used to invoke the "todo" command.
     */
    public static final String COMMAND_WORD = "todo";

    /**
     * The format of the "todo" command.
     */
    public static final String COMMAND_FORMAT = "todo <name> [/tags <tag1> <tag2> ...]";

    /**
     * The keyword indicating the tags.
     */
    private static final String TAGS_KEYWORD = "/tags";

    /**
     * Constructs a new {@link ToDoBuilder} with the specified {@link TaskManager}.
     *
     * @param taskManager The {@link TaskManager} instance that manages tasks.
     */
    public ToDoBuilder(TaskManager taskManager) {
        super(taskManager);
    }

    /**
     * Creates a {@code ToDoCommand} from user-provided input.
     *
     * @param input The user-provided input string, expected to contain the task's name.
     * @return A new {@code ToDoCommand} instance.
     * @throws InvalidCommandException If the input is incorrectly formatted.
     */
    public ToDoCommand fromInput(String input) throws InvalidCommandException,
            InvalidValueException {
        if (input.isEmpty()) {
            throw new InvalidCommandException(String.format(Messages.WRONG_COMMAND_FORMAT,
                    COMMAND_FORMAT));
        }

        String taskName;
        List<String> tags = new ArrayList<>();

        String[] parts = input.split(TAGS_KEYWORD, 2);
        taskName = parts[0].trim();
        if (taskName.isEmpty()) {
            throw new InvalidCommandException(String.format(Messages.WRONG_COMMAND_FORMAT,
                    COMMAND_FORMAT));
        }
        if (parts.length > 1) {
            tags = Arrays.asList(parts[1].trim().split("\\s+"));
        }
        Validation.validateTags(tags);

        return new ToDoCommand(taskManager, taskName, tags);
    }
}
