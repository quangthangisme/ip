package mightyduck.data.task;

import mightyduck.exception.InvalidValueException;
import mightyduck.messages.Messages;
import mightyduck.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class TaskManager {
    private final List<Task> tasks = new ArrayList<>();

    public int addTask(Task task) {
        this.tasks.add(task);
        return this.getTaskCount() - 1;
    }

    public Task getTask(int index) throws InvalidValueException {
        if (index < 0 || index >= this.tasks.size()) {
            throw new InvalidValueException(Messages.OUT_OF_RANGE_INDEX);
        }
        return this.tasks.get(index);
    }

    public int getTaskCount() {
        return this.tasks.size();
    }

    public List<Task> getTasks() {
        return new ArrayList<>(this.tasks);
    }

    public Task markTask(int index) throws InvalidValueException {
        Task task = this.getTask(index);
        task.mark();
        return task;
    }

    public Task unmarkTask(int index) throws InvalidValueException {
        Task task = this.getTask(index);
        task.unmark();
        return task;
    }

    public Task deleteTask(int index) throws InvalidValueException {
        if (index < 0 || index >= this.tasks.size()) {
            throw new InvalidValueException(Messages.OUT_OF_RANGE_INDEX);
        }
        return this.tasks.remove(index);
    }

    public List<Pair<Integer, Task>> searchKeyword(String word) {
        return IntStream.range(0, this.tasks.size())
                .filter(i -> this.tasks.get(i).getName().toLowerCase().contains(word.toLowerCase()))
                .mapToObj(i -> new Pair<>(i, this.tasks.get(i)))
                .toList();
    }

    public List<String> encodeTasks() {
        return this.tasks.stream().map(Task::encode).toList();
    }
}
