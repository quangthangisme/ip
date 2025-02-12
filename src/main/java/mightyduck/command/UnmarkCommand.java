package mightyduck.command;

import java.util.List;

import mightyduck.exception.InvalidValueException;
import mightyduck.task.TaskManager;
import mightyduck.utils.Messages;

/**
 * Represents the "unmark" command, which unmarks multiple tasks from the {@link TaskManager}.
 */
public class UnmarkCommand extends Command {

    /**
     * The indices of the tasks to be unmarked.
     */
    private final List<Integer> indices;

    /**
     * Constructs a {@code UnmarkCommand} with the specified {@link TaskManager} and the indices of
     * the tasks to be unmarked.
     *
     * @param taskManager The {@link TaskManager} instance from which the tasks will be unmarked.
     * @param indices     The indices of the tasks to be unmarked.
     */
    public UnmarkCommand(TaskManager taskManager, List<Integer> indices) {
        super(taskManager);
        this.indices = indices;
    }

    /**
     * Executes the "unmark" command, unmarking the specified tasks from the task manager.
     *
     * @return A {@link CommandResult} containing feedback on the operation and the unmarked tasks.
     * @throws InvalidValueException If any index is invalid or out of bounds.
     */
    @Override
    public CommandResult execute() throws InvalidValueException {
        return new CommandResult(
                CommandResultType.SUCCESS,
                Messages.UNMARK,
                taskManager.unmarkTasks(indices)
        );
    }
}
