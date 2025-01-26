package mightyduck.data.task.type;

import mightyduck.data.task.Task;
import mightyduck.exception.InvalidValueException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class Event extends Task {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public static final String SIGNATURE = "E";
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Event(String name, String startTime, String endTime)
            throws InvalidValueException {
        super(name, SIGNATURE);
        try {
            this.startTime = LocalDateTime.parse(startTime, formatter);
            this.endTime = LocalDateTime.parse(endTime, formatter);
            if (this.endTime.isBefore(this.startTime)) {
                throw new InvalidValueException("The end time must be after " +
                        "the start time!");
            }
        } catch (DateTimeParseException e) {
            throw new InvalidValueException("Cannot parse the time format! " +
                    "Expected 'yyyy-MM-dd HH:mm'.");
        }
    }

    public String toString() {
        return super.toString()
                + " (from " + this.startTime.format(formatter)
                + " to " + this.endTime.format(formatter) + ")";
    }

    @Override
    public List<String> encodedAddedInfo() {
        return List.of(this.startTime.format(formatter),
                this.endTime.format(formatter));
    }
}