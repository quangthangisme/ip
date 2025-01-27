package mightyduck.command.commands;

import java.util.List;

import mightyduck.command.Command;
import mightyduck.command.CommandResult;
import mightyduck.data.task.Task;
import mightyduck.data.task.TaskManager;
import mightyduck.data.task.type.Event;
import mightyduck.exception.InvalidValueException;
import mightyduck.messages.Messages;
import mightyduck.utils.Pair;

/**
 * Represents the "event" command, which adds a new {@link Event} task to the task manager.
 */
public class EventCommand extends Command {

    /**
     * The command word used to invoke the "event" command.
     */
    public static final String COMMAND_WORD = "event";

    /**
     * The format of the "event" command.
     */
    public static final String COMMAND_FORMAT = "event <name> " + "/from <time> /to <time>";

    /**
     * The list of keywords required for parsing the "event" command.
     */
    public static final List<String> KEYWORDS = List.of("/from", "/to");

    /**
     * The arguments provided for creating the {@link Event} task.
     */
    private final String[] arguments;

    /**
     * Constructs a {@code EventCommand} with the specified {@link TaskManager} and arguments for
     * the event task.
     *
     * @param taskManager The {@link TaskManager} instance to add the event task to.
     * @param arguments   The arguments used to create the event task. Must include the task name, a
     *                    start time, and an end time.
     */
    public EventCommand(TaskManager taskManager, String[] arguments) {
        super(taskManager);
        this.arguments = arguments;
    }

    /**
     * Executes the "event" command, creating a new {@link Event} task and adding it to the task
     * manager.
     *
     * @return A {@link CommandResult} containing feedback on the operation and the newly added
     *         event task.
     * @throws InvalidValueException If the provided arguments are invalid or incomplete.
     */
    @Override
    public CommandResult execute() throws InvalidValueException {
        if (this.arguments.length != 3) {
            throw new InvalidValueException(
                    String.format(Messages.WRONG_COMMAND_FORMAT, COMMAND_FORMAT));
        }
        Task event = new Event(arguments[0], arguments[1], arguments[2]);
        int index = this.taskManager.addTask(event);
        return new CommandResult(Messages.ADD_TASK, List.of(new Pair<>(index, event)));
    }

    @Override
    public boolean isBye() {
        return false;
    }
}
