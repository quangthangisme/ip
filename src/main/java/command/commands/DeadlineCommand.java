package command.commands;

import command.Command;
import command.CommandResult;
import data.TaskManager;
import exception.InvalidValueException;
import data.task.type.Deadline;
import data.task.Task;
import messages.Messages;
import utils.Pair;

import java.util.List;

public class DeadlineCommand extends Command {
    public static final String COMMAND_WORD = "deadline";
    public static final String COMMAND_FORMAT = "deadline <name> /by <time>";
    public static final List<String> KEYWORDS = List.of("/by");

    private final String[] arguments;

    public DeadlineCommand(TaskManager taskManager, String[] arguments) {
        super(taskManager);
        this.arguments = arguments;
    }

    @Override
    public CommandResult execute() throws InvalidValueException {
        if (this.arguments.length != 2) {
            throw new InvalidValueException(Messages.EMPTY_TODO);
        }
        Task deadline = new Deadline(arguments[0], arguments[1]);
        int index = this.taskManager.addTask(deadline);
        return new CommandResult(
                Messages.ADD_TASK,
                List.of(new Pair<>(index, deadline))
        );
    }

    @Override
    public boolean isBye() {
        return false;
    }
}

