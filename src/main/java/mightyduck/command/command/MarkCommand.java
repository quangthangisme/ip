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
 * Represents the "mark" command, which marks a task as completed in the {@link TaskManager}.
 */
public class MarkCommand extends Command {

    /**
     * The index of the task to be marked as completed.
     */
    private final int index;

    /**
     * Constructs a {@code MarkCommand} with the specified {@link TaskManager} and task index.
     *
     * @param taskManager The {@link TaskManager} instance to manage the task.
     * @param index       The index of the task to be marked as completed.
     */
    public MarkCommand(TaskManager taskManager, int index) {
        super(taskManager);
        this.index = index;
    }

    /**
     * Executes the "mark" command, marking the specified task as completed and returning the result
     * of the operation.
     *
     * @return A {@link CommandResult} containing the feedback message and the updated task.
     * @throws InvalidValueException If the task index is invalid.
     */
    @Override
    public CommandResult execute() throws InvalidValueException {
        Task task = taskManager.markTask(index);
        return new CommandResult(
                CommandResultType.SUCCESS,
                Messages.MARK,
                List.of(new Pair<>(index, task))
        );
    }
}
