package mightyduck.data.task.type;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import mightyduck.data.task.Task;
import mightyduck.exception.InvalidValueException;
import mightyduck.messages.Messages;

/**
 * Represents an event task with a specific name and deadline time. The task is identified with the
 * signature "E".
 */
public class Event extends Task {

    /**
     * The signature representing an event task.
     */
    public static final String SIGNATURE = "E";

    /**
     * The date-time formatter for parsing and displaying start and end times.
     */
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * The start time of the event.
     */
    private final LocalDateTime startTime;

    /**
     * The end time of the event.
     */
    private final LocalDateTime endTime;

    /**
     * Constructs a new {@code Event} task with the specified name, start time, and end time. The
     * times should be in the format "yyyy-MM-dd HH:mm".
     *
     * @param name      The name of the task.
     * @param startTime The start time of the task as a string.
     * @param endTime   The end time of the task as a string.
     * @throws InvalidValueException if the times cannot be parsed correctly.
     */
    public Event(String name, String startTime, String endTime) throws InvalidValueException {
        super(name, SIGNATURE);
        try {
            this.startTime = LocalDateTime.parse(startTime, formatter);
            this.endTime = LocalDateTime.parse(endTime, formatter);
            if (this.endTime.isBefore(this.startTime)) {
                throw new InvalidValueException(Messages.END_TIME_BEFORE_START_TIME);
            }
        } catch (DateTimeParseException e) {
            throw new InvalidValueException(Messages.FAILED_PARSE_TIME);
        }
    }

    /**
     * Returns a string representation of the event task, including the task's completion status,
     * name, and formatted start and end times.
     *
     * @return A string representation of the event task.
     */
    public String toString() {
        return super.toString()
                + " (from "
                + this.startTime.format(formatter)
                + " to "
                + this.endTime.format(formatter)
                + ")";
    }

    /**
     * Encodes the event task into a string representation suitable for storage
     *
     * @return A list containing the encoded start and end time as a string.
     */
    @Override
    public List<String> encodedAddedInfo() {
        return List.of(this.startTime.format(formatter), this.endTime.format(formatter));
    }
}
