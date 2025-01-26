package mightyduck.command;

import mightyduck.data.task.Task;
import mightyduck.utils.Pair;

import java.util.List;

public record CommandResult(String feedback, List<Pair<Integer, Task>> tasks) {}
