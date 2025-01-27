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
        this.isDone = false;
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
        return "[" + this.signature + "][" + (this.isDone ? "X" : " ") + "] " + this.name;
    }

    /**
     * Gets the name of the task.
     *
     * @return The name of the task.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Marks the task as completed.
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Checks if the task is marked as completed.
     *
     * @return {@code true} if the task is marked as completed, {@code false} otherwise.
     */
    public boolean isMarked() {
        return this.isDone;
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
        encodedTaskBuilder.append(this.signature);
        encodedTaskBuilder.append("\t").append(this.isDone ? 1 : 0);
        encodedTaskBuilder.append("\t").append(this.name);
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
