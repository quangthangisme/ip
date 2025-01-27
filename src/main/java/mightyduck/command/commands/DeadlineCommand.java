package mightyduck.command.commands;

import mightyduck.command.Command;
import mightyduck.command.CommandResult;
import mightyduck.data.task.TaskManager;
import mightyduck.exception.InvalidValueException;
import mightyduck.data.task.type.Deadline;
import mightyduck.data.task.Task;
import mightyduck.messages.Messages;
import mightyduck.utils.Pair;

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
            throw new InvalidValueException(String.format(
                    Messages.WRONG_COMMAND_FORMAT, COMMAND_FORMAT));
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

