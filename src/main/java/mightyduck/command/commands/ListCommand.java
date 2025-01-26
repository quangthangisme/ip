package mightyduck.command.commands;

import mightyduck.command.Command;
import mightyduck.command.CommandResult;
import mightyduck.data.TaskManager;
import mightyduck.data.task.Task;
import mightyduck.messages.Messages;
import mightyduck.utils.Pair;

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
