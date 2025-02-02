package mightyduck.command.builder;

import mightyduck.command.command.ToDoCommand;
import mightyduck.data.task.TaskManager;
import mightyduck.exception.InvalidCommandException;
import mightyduck.utils.Messages;

/**
 * A builder class for constructing a {@link ToDoCommand} from user input.
 */
public class ToDoBuilder extends Builder {

    /**
     * The command word used to invoke the "todo" command.
     */
    public static final String COMMAND_WORD = "todo";

    /**
     * The format of the "todo" command.
     */
    public static final String COMMAND_FORMAT = "todo <name>";

    /**
     * Constructs a new {@link ToDoBuilder} with the specified {@link TaskManager}.
     *
     * @param taskManager The {@link TaskManager} instance that manages tasks.
     */
    public ToDoBuilder(TaskManager taskManager) {
        super(taskManager);
    }

    /**
     * Creates a {@code ToDoCommand} from user-provided input.
     *
     * @param input       The user-provided input string, expected to contain the task's name.
     * @return A new {@code ToDoCommand} instance.
     * @throws InvalidCommandException If the input is incorrectly formatted.
     */
    public ToDoCommand fromInput(String input) throws InvalidCommandException {
        if (input.isEmpty()) {
            throw new InvalidCommandException(String.format(Messages.WRONG_COMMAND_FORMAT,
                    COMMAND_FORMAT));
        }

        return new ToDoCommand(taskManager, input);
    }
}
