package mightyduck.command.command;

import java.util.List;

import mightyduck.command.CommandResult;
import mightyduck.command.CommandResultType;
import mightyduck.data.task.Task;
import mightyduck.data.task.TaskManager;
import mightyduck.exception.InvalidValueException;
import mightyduck.utils.Messages;
import mightyduck.utils.Pair;

/**
 * Represents the "delete" command, which removes a task from the {@link TaskManager}.
 */
public class DeleteCommand extends Command {

    /**
     * The index of the task to be deleted.
     */
    private final int index;

    /**
     * Constructs a {@code DeleteCommand} with the specified {@link TaskManager} and the index of
     * the task to be deleted.
     *
     * @param taskManager The {@link TaskManager} instance from which the task will be deleted.
     * @param index       The index of the task to be deleted (0-based index).
     */
    public DeleteCommand(TaskManager taskManager, int index) {
        super(taskManager);
        this.index = index;
    }

    /**
     * Executes the "delete" command, removing the specified task from the task manager.
     *
     * @return A {@link CommandResult} containing feedback on the operation and the deleted task.
     * @throws InvalidValueException If the index is invalid or out of bounds.
     */
    @Override
    public CommandResult execute() throws InvalidValueException {
        Task task = taskManager.deleteTask(index);
        return new CommandResult(
                CommandResultType.SUCCESS,
                Messages.DELETE,
                List.of(new Pair<>(index, task))
        );
    }
}
