package mightyduck.command.commands;

import mightyduck.command.Command;
import mightyduck.command.CommandResult;
import mightyduck.data.TaskManager;
import mightyduck.data.task.Task;
import mightyduck.exception.InvalidValueException;
import mightyduck.messages.Messages;
import mightyduck.utils.Pair;

import java.util.List;

public class UnmarkCommand extends Command {
    public static final String COMMAND_WORD = "unmark";

    private final int index;

    public UnmarkCommand(TaskManager taskManager, int index) {
        super(taskManager);
        this.index = index;
    }

    @Override
    public CommandResult execute() throws InvalidValueException {
        Task task = this.taskManager.unmarkTask(this.index);
        return new CommandResult(
                Messages.UNMARK,
                List.of(new Pair<>(this.index, task))
        );
    }

    @Override
    public boolean isBye() {
        return false;
    }
}
