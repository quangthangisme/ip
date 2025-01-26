package command;

import data.task.Task;
import utils.Pair;

import java.util.List;

public record CommandResult(String feedback, List<Pair<Integer, Task>> tasks) {}
