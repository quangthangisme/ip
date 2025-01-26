package command.commands;

import command.Command;
import command.CommandResult;
import data.TaskManager;
import exception.InvalidValueException;
import data.task.type.Event;
import data.task.Task;
import messages.Messages;
import utils.Pair;

import java.util.List;

public class EventCommand extends Command {
    public static final String COMMAND_WORD = "event";
    public static final String COMMAND_FORMAT = "event <name> " +
            "/from <time> /to <time>";
    public static final List<String> KEYWORDS = List.of("/from", "/to");

    private final String[] arguments;

    public EventCommand(TaskManager taskManager, String[] arguments) {
        super(taskManager);
        this.arguments = arguments;
    }

    @Override
    public CommandResult execute() throws InvalidValueException {
        if (this.arguments.length != 3) {
            throw new InvalidValueException(Messages.EMPTY_TODO);
        }
        Task event = new Event(arguments[0], arguments[1], arguments[2]);
        int index = this.taskManager.addTask(event);
        return new CommandResult(
                Messages.ADD_TASK,
                List.of(new Pair<>(index, event))
        );
    }

    @Override
    public boolean isBye() {
        return false;
    }
}


