package mightyduck.data.task.type;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import mightyduck.data.task.Task;
import mightyduck.exception.InvalidValueException;
import mightyduck.messages.Messages;

public class Deadline extends Task {
    public static final String SIGNATURE = "D";
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final LocalDateTime deadline;

    public Deadline(String name, String deadline)
            throws InvalidValueException {
        super(name, SIGNATURE);
        try {
            this.deadline = LocalDateTime.parse(deadline, formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidValueException(Messages.FAILED_PARSE_TIME);
        }
    }

    public String toString() {
        return super.toString()
                + " (by " + this.deadline.format(formatter) + ")";
    }

    @Override
    public List<String> encodedAddedInfo() {
        return List.of(this.deadline.format(formatter));
    }
}
