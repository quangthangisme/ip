package mightyduck.task;

import java.util.ArrayList;
import java.util.Collections;
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
     * Marks tasks at the specified indices as completed.
     *
     * @param indices The list of indices of the tasks to mark.
     * @return A list of {@link Pair} with the index and the marked {@link Task}.
     * @throws InvalidValueException If any of the indices are out of range or if any tasks are
     *                               already marked.
     */
    public List<Pair<Integer, Task>> markTasks(List<Integer> indices) throws InvalidValueException {
        validateIndices(indices);
        for (int index : indices) {
            Task task = getTask(index);
            if (task.isMarked()) {
                throw new InvalidValueException(
                        String.format(Messages.ALREADY_MARKED, task.getName()));
            }
        }

        List<Pair<Integer, Task>> result = new ArrayList<>();
        for (int index : indices) {
            Task task = getTask(index);
            task.mark();
            result.add(new Pair<>(index, task));
        }
        return result;
    }

    /**
     * Marks tasks at the specified indices as not completed.
     *
     * @param indices The list of indices of the tasks to unmark.
     * @return A list of {@link Pair} with the index and the unmarked {@link Task}.
     * @throws InvalidValueException If any of the indices are out of range or if any tasks are
     *                               already unmarked.
     */
    public List<Pair<Integer, Task>> unmarkTasks(List<Integer> indices)
            throws InvalidValueException {
        validateIndices(indices);
        for (int index : indices) {
            Task task = getTask(index);
            if (!task.isMarked()) {
                throw new InvalidValueException(
                        String.format(Messages.ALREADY_UNMARKED, task.getName()));
            }
        }

        List<Pair<Integer, Task>> result = new ArrayList<>();
        for (int index : indices) {
            Task task = getTask(index);
            task.unmark();
            result.add(new Pair<>(index, task));
        }
        return result;
    }

    /**
     * Assigns the specified tags to tasks at the given indices.
     *
     * @param indices The list of indices of the tasks to be tagged.
     * @param tags    The list of tags to be assigned.
     * @return A list of {@link Pair} with the index and the tagged {@link Task}.
     * @throws InvalidValueException If any of the indices are out of range or if any tasks already
     *                               have one of the tags.
     */
    public List<Pair<Integer, Task>> tagTasks(List<Integer> indices, List<String> tags)
            throws InvalidValueException {
        validateIndices(indices);
        for (int index : indices) {
            Task task = getTask(index);
            for (String tag : tags) {
                if (task.hasTag(tag)) {
                    throw new InvalidValueException(
                            String.format(Messages.TAG_ALREADY_EXISTED, tag, task.getName()));
                }
            }
        }

        List<Pair<Integer, Task>> result = new ArrayList<>();
        for (int index : indices) {
            Task task = getTask(index);
            task.addTags(tags);
            result.add(new Pair<>(index, task));
        }
        return result;
    }

    /**
     * Removes the specified tags from tasks at the given indices.
     *
     * @param indices The list of indices of the tasks to be untagged.
     * @param tags    The list of tags to be removed.
     * @return A list of {@link Pair} with the index and the untagged {@link Task}.
     * @throws InvalidValueException If any of the indices are out of range or if any tasks do not
     *                               have all the tags to remove.
     */
    public List<Pair<Integer, Task>> untagTasks(List<Integer> indices, List<String> tags)
            throws InvalidValueException {
        validateIndices(indices);
        for (int index : indices) {
            Task task = getTask(index);
            for (String tag : tags) {
                if (!task.hasTag(tag)) {
                    throw new InvalidValueException(
                            String.format(Messages.TAG_NOT_FOUND, tag, task.getName()));
                }
            }
        }

        List<Pair<Integer, Task>> result = new ArrayList<>();
        for (int index : indices) {
            Task task = getTask(index);
            task.removeTags(tags);
            result.add(new Pair<>(index, task));
        }
        return result;
    }


    /**
     * Deletes tasks at the specified indices.
     *
     * @param indices The immutable list of indices of the tasks to delete.
     * @return A list of {@link Pair} with the index and the deleted {@link Task}.
     * @throws InvalidValueException If any of the indices are out of range.
     */
    public List<Pair<Integer, Task>> deleteTasks(List<Integer> indices)
            throws InvalidValueException {
        validateIndices(indices);

        List<Pair<Integer, Task>> result = new ArrayList<>();

        // Sort indices in descending order to prevent index shifting during deletion
        List<Integer> sortedIndices = new ArrayList<>(indices);
        sortedIndices.sort(Collections.reverseOrder());

        for (int index : sortedIndices) {
            Task task = tasks.remove(index);
            result.add(new Pair<>(index, task));
        }

        return result;
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

    /**
     * Checks if the indices are at least 0 and smaller than the number of tasks
     *
     * @param indices The list of indices to validate.
     * @throws InvalidValueException If at least one of the indices is invalid.
     */
    private void validateIndices(List<Integer> indices) throws InvalidValueException {
        for (int index : indices) {
            if (index < 0 || index >= tasks.size()) {
                throw new InvalidValueException(String.format(Messages.OUT_OF_RANGE_INDEX, index));
            }
        }
    }
}
