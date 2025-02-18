package mightyduck.command;

import java.util.List;

import mightyduck.task.TaskManager;
import mightyduck.utils.Messages;

/**
 * Represents the "help" command, which displays a help message explaining the available features.
 */
public class HelpCommand extends Command {

    /**
     * Constructs a {@code HelpCommand} with the specified {@link TaskManager}.
     *
     * @param taskManager The {@link TaskManager} instance to be used by this command.
     */
    public HelpCommand(TaskManager taskManager) {
        super(taskManager);
    }

    /**
     * Executes the "help" command, returning a message that explains the features to the user.
     *
     * @return A {@link CommandResult} containing the help message.
     */
    @Override
    public CommandResult execute() {
        return new CommandResult(
                CommandResultType.HELP,
                Messages.HELP_MESSAGE,
                List.of()
        );
    }
}
