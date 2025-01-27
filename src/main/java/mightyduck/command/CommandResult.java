package mightyduck.command;

import java.util.List;

import mightyduck.data.task.Task;
import mightyduck.data.task.TaskManager;
import mightyduck.utils.Pair;

/**
 * Represents the result of a command execution.
 *
 * @param feedback The message resulting from the command execution.
 * @param tasks    The list of tasks that are part of the result of the command. Each task is paired
 *                 with its index in the {@link TaskManager}.
 */
public record CommandResult(String feedback, List<Pair<Integer, Task>> tasks) {
}
