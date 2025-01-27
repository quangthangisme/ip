package mightyduck.data.task.type;

import java.util.List;

import mightyduck.data.task.Task;

/**
 * Represents a to-do task with a specific name. The task is identified with the signature "T".
 */
public class ToDo extends Task {

    /**
     * The signature representing a to-do task.
     */
    public static final String SIGNATURE = "T";

    /**
     * Constructs a new {@code ToDo} task with the specified name..
     *
     * @param name The name of the task.
     */
    public ToDo(String name) {
        super(name, SIGNATURE);
    }

    /**
     * Encodes the to-do task into a string representation suitable for storage
     *
     * @return An empty list as the to-do task contains no additional information.
     */
    @Override
    public List<String> encodedAddedInfo() {
        return List.of();
    }
}
