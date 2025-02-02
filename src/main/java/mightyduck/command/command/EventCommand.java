package mightyduck.command.command;

import java.time.LocalDateTime;
import java.util.List;

import mightyduck.command.CommandResult;
import mightyduck.command.CommandResultType;
import mightyduck.data.task.Task;
import mightyduck.data.task.TaskManager;
import mightyduck.data.task.type.Event;
import mightyduck.utils.Messages;
import mightyduck.utils.Pair;

/**
 * Represents the "event" command, which adds a new {@link Event} task to the task manager.
 */
public class EventCommand extends Command {

    /**
     * The name provided for creating the {@link Event} task.
     */
    private final String taskName;

    /**
     * The start time provided for creating the {@link Event} task.
     */
    private final LocalDateTime startTime;

    /**
     * The end time provided for creating the {@link Event} task.
     */
    private final LocalDateTime endTime;

    /**
     * Constructs a {@code EventCommand} with the specified {@link TaskManager} and arguments for
     * the event task.
     *
     * @param taskManager The {@link TaskManager} instance to add the event task to.
     * @param taskName    The name of the task to be created.
     * @param startTime   The {@link LocalDateTime} representing the start time of the task.
     * @param endTime     The {@link LocalDateTime} representing the end time of the task.
     */
    public EventCommand(TaskManager taskManager, String taskName, LocalDateTime startTime,
                        LocalDateTime endTime) {
        super(taskManager);
        this.taskName = taskName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Executes the "event" command, creating a new {@link Event} task and adding it to the task
     * manager.
     *
     * @return A {@link CommandResult} containing feedback on the operation and the newly added
     *         event task.
     */
    @Override
    public CommandResult execute() {
        Task event = new Event(taskName, startTime, endTime);
        int index = taskManager.addTask(event);
        return new CommandResult(
                CommandResultType.SUCCESS,
                Messages.ADD_TASK,
                List.of(new Pair<>(index, event))
        );
    }
}
