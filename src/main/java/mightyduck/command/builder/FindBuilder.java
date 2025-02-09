package mightyduck.command.builder;

import java.util.Arrays;
import java.util.List;

import mightyduck.command.command.FindCommand;
import mightyduck.data.task.TaskManager;
import mightyduck.exception.InvalidCommandException;
import mightyduck.utils.Messages;

/**
 * A builder class for constructing a {@link FindCommand} from user input.
 */
public class FindBuilder extends Builder {

    /**
     * The command word used to invoke the "find" command.
     */
    public static final String COMMAND_WORD = "find";

    /**
     * The format of the "find" command.
     */
    public static final String COMMAND_FORMAT = "find <keyword1> <keyword2> ...";

    /**
     * Constructs a new {@link FindBuilder} with the specified {@link TaskManager}.
     *
     * @param taskManager The {@link TaskManager} instance that manages tasks.
     */
    public FindBuilder(TaskManager taskManager) {
        super(taskManager);
    }

    /**
     * Creates a {@code FindCommand} from user-provided input.
     *
     * @param input The user-provided input string containing the search terms.
     * @return A new {@code FindCommand} instance.
     * @throws InvalidCommandException If the input is missing or improperly formatted.
     */
    public FindCommand fromInput(String input) throws InvalidCommandException {
        String[] parts = input.trim().split("\\s+");
        if (input.trim().isEmpty()) {
            throw new InvalidCommandException(String.format(Messages.WRONG_COMMAND_FORMAT,
                    COMMAND_FORMAT));
        }

        List<String> searchTerms = Arrays.stream(parts).toList();

        return new FindCommand(taskManager, searchTerms);
    }
}
