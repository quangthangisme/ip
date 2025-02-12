package mightyduck.task;

import java.util.ArrayList;
import java.util.List;

import mightyduck.exception.InvalidValueException;
import mightyduck.utils.Messages;

/**
 * Represents an abstract task.
 */
public abstract class Task {

    /**
     * A string representing the completed status of a task in storage.
     */
    public static final String STATUS_DONE_STORAGE = "1";

    /**
     * A string representing the incomplete status of a task in storage.
     */
    public static final String STATUS_NOT_DONE_STORAGE = "0";

    /**
     * A string representing the completed status of a task for display.
     */
    private static final String STATUS_DONE_DISPLAY = "X";

    /**
     * A string representing the incomplete status of a task for display.
     */
    private static final String STATUS_NOT_DONE_DISPLAY = " ";

    /**
     * The name of the task.
     */
    private final String name;

    /**
     * A unique signature representing the type of task.
     */
    private final String signature;

    /**
     * The completion status of the task.
     */
    private boolean isDone;

    /**
     * The list of tags associated with the task.
     */
    private final List<String> tags;

    /**
     * Constructs a new {@code Task} with the specified name, signature, completion status, and
     * tags.
     *
     * @param name      The name of the task.
     * @param signature The unique signature of the task.
     * @param isDone    The completion status of the task.
     * @param tags      The list of tags associated with the task.
     */
    public Task(String name, String signature, boolean isDone, List<String> tags) {
        this.name = name;
        this.signature = signature;
        this.isDone = isDone;
        this.tags = tags;
    }

    /**
     * Constructs a new {@code Task} with the specified name and signature.
     *
     * @param name      The name of the task.
     * @param signature The unique signature of the task.
     */
    public Task(String name, String signature) {
        this.name = name;
        isDone = false;
        this.signature = signature;
        this.tags = new ArrayList<>();
    }

    /**
     * Returns a string representation of the task, including its type, completion status, and
     * name.
     *
     * @return A string representation of the task.
     */
    @Override
    public String toString() {
        return "[" + signature + "]"
                + "[" + (isDone ? STATUS_DONE_DISPLAY : STATUS_NOT_DONE_DISPLAY) + "]"
                + "[" + (tags.isEmpty() ? " " : String.join(", ", tags)) + "]\n"
                + name;
    }

    /**
     * Gets the name of the task.
     *
     * @return The name of the task.
     */
    public String getName() {
        return name;
    }

    /**
     * Checks if the task contains the specified tag.
     *
     * @param tag The tag to check for.
     * @return {@code true} if the tag exists in the task, otherwise {@code false}.
     */
    public boolean hasTag(String tag) {
        return tags.contains(tag);
    }

    /**
     * Marks the task as completed.
     *
     * @throws InvalidValueException If the task is already marked as completed.
     */
    public void mark() throws InvalidValueException {
        if (isDone) {
            throw new InvalidValueException(String.format(Messages.ALREADY_MARKED, name));
        }
        isDone = true;
    }

    /**
     * Marks the task as not completed.
     *
     * @throws InvalidValueException If the task is already marked as not completed.
     */
    public void unmark() throws InvalidValueException {
        if (!isDone) {
            throw new InvalidValueException(String.format(Messages.ALREADY_UNMARKED, name));
        }
        isDone = false;
    }

    /**
     * Checks if the task is marked as completed.
     *
     * @return {@code true} if the task is marked as completed, {@code false} otherwise.
     */
    public boolean isMarked() {
        return isDone;
    }

    /**
     * Adds a tag to the task.
     *
     * @param tags The list of tags to add.
     * @throws InvalidValueException If any of the tags already exist.
     */
    public void addTags(List<String> tags) throws InvalidValueException {
        for (String tag: tags) {
            if (this.tags.contains(tag)) {
                throw new InvalidValueException(String.format(Messages.TAG_ALREADY_EXISTED, tag, name));
            }
            this.tags.add(tag);
        }
    }

    /**
     * Removes a tag from the task.
     *
     * @param tags The list of tags to remove.
     * @throws InvalidValueException If any of the tags do not exist.
     */
    public void removeTags(List<String> tags) throws InvalidValueException {
        for (String tag: tags) {
            if (!this.tags.contains(tag)) {
                throw new InvalidValueException(String.format(Messages.TAG_NOT_FOUND, tag, name));
            }
            this.tags.remove(tag);
        }
    }

    /**
     * Encodes the task into a string representation suitable for storage, including type,
     * completion status, and name. Appends additional encoded information from the subclass
     * implementation.
     *
     * @return An encoded string representation of the task.
     */
    public String encode() {
        return signature + "|"
                + (isDone ? STATUS_DONE_STORAGE : STATUS_NOT_DONE_STORAGE) + "|"
                + String.join(",", tags) + "|"
                + name + "|"
                + String.join(",", encodedAddedInfo());
    }

    /**
     * Provides additional encoding details specific to the subclass.
     *
     * @return A list of additional encoded strings.
     */
    public abstract List<String> encodedAddedInfo();
}
