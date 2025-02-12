package mightyduck.command;

import java.util.List;
import java.util.stream.IntStream;

import mightyduck.task.Task;
import mightyduck.task.TaskManager;
import mightyduck.utils.Messages;
import mightyduck.utils.Pair;

/**
 * Represents the "list" command, which retrieves and displays the list of tasks managed by the
 * {@link TaskManager}.
 */
public class ListCommand extends Command {

    /**
     * Constructs a {@code ListCommand} with the specified {@link TaskManager}.
     *
     * @param taskManager The {@link TaskManager} instance from which tasks will be retrieved.
     */
    public ListCommand(TaskManager taskManager) {
        super(taskManager);
    }

    /**
     * Executes the "list" command, retrieving all tasks from the {@link TaskManager} and returning
     * them in a {@link CommandResult}.
     *
     * @return A {@link CommandResult} containing the tasks to be display and a feedback message.
     */
    @Override
    public CommandResult execute() {
        List<Task> tasks = taskManager.getTasks();
        return new CommandResult(
                CommandResultType.SUCCESS,
                tasks.isEmpty() ? Messages.EMPTY_LIST : Messages.LIST,
                IntStream.range(0, tasks.size())
                        .mapToObj(i -> new Pair<>(i, tasks.get(i)))
                        .toList()
        );
    }
}
