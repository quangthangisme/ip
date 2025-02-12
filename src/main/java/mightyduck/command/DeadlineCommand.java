package mightyduck.command;

import java.time.LocalDateTime;
import java.util.List;

import mightyduck.task.Deadline;
import mightyduck.task.Task;
import mightyduck.task.TaskManager;
import mightyduck.utils.Messages;
import mightyduck.utils.Pair;

/**
 * Represents the "deadline" command, which adds a new {@link Deadline} task to the task manager.
 */
public class DeadlineCommand extends Command {

    /**
     * The name provided for creating the {@link Deadline} task.
     */
    private final String taskName;

    /**
     * The deadline time provided for creating the {@link Deadline} task.
     */
    private final LocalDateTime deadline;

    /**
     * The tags provided for creating the {@link Deadline} task.
     */
    private final List<String> tags;

    /**
     * Constructs a {@code DeadlineCommand} with the specified {@link TaskManager} and arguments for
     * the deadline task.
     *
     * @param taskManager The {@link TaskManager} instance to add the deadline task to.
     * @param taskName    The name of the task to be created.
     * @param deadline    The {@link LocalDateTime} representing the deadline time of the task.
     * @param tags        The list of tags associated with the task.
     */
    public DeadlineCommand(TaskManager taskManager, String taskName, LocalDateTime deadline,
                           List<String> tags) {
        super(taskManager);
        this.taskName = taskName;
        this.deadline = deadline;
        this.tags = tags;
    }

    /**
     * Executes the "deadline" command, creating a new {@link Deadline} task and adding it to the
     * task manager.
     *
     * @return A {@link CommandResult} containing feedback on the operation and the newly added
     *         deadline task.
     */
    @Override
    public CommandResult execute() {
        Task deadlineTask = new Deadline(taskName, deadline, tags);
        int index = taskManager.addTask(deadlineTask);
        return new CommandResult(
                CommandResultType.SUCCESS,
                Messages.ADD_TASK,
                List.of(new Pair<>(index, deadlineTask))
        );
    }
}
