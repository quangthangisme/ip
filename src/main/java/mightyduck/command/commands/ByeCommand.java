package mightyduck.command.commands;

import java.util.List;

import mightyduck.command.Command;
import mightyduck.command.CommandResult;
import mightyduck.command.CommandResultType;
import mightyduck.data.task.TaskManager;
import mightyduck.messages.Messages;

/**
 * Represents the "bye" command, which terminates the application.
 */
public class ByeCommand extends Command {

    /**
     * The command word used to invoke the "bye" command.
     */
    public static final String COMMAND_WORD = "bye";

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
