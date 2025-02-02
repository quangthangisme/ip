package mightyduck.command.commands;

import java.util.List;

import mightyduck.command.Command;
import mightyduck.command.CommandResult;
import mightyduck.command.CommandResultType;
import mightyduck.data.task.Task;
import mightyduck.data.task.TaskManager;
import mightyduck.exception.InvalidValueException;
import mightyduck.messages.Messages;
import mightyduck.utils.Pair;

/**
 * Represents the "find" command, which searches for tasks containing a specific keyword.
 */
public class FindCommand extends Command {

    /**
     * The command word used to invoke the "find" command.
     */
    public static final String COMMAND_WORD = "find";

    /**
     * The format of the "find" command.
     */
    public static final String COMMAND_FORMAT = "find <keyword>";

    /**
     * The list of keywords required for parsing the "find" command.
     */
    public static final List<String> KEYWORDS = List.of();

    /**
     * The arguments provided for the "find" command, including the keyword to search for.
     */
    private final String[] arguments;

    /**
     * Constructs a {@code FindCommand} with the specified {@link TaskManager} and search keyword.
     *
     * @param taskManager The {@link TaskManager} instance to search for tasks.
     * @param arguments   The arguments used for the search, expected to include a single keyword.
     */
    public FindCommand(TaskManager taskManager, String[] arguments) {
        super(taskManager);
        this.arguments = arguments;
    }

    /**
     * Executes the "find" command by searching for tasks containing the provided keyword.
     * <p>
     * If the keyword is found in the task name, it will return a list of those tasks. If no tasks
     * match, an appropriate message will be returned.
     *
     * @return A {@link CommandResult} containing a message and the search results, which are a list
     *         of tasks containing the keyword, if found.
     * @throws InvalidValueException If the provided arguments are invalid or the keyword is
     *                               missing.
     */
    @Override
    public CommandResult execute() throws InvalidValueException {
        if (arguments.length != 1) {
            throw new InvalidValueException(String.format(
                    Messages.WRONG_COMMAND_FORMAT, COMMAND_FORMAT));
        }
        List<Pair<Integer, Task>> res = taskManager.searchKeyword(arguments[0]);
        return new CommandResult(
                CommandResultType.SUCCESS,
                res.isEmpty() ? Messages.EMPTY_FIND : Messages.FIND,
                res
        );
    }
}
