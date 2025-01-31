package mightyduck.data.task;

import java.util.List;

/**
 * Represents an abstract task.
 */
public abstract class Task {

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
     * Constructs a new {@code Task} with the specified name and signature.
     *
     * @param name      The name of the task.
     * @param signature The unique signature of the task.
     */
    public Task(String name, String signature) {
        this.name = name;
        isDone = false;
        this.signature = signature;
    }

    /**
     * Returns a string representation of the task, including its type, completion status, and
     * name.
     *
     * @return A string representation of the task.
     */
    @Override
    public String toString() {
        return "[" + signature + "][" + (isDone ? "X" : " ") + "] " + name;
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
     * Marks the task as completed.
     */
    public void mark() {
        isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void unmark() {
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
     * Encodes the task into a string representation suitable for storage, including type,
     * completion status, and name. Appends additional encoded information from the subclass
     * implementation.
     *
     * @return An encoded string representation of the task.
     */
    public String encode() {
        StringBuilder encodedTaskBuilder = new StringBuilder();
        encodedTaskBuilder.append(signature);
        encodedTaskBuilder.append("\t").append(isDone ? 1 : 0);
        encodedTaskBuilder.append("\t").append(name);
        encodedAddedInfo().forEach(info -> encodedTaskBuilder.append("\t").append(info));
        return encodedTaskBuilder.toString();
    }

    /**
     * Provides additional encoding details specific to the subclass.
     *
     * @return A list of additional encoded strings.
     */
    public abstract List<String> encodedAddedInfo();
}
