package mightyduck.command.commands;

import java.util.List;

import mightyduck.command.Command;
import mightyduck.command.CommandResult;
import mightyduck.data.task.Task;
import mightyduck.data.task.TaskManager;
import mightyduck.exception.InvalidValueException;
import mightyduck.messages.Messages;
import mightyduck.utils.Pair;

public class FindCommand extends Command {
    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_FORMAT = "find <keyword>";
    public static final List<String> KEYWORDS = List.of();

    private final String[] arguments;

    public FindCommand(TaskManager taskManager, String[] arguments) {
        super(taskManager);
        this.arguments = arguments;
    }

    @Override
    public CommandResult execute() throws InvalidValueException {
        if (this.arguments.length != 1) {
            throw new InvalidValueException(String.format(
                    Messages.WRONG_COMMAND_FORMAT, COMMAND_FORMAT));
        }
        List<Pair<Integer, Task>> res = this.taskManager.searchKeyword(this.arguments[0]);
        return new CommandResult((res.isEmpty()) ? Messages.EMPTY_FIND : Messages.FIND, res);
    }

    @Override
    public boolean isBye() {
        return false;
    }
}
