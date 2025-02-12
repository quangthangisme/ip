package mightyduck.command;

import java.util.List;

import mightyduck.task.Task;
import mightyduck.task.TaskManager;
import mightyduck.utils.Messages;
import mightyduck.utils.Pair;

/**
 * Represents the "find" command, which searches for tasks containing a specific keyword.
 */
public class FindCommand extends Command {

    /**
     * The list of search terms to search for.
     */
    private final List<String> searchTerms;

    /**
     * Constructs a {@code FindCommand} with the specified {@link TaskManager} and search
     * keyword(s).
     *
     * @param taskManager The {@link TaskManager} instance to search for tasks.
     * @param searchTerms The list of keywords to search for.
     */
    public FindCommand(TaskManager taskManager, List<String> searchTerms) {
        super(taskManager);
        this.searchTerms = searchTerms;
    }

    /**
     * Executes the "find" command by searching for tasks containing at least one of the provided
     * keywords.
     *
     * @return A {@link CommandResult} containing the search results and a feedback message.
     */
    @Override
    public CommandResult execute() {
        List<Pair<Integer, Task>> result = taskManager.searchKeywords(searchTerms);
        return new CommandResult(
                CommandResultType.SUCCESS,
                result.isEmpty() ? Messages.EMPTY_FIND : Messages.FIND,
                result
        );
    }
}
