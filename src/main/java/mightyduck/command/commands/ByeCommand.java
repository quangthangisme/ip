package mightyduck.command.commands;

import mightyduck.command.Command;
import mightyduck.command.CommandResult;
import mightyduck.data.TaskManager;
import mightyduck.messages.Messages;

import java.util.List;

public class ByeCommand extends Command {
    public static final String COMMAND_WORD = "bye";

    public ByeCommand(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public CommandResult execute() {
        return new CommandResult(Messages.BYE, List.of());
    }

    @Override
    public boolean isBye() {
        return true;
    }
}
