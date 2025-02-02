package mightyduck.data.task.type;

import static mightyduck.utils.DateTimeUtils.FORMATTER;

import java.time.LocalDateTime;
import java.util.List;

import mightyduck.data.task.Task;

/**
 * Represents a deadline task with a specific name and deadline time. The task is identified with
 * the signature "D".
 */
public class Deadline extends Task {

    /**
     * The signature representing a deadline task.
     */
    public static final String SIGNATURE = "D";

    /**
     * The deadline time of the task.
     */
    private final LocalDateTime deadline;

    /**
     * Constructs a new {@code Deadline} task with the specified name and deadline time.
     *
     * @param name     The name of the task.
     * @param deadline The deadline time of the task.
     */
    public Deadline(String name, LocalDateTime deadline) {
        super(name, SIGNATURE);
        this.deadline = deadline;
    }

    /**
     * Returns a string representation of the deadline task, including the task's completion status,
     * name, and formatted deadline time.
     *
     * @return A string representation of the deadline task.
     */
    @Override
    public String toString() {
        return super.toString() + " (by " + deadline.format(FORMATTER) + ")";
    }

    /**
     * Encodes the deadline task into a string representation suitable for storage
     *
     * @return A list containing the encoded deadline time as a string.
     */
    @Override
    public List<String> encodedAddedInfo() {
        return List.of(deadline.format(FORMATTER));
    }
}
