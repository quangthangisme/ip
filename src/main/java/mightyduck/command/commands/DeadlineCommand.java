package mightyduck.command.commands;

import java.util.List;

import mightyduck.command.Command;
import mightyduck.command.CommandResult;
import mightyduck.command.CommandResultType;
import mightyduck.data.task.Task;
import mightyduck.data.task.TaskManager;
import mightyduck.data.task.type.Deadline;
import mightyduck.exception.InvalidValueException;
import mightyduck.messages.Messages;
import mightyduck.utils.Pair;

/**
 * Represents the "deadline" command, which adds a new {@link Deadline} task to the task manager.
 */
public class DeadlineCommand extends Command {

    /**
     * The command word used to invoke the "deadline" command.
     */
    public static final String COMMAND_WORD = "deadline";

    /**
     * The format of the "deadline" command.
     */
    public static final String COMMAND_FORMAT = "deadline <name> /by <time>";

    /**
     * The list of keywords required for parsing the "deadline" command.
     */
    public static final List<String> KEYWORDS = List.of("/by");

    /**
     * The arguments provided for creating the {@link Deadline} task.
     */
    private final String[] arguments;

    /**
     * Constructs a {@code DeadlineCommand} with the specified {@link TaskManager} and arguments for
     * the deadline task.
     *
     * @param taskManager The {@link TaskManager} instance to add the deadline task to.
     * @param arguments   The arguments used to create the deadline task. Must include the task name
     *                    and the deadline time.
     */
    public DeadlineCommand(TaskManager taskManager, String[] arguments) {
        super(taskManager);
        this.arguments = arguments;
    }

    /**
     * Executes the "deadline" command, creating a new {@link Deadline} task and adding it to the
     * task manager.
     *
     * @return A {@link CommandResult} containing feedback on the operation and the newly added
     *         deadline task.
     * @throws InvalidValueException If the provided arguments are invalid or incomplete.
     */
    @Override
    public CommandResult execute() throws InvalidValueException {
        if (arguments.length != 2) {
            throw new InvalidValueException(
                    String.format(Messages.WRONG_COMMAND_FORMAT, COMMAND_FORMAT));
        }
        Task deadline = new Deadline(arguments[0], arguments[1]);
        int index = taskManager.addTask(deadline);
        return new CommandResult(
                CommandResultType.SUCCESS,
                Messages.ADD_TASK,
                List.of(new Pair<>(index, deadline))
        );
    }
}
