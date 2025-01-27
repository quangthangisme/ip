package mightyduck.command;

import java.util.List;

import mightyduck.data.task.Task;
import mightyduck.utils.Pair;

public record CommandResult(String feedback, List<Pair<Integer, Task>> tasks) {
}
