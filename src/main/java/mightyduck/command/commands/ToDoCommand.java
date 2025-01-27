package mightyduck.command.commands;

import java.util.List;

import mightyduck.command.Command;
import mightyduck.command.CommandResult;
import mightyduck.data.task.Task;
import mightyduck.data.task.TaskManager;
import mightyduck.data.task.type.ToDo;
import mightyduck.exception.InvalidValueException;
import mightyduck.messages.Messages;
import mightyduck.utils.Pair;

public class ToDoCommand extends Command {
    public static final String COMMAND_WORD = "todo";
    public static final String COMMAND_FORMAT = "todo <name>";
    public static final List<String> KEYWORDS = List.of();

    private final String[] arguments;

    public ToDoCommand(TaskManager taskManager, String[] arguments) {
        super(taskManager);
        this.arguments = arguments;
    }

    @Override
    public CommandResult execute() throws InvalidValueException {
        if (this.arguments.length != 1) {
            throw new InvalidValueException(String.format(
                    Messages.WRONG_COMMAND_FORMAT, COMMAND_FORMAT));
        }
        Task todo = new ToDo(arguments[0]);
        int index = this.taskManager.addTask(todo);
        return new CommandResult(
                Messages.ADD_TASK,
                List.of(new Pair<>(index, todo))
        );
    }

    @Override
    public boolean isBye() {
        return false;
    }
}
