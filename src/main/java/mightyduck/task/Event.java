package mightyduck.task;

import static mightyduck.utils.DateTimeUtils.FORMATTER;

import java.time.LocalDateTime;
import java.util.List;

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
     * The start time of the event.
     */
    private final LocalDateTime startTime;

    /**
     * The end time of the event.
     */
    private final LocalDateTime endTime;

    /**
     * Constructs a new {@code Event} task with the specified name, completion status, start time,
     * end time, and tags.
     *
     * @param name      The name of the task.
     * @param isDone    The completion status of the task.
     * @param startTime The start time of the task.
     * @param endTime   The end time of the task.
     * @param tags      The list of tags associated with the task.
     */
    public Event(String name, boolean isDone, LocalDateTime startTime, LocalDateTime endTime,
                 List<String> tags) {
        super(name, SIGNATURE, isDone, tags);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Constructs a new {@code Event} task with the specified name, start time, end time, and tags.
     *
     * @param name      The name of the task.
     * @param startTime The start time of the task.
     * @param endTime   The end time of the task.
     * @param tags      The list of tags associated with the task.
     */
    public Event(String name, LocalDateTime startTime, LocalDateTime endTime, List<String> tags) {
        super(name, SIGNATURE, false, tags);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Constructs a new {@code Event} task with the specified name, start time, and end time.
     *
     * @param name      The name of the task.
     * @param startTime The start time of the task.
     * @param endTime   The end time of the task.
     */
    public Event(String name, LocalDateTime startTime, LocalDateTime endTime) {
        super(name, SIGNATURE);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Returns a string representation of the event task, including the task's completion status,
     * name, and formatted start and end times.
     *
     * @return A string representation of the event task.
     */
    public String toString() {
        return super.toString() + " (from " + startTime.format(FORMATTER)
                + " to " + endTime.format(FORMATTER) + ")";
    }

    /**
     * Encodes the event task into a string representation suitable for storage
     *
     * @return A list containing the encoded start and end time as a string.
     */
    @Override
    public List<String> encodedAddedInfo() {
        return List.of(startTime.format(FORMATTER), endTime.format(FORMATTER));
    }
}
