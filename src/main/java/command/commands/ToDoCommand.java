package command.commands;

import command.Command;
import command.CommandResult;
import data.TaskManager;
import exception.InvalidValueException;
import data.task.Task;
import data.task.type.ToDo;
import messages.Messages;
import utils.Pair;

import java.util.List;

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
            throw new InvalidValueException(Messages.EMPTY_TODO);
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
