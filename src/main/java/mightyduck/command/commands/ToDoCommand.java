package mightyduck.command.commands;

import java.util.List;

import mightyduck.command.Command;
import mightyduck.command.CommandResult;
import mightyduck.data.task.Task;
import mightyduck.data.task.TaskManager;
import mightyduck.data.task.type.ToDo;
import mightyduck.exception.InvalidValueException;
import mightyduck.messages.Messages;
import mightyduck.utils.Pair;

/**
 * Represents the "todo" command, which adds a new {@link ToDo} task to the task manager.
 */
public class ToDoCommand extends Command {

    /**
     * The command word used to invoke the "todo" command.
     */
    public static final String COMMAND_WORD = "todo";

    /**
     * The format of the "todo" command.
     */
    public static final String COMMAND_FORMAT = "todo <name>";

    /**
     * The list of keywords required for parsing the "todo" command.
     */
    public static final List<String> KEYWORDS = List.of();

    /**
     * The arguments provided for creating the {@link ToDo} task.
     */
    private final String[] arguments;

    /**
     * Constructs a {@code ToDoCommand} with the specified {@link TaskManager} and arguments for the
     * deadline task.
     *
     * @param taskManager The {@link TaskManager} instance to add the to-do task to.
     * @param arguments   The arguments used to create the to-do task. Must include the task name.
     */
    public ToDoCommand(TaskManager taskManager, String[] arguments) {
        super(taskManager);
        this.arguments = arguments;
    }

    /**
     * Executes the "todo" command, creating a new {@link ToDo} task and adding it to the task
     * manager.
     *
     * @return A {@link CommandResult} containing feedback on the operation and the newly added
     *         to-do task.
     * @throws InvalidValueException If the provided arguments are invalid or incomplete.
     */
    @Override
    public CommandResult execute() throws InvalidValueException {
        if (arguments.length != 1) {
            throw new InvalidValueException(
                    String.format(Messages.WRONG_COMMAND_FORMAT, COMMAND_FORMAT));
        }
        Task todo = new ToDo(arguments[0]);
        int index = taskManager.addTask(todo);
        return new CommandResult(Messages.ADD_TASK, List.of(new Pair<>(index, todo)));
    }

    @Override
    public boolean isBye() {
        return false;
    }
}
