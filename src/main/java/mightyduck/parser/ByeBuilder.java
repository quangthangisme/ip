package mightyduck.parser;

import mightyduck.command.ByeCommand;
import mightyduck.task.TaskManager;

/**
 * A builder class for constructing a {@link ByeCommand} from user input.
 */
public class ByeBuilder extends Builder {

    /**
     * The command word used to invoke the "bye" command.
     */
    public static final String COMMAND_WORD = "bye";

    /**
     * Constructs a new {@link ByeBuilder} with the specified {@link TaskManager}.
     *
     * @param taskManager The {@link TaskManager} instance that manages tasks.
     */
    public ByeBuilder(TaskManager taskManager) {
        super(taskManager);
    }

    /**
     * Parses the user input and returns a {@link ByeCommand}.
     *
     * @param input The user input string.
     * @return A new {@link ByeCommand} instance.
     */
    public ByeCommand fromInput(String input) {
        return new ByeCommand(taskManager);
    }
}
