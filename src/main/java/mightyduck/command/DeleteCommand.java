package mightyduck.command;

import java.util.List;

import mightyduck.exception.InvalidValueException;
import mightyduck.task.TaskManager;
import mightyduck.utils.Messages;

/**
 * Represents the "delete" command, which removes multiple tasks from the {@link TaskManager}.
 */
public class DeleteCommand extends Command {

    /**
     * The indices of the tasks to be deleted.
     */
    private final List<Integer> indices;

    /**
     * Constructs a {@code DeleteCommand} with the specified {@link TaskManager} and the indices of
     * the tasks to be deleted.
     *
     * @param taskManager The {@link TaskManager} instance from which the tasks will be deleted.
     * @param indices     The indices of the tasks to be deleted.
     */
    public DeleteCommand(TaskManager taskManager, List<Integer> indices) {
        super(taskManager);
        this.indices = indices;
    }

    /**
     * Executes the "delete" command, removing the specified tasks from the task manager.
     *
     * @return A {@link CommandResult} containing feedback on the operation and the deleted tasks.
     * @throws InvalidValueException If any index is invalid or out of bounds.
     */
    @Override
    public CommandResult execute() throws InvalidValueException {
        return new CommandResult(
                CommandResultType.SUCCESS,
                Messages.DELETE,
                taskManager.deleteTasks(indices)
        );
    }
}
