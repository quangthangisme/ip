package mightyduck.command.commands;

import java.util.List;

import mightyduck.command.Command;
import mightyduck.command.CommandResult;
import mightyduck.data.task.Task;
import mightyduck.data.task.TaskManager;
import mightyduck.data.task.type.Event;
import mightyduck.exception.InvalidValueException;
import mightyduck.messages.Messages;
import mightyduck.utils.Pair;

public class EventCommand extends Command {
    public static final String COMMAND_WORD = "event";
    public static final String COMMAND_FORMAT = "event <name> /from <time> /to <time>";
    public static final List<String> KEYWORDS = List.of("/from", "/to");

    private final String[] arguments;

    public EventCommand(TaskManager taskManager, String[] arguments) {
        super(taskManager);
        this.arguments = arguments;
    }

    @Override
    public CommandResult execute() throws InvalidValueException {
        if (this.arguments.length != 3) {
            throw new InvalidValueException(String.format(
                    Messages.WRONG_COMMAND_FORMAT, COMMAND_FORMAT));
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
