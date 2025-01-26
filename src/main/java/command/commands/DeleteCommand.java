package command.commands;

import command.Command;
import command.CommandResult;
import data.TaskManager;
import data.task.Task;
import exception.InvalidValueException;
import messages.Messages;
import utils.Pair;

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
