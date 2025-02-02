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
 * Represents the "unmark" command, which marks a task as not completed in the {@link TaskManager}.
 */
public class UnmarkCommand extends Command {

    /**
     * The index of the task to be marked as not completed.
     */
    private final int index;

    /**
     * Constructs an {@code UnmarkCommand} with the specified {@link TaskManager} and task index.
     *
     * @param taskManager The {@link TaskManager} instance to manage the task.
     * @param index       The index of the task to be marked as not completed.
     */
    public UnmarkCommand(TaskManager taskManager, int index) {
        super(taskManager);
        this.index = index;
    }

    /**
     * Executes the "unmark" command, marking the specified task as not completed and returning the
     * result of the operation.
     *
     * @return A {@link CommandResult} containing the feedback message and the updated task.
     * @throws InvalidValueException If the task index is invalid.
     */
    @Override
    public CommandResult execute() throws InvalidValueException {
        Task task = taskManager.unmarkTask(index);
        return new CommandResult(
                CommandResultType.SUCCESS,
                Messages.UNMARK,
                List.of(new Pair<>(index, task))
        );
    }
}
