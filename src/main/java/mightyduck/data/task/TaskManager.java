package mightyduck.data.task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import mightyduck.exception.InvalidValueException;
import mightyduck.utils.Messages;
import mightyduck.utils.Pair;

/**
 * Manages a collection of tasks.
 */
public class TaskManager {

    /**
     * A list storing all tasks managed by this instance.
     */
    private final List<Task> tasks;

    /**
     * Constructs a new TaskManager instance. Initializes the tasks list as an empty ArrayList.
     */
    public TaskManager() {
        tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The task to be added.
     * @return The index at which the task was added.
     */
    public int addTask(Task task) {
        assert task != null : "Task should not be null";
        tasks.add(task);
        return getTaskCount() - 1;
    }

    /**
     * Retrieves a task at the specified index.
     *
     * @param index The index of the task to retrieve.
     * @return The {@link Task} at the specified index.
     * @throws InvalidValueException If the index is out of range.
     */
    public Task getTask(int index) throws InvalidValueException {
        if (index < 0 || index >= tasks.size()) {
            throw new InvalidValueException(String.format(Messages.OUT_OF_RANGE_INDEX, index));
        }
        return tasks.get(index);
    }

    /**
     * Returns the total number of tasks managed.
     *
     * @return The number of tasks.
     */
    public int getTaskCount() {
        return tasks.size();
    }

    /**
     * Returns a copy of the list of tasks.
     *
     * @return A new {@link List} containing all tasks.
     */
    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    /**
     * Marks a task at the specified index as completed.
     *
     * @param index The index of the task to mark.
     * @return The {@link Task} that was marked.
     * @throws InvalidValueException If the index is out of range.
     */
    public Task markTask(int index) throws InvalidValueException {
        Task task = getTask(index);
        task.mark();
        return task;
    }

    /**
     * Marks a task at the specified index as not completed.
     *
     * @param index The index of the task to unmark.
     * @return The {@link Task} that was unmarked.
     * @throws InvalidValueException If the index is out of range.
     */
    public Task unmarkTask(int index) throws InvalidValueException {
        Task task = getTask(index);
        task.unmark();
        return task;
    }

    /**
     * Deletes a task at the specified index.
     *
     * @param index The index of the task to delete.
     * @return The {@link Task} that was deleted.
     * @throws InvalidValueException If the index is out of range.
     */
    public Task deleteTask(int index) throws InvalidValueException {
        if (index < 0 || index >= tasks.size()) {
            throw new InvalidValueException(String.format(Messages.OUT_OF_RANGE_INDEX, index));
        }
        return tasks.remove(index);
    }

    /**
     * Assigns the task at the specified index the specified tags.
     *
     * @param index The index of the task to be tagged.
     * @param tags   The list of tags to be assigned.
     * @return The {@link Task} that was tagged.
     * @throws InvalidValueException If the index is out of range or any of the tags already exist.
     */
    public Task tagTask(int index, List<String> tags) throws InvalidValueException {
        Task task = getTask(index);
        task.addTags(tags);
        return task;
    }

    /**
     * Remove from the task at the specified index the specified tags.
     *
     * @param index The index of the task to be untagged.
     * @param tags   The list of tags to be removed.
     * @return The {@link Task} that was untagged.
     * @throws InvalidValueException If the index is out of range or any of the tags are invalid.
     */
    public Task untagTask(int index, List<String> tags) throws InvalidValueException {
        Task task = getTask(index);
        task.removeTags(tags);
        return task;
    }

    /**
     * Searches for tasks whose names contain at least one of the specified keywords
     * (case-insensitive). It returns a list of tasks paired with the index of the task in the
     * list.
     *
     * @param words The list of keywords to search for in the task names.
     * @return A list of {@link Pair} objects, where each pair contains the index of the matching
     *         task and the task itself.
     */
    public List<Pair<Integer, Task>> searchKeywords(List<String> words) {
        assert words != null && !words.isEmpty() : "Search keywords should not be null or empty";
        return IntStream.range(0, tasks.size())
                .filter(i -> words.stream().anyMatch(word ->
                        tasks.get(i).getName().toLowerCase().contains(word.toLowerCase())))
                .mapToObj(i -> new Pair<>(i, tasks.get(i)))
                .toList();
    }

    /**
     * Encodes all tasks into a list of string representations.
     *
     * @return A list of encoded strings representing each task.
     */
    public List<String> encodeTasks() {
        return tasks.stream().map(Task::encode).toList();
    }
}
