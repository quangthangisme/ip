package mightyduck.command.commands;

import mightyduck.command.Command;
import mightyduck.command.CommandResult;
import mightyduck.data.task.TaskManager;
import mightyduck.data.task.Task;
import mightyduck.exception.InvalidValueException;
import mightyduck.messages.Messages;
import mightyduck.utils.Pair;

import java.util.List;

public class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";

    private final int index;

    public DeleteCommand(TaskManager taskManager, int index) {
        super(taskManager);
        this.index = index;
    }

    @Override
    public CommandResult execute() throws InvalidValueException {
        Task task = this.taskManager.deleteTask(this.index);
        return new CommandResult(
                Messages.DELETE,
                List.of(new Pair<>(this.index, task))
        );
    }

    @Override
    public boolean isBye() {
        return false;
    }
}
