package mightyduck.command;

import java.util.List;

import mightyduck.task.TaskManager;
import mightyduck.utils.Messages;

/**
 * Represents the "bye" command, which terminates the application.
 */
public class ByeCommand extends Command {

    /**
     * Constructs a {@code ByeCommand} with the specified {@link TaskManager}.
     *
     * @param taskManager The {@link TaskManager} instance to be used by this command.
     */
    public ByeCommand(TaskManager taskManager) {
        super(taskManager);
    }

    /**
     * Executes the "bye" command, signaling the termination of the application.
     *
     * @return A {@link CommandResult} containing the goodbye message and an empty task list.
     */
    @Override
    public CommandResult execute() {
        return new CommandResult(
                CommandResultType.TERMINATION,
                Messages.BYE,
                List.of()
        );
    }
}
