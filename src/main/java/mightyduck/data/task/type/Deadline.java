package mightyduck.data.task.type;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import mightyduck.data.task.Task;
import mightyduck.exception.InvalidValueException;
import mightyduck.messages.Messages;

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
     * The date-time formatter for parsing and displaying deadline times.
     */
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * The deadline time of the task.
     */
    private final LocalDateTime deadline;

    /**
     * Constructs a new {@code Deadline} task with the specified name and deadline time. The
     * deadline time should be in the format "yyyy-MM-dd HH:mm".
     *
     * @param name     The name of the task.
     * @param deadline The deadline time of the task as a string.
     * @throws InvalidValueException if the deadline cannot be parsed correctly.
     */
    public Deadline(String name, String deadline) throws InvalidValueException {
        super(name, SIGNATURE);
        try {
            this.deadline = LocalDateTime.parse(deadline, formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidValueException(Messages.FAILED_PARSE_TIME);
        }
    }

    /**
     * Returns a string representation of the deadline task, including the task's completion status,
     * name, and formatted deadline time.
     *
     * @return A string representation of the deadline task.
     */
    @Override
    public String toString() {
        return super.toString() + " (by " + this.deadline.format(formatter) + ")";
    }

    /**
     * Encodes the deadline task into a string representation suitable for storage
     *
     * @return A list containing the encoded deadline time as a string.
     */
    @Override
    public List<String> encodedAddedInfo() {
        return List.of(this.deadline.format(formatter));
    }
}
