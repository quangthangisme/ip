package mightyduck.command.commands;

import java.util.List;
import java.util.stream.IntStream;

import mightyduck.command.Command;
import mightyduck.command.CommandResult;
import mightyduck.data.task.Task;
import mightyduck.data.task.TaskManager;
import mightyduck.messages.Messages;
import mightyduck.utils.Pair;

public class ListCommand extends Command {
    public static final String COMMAND_WORD = "list";

    public ListCommand(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public CommandResult execute() {
        List<Task> tasks = this.taskManager.getTasks();
        return new CommandResult((tasks.isEmpty()) ? Messages.EMPTY_LIST : Messages.LIST,
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
