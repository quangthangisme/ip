package mightyduck.command;

import java.util.List;

import mightyduck.exception.InvalidValueException;
import mightyduck.task.TaskManager;
import mightyduck.utils.Messages;

/**
 * Represents the "tag" command, which assigns multiple tasks in the {@link TaskManager} some tags.
 */
public class TagCommand extends Command {

    /**
     * The indices of the tasks to be tagged.
     */
    private final List<Integer> indices;

    /**
     * The list of tags to be assigned to the tasks.
     */
    private final List<String> tags;

    /**
     * Constructs a {@code TagCommand} with the specified {@link TaskManager}, task indices, and
     * tags.
     *
     * @param taskManager The {@link TaskManager} instance to manage the task.
     * @param indices     The indices of the tasks to be tagged.
     * @param tags        The list of tags to be assigned to the task.
     */
    public TagCommand(TaskManager taskManager, List<Integer> indices, List<String> tags) {
        super(taskManager);
        this.indices = indices;
        this.tags = tags;
    }

    /**
     * Executes the "tag" command, tagging the specified tasks and returning the result of the
     * operation.
     *
     * @return A {@link CommandResult} containing the feedback message and the updated tasks.
     * @throws InvalidValueException If the task index or any of the tags are invalid.
     */
    @Override
    public CommandResult execute() throws InvalidValueException {
        return new CommandResult(
                CommandResultType.SUCCESS,
                Messages.TAG,
                taskManager.tagTasks(indices, tags)
        );
    }
}
