package command.commands;

import command.Command;
import command.CommandResult;
import data.TaskManager;
import data.task.Task;
import messages.Messages;
import utils.Pair;

import java.util.List;
import java.util.stream.IntStream;

public class ListCommand extends Command {
    public static final String COMMAND_WORD = "list";

    public ListCommand(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public CommandResult execute() {
        List<Task> tasks = this.taskManager.getTasks();
        return new CommandResult(
                (tasks.isEmpty()) ?
                        Messages.EMPTY_LIST : Messages.LIST,
                IntStream.range(0, tasks.size())
                        .mapToObj(i -> new Pair<>(i, tasks.get(i)))
                        .toList()
        );
    }

    @Override
    public boolean isBye() {
        return false;
    }
}
