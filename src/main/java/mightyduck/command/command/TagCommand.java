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
 * Represents the "tag" command, which assigns a task a tag in the {@link TaskManager}.
 */
public class TagCommand extends Command {

    /**
     * The index of the task to be tagged.
     */
    private final int index;

    /**
     * The list of tags to be assigned to the task.
     */
    private final List<String> tags;

    /**
     * Constructs a {@code TagCommand} with the specified {@link TaskManager}, task index, and tags.
     *
     * @param taskManager The {@link TaskManager} instance to manage the task.
     * @param index       The index of the task to be tagged.
     * @param tags        The list of tags to be assigned to the task.
     */
    public TagCommand(TaskManager taskManager, int index, List<String> tags) {
        super(taskManager);
        this.index = index;
        this.tags = tags;
    }

    /**
     * Executes the "tag" command, tagging the specified task and returning the result of the
     * operation.
     *
     * @return A {@link CommandResult} containing the feedback message and the updated task.
     * @throws InvalidValueException If the task index or any of the tags are invalid.
     */
    @Override
    public CommandResult execute() throws InvalidValueException {
        Task task = taskManager.tagTask(index, tags);
        return new CommandResult(
                CommandResultType.SUCCESS,
                Messages.TAG,
                List.of(new Pair<>(index, task))
        );
    }
}
