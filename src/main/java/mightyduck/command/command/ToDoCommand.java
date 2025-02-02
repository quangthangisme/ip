package mightyduck.command.command;

import java.util.List;

import mightyduck.command.CommandResult;
import mightyduck.command.CommandResultType;
import mightyduck.data.task.Task;
import mightyduck.data.task.TaskManager;
import mightyduck.data.task.type.ToDo;
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
     * Constructs a {@code ToDoCommand} with the specified {@link TaskManager} and the name for the
     * to-do.
     *
     * @param taskManager The {@link TaskManager} instance to add the to-do task to.
     * @param taskName    The name for the to-do task.
     */
    public ToDoCommand(TaskManager taskManager, String taskName) {
        super(taskManager);
        this.taskName = taskName;
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
        Task todo = new ToDo(taskName);
        int index = taskManager.addTask(todo);
        return new CommandResult(
                CommandResultType.SUCCESS,
                Messages.ADD_TASK,
                List.of(new Pair<>(index, todo))
        );
    }
}
