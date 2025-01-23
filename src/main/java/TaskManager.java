import task.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private final List<Task> tasks = new ArrayList<>();

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public Task getTask(int index) {
        if (index < 0 || index >= this.tasks.size()) {
            throw new IllegalArgumentException("Task number out of range!");
        }
        return this.tasks.get(index);
    }

    public int getTaskCount() {
        return this.tasks.size();
    }

    public List<Task> getTasks() {
        return new ArrayList<>(this.tasks);
    }

    public Task markTask(int index) {
        Task task = this.getTask(index);
        task.mark();
        return task;
    }

    public Task unmarkTask(int index) {
        Task task = this.getTask(index);
        task.unmark();
        return task;
    }
}
