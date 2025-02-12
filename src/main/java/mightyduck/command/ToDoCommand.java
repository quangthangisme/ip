package mightyduck.command;

import java.util.List;

import mightyduck.task.Task;
import mightyduck.task.TaskManager;
import mightyduck.task.ToDo;
import mightyduck.utils.Messages;
import mightyduck.utils.Pair;

/**
 * Represents the "todo" command, which adds a new {@link ToDo} task to the task manager.
 */
public class ToDoCommand extends Command {

    /**
     * The name provided for creating the {@link ToDo} task.
     */
    private final String taskName;

    /**
     * The list of tags associated with the task provided.
     */
    private final List<String> tags;

    /**
     * Constructs a {@code ToDoCommand} with the specified {@link TaskManager} and the name for the
     * to-do.
     *
     * @param taskManager The {@link TaskManager} instance to add the to-do task to.
     * @param taskName    The name for the to-do task.
     * @param tags        The list of tags for the to-do task.
     */
    public ToDoCommand(TaskManager taskManager, String taskName, List<String> tags) {
        super(taskManager);
        this.taskName = taskName;
        this.tags = tags;
    }

    /**
     * Executes the "todo" command, creating a new {@link ToDo} task and adding it to the task
     * manager.
     *
     * @return A {@link CommandResult} containing feedback on the operation and the newly added
     *         to-do task.
     */
    @Override
    public CommandResult execute() {
        Task todo = new ToDo(taskName, tags);
        int index = taskManager.addTask(todo);
        return new CommandResult(
                CommandResultType.SUCCESS,
                Messages.ADD_TASK,
                List.of(new Pair<>(index, todo))
        );
    }
}
