package mightyduck.data.task;

import java.util.ArrayList;
import java.util.List;

import mightyduck.exception.InvalidValueException;
import mightyduck.messages.Messages;

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

    public List<String> encodeTasks() {
        return this.tasks.stream().map(Task::encode).toList();
    }
}
