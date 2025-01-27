package mightyduck.command.commands;

import java.util.List;
import java.util.stream.IntStream;

import mightyduck.command.Command;
import mightyduck.command.CommandResult;
import mightyduck.data.task.Task;
import mightyduck.data.task.TaskManager;
import mightyduck.messages.Messages;
import mightyduck.utils.Pair;

/**
 * Represents the "list" command, which retrieves and displays the list of tasks managed by the
 * {@link TaskManager}.
 */
public class ListCommand extends Command {

    /**
     * The command word used to invoke the "list" command.
     */
    public static final String COMMAND_WORD = "list";

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
        List<Task> tasks = this.taskManager.getTasks();
        return new CommandResult((tasks.isEmpty()) ? Messages.EMPTY_LIST : Messages.LIST,
                IntStream.range(0, tasks.size())
                        .mapToObj(i -> new Pair<>(i, tasks.get(i)))
                        .toList());
    }

    @Override
    public boolean isBye() {
        return false;
    }
}
