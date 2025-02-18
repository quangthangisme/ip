package mightyduck.parser;

import mightyduck.command.HelpCommand;
import mightyduck.task.TaskManager;

/**
 * A builder class for constructing a {@link HelpCommand} from user input.
 */
public class HelpBuilder extends Builder {

    /**
     * The command word used to invoke the "help" command.
     */
    public static final String COMMAND_WORD = "help";

    /**
     * Constructs a new {@link HelpBuilder} with the specified {@link TaskManager}.
     *
     * @param taskManager The {@link TaskManager} instance that manages tasks.
     */
    public HelpBuilder(TaskManager taskManager) {
        super(taskManager);
    }

    /**
     * Parses the user input and returns a {@link HelpCommand}.
     *
     * @param input The user input string.
     * @return A new {@link HelpCommand} instance.
     */
    public HelpCommand fromInput(String input) {
        return new HelpCommand(taskManager);
    }
}
