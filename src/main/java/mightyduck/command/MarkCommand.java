package mightyduck.command;

import java.util.List;

import mightyduck.exception.InvalidValueException;
import mightyduck.task.TaskManager;
import mightyduck.utils.Messages;

/**
 * Represents the "mark" command, which marks multiple tasks from the {@link TaskManager}.
 */
public class MarkCommand extends Command {

    /**
     * The indices of the tasks to be marked.
     */
    private final List<Integer> indices;

    /**
     * Constructs a {@code MarkCommand} with the specified {@link TaskManager} and the indices of
     * the tasks to be marked.
     *
     * @param taskManager The {@link TaskManager} instance from which the tasks will be marked.
     * @param indices     The indices of the tasks to be marked.
     */
    public MarkCommand(TaskManager taskManager, List<Integer> indices) {
        super(taskManager);
        this.indices = indices;
    }

    /**
     * Executes the "mark" command, marking the specified tasks from the task manager.
     *
     * @return A {@link CommandResult} containing feedback on the operation and the marked tasks.
     * @throws InvalidValueException If any index is invalid or out of bounds.
     */
    @Override
    public CommandResult execute() throws InvalidValueException {
        return new CommandResult(
                CommandResultType.SUCCESS,
                Messages.MARK,
                taskManager.markTasks(indices)
        );
    }
}
