package mightyduck.command.builder;

import mightyduck.command.command.ListCommand;
import mightyduck.data.task.TaskManager;

/**
 * A builder class for constructing a {@link ListCommand} from user input.
 */
public class ListBuilder extends Builder {

    /**
     * The command word used to invoke the "list" command.
     */
    public static final String COMMAND_WORD = "list";

    /**
     * Constructs a new {@link ListCommand} with the specified {@link TaskManager}.
     *
     * @param taskManager The {@link TaskManager} instance that manages tasks.
     */
    public ListBuilder(TaskManager taskManager) {
        super(taskManager);
    }

    public ListCommand fromInput(String input) {
        return new ListCommand(taskManager);
    }
}
