package command.commands;

import command.Command;
import command.CommandResult;
import data.TaskManager;
import data.task.Task;
import exception.InvalidValueException;
import messages.Messages;
import utils.Pair;

import java.util.List;

public class MarkCommand extends Command {
    public static final String COMMAND_WORD = "mark";

    private final int index;

    public MarkCommand(TaskManager taskManager, int index) {
        super(taskManager);
        this.index = index;
    }

    @Override
    public CommandResult execute() throws InvalidValueException {
        Task task = this.taskManager.markTask(this.index);
        return new CommandResult(
                Messages.MARK,
                List.of(new Pair<>(this.index, task))
        );
    }

    @Override
    public boolean isBye() {
        return false;
    }
}
