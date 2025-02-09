package mightyduck.command.builder;

import static mightyduck.utils.DateTimeUtils.FORMATTER;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import mightyduck.command.command.EventCommand;
import mightyduck.data.task.TaskManager;
import mightyduck.exception.InvalidCommandException;
import mightyduck.exception.InvalidValueException;
import mightyduck.utils.Messages;

/**
 * A builder class for constructing an {@link EventCommand} from user input.
 */
public class EventBuilder extends Builder {

    /**
     * The command word used to invoke the "event" command.
     */
    public static final String COMMAND_WORD = "event";

    /**
     * The format of the "event" command.
     */
    public static final String COMMAND_FORMAT = "event <name> /from <time> /to <time>";

    /**
     * The keywords of the "event" command.
     */
    private static final List<String> KEYWORDS = List.of("/from", "/to");

    /**
     * Constructs a new {@link EventBuilder} with the specified {@link TaskManager}.
     *
     * @param taskManager The {@link TaskManager} instance that manages tasks.
     */
    public EventBuilder(TaskManager taskManager) {
        super(taskManager);
    }

    /**
     * Creates a {@code EventCommand} from user-provided input.
     *
     * @param input The user-provided input string, expected to contain the task name, the start
     *              time, and the end time in the format "task_name /by yyyy-MM-dd HH:mm".
     * @return A new {@code EventCommand} instance.
     * @throws InvalidCommandException If the arguments are missing, incorrectly formatted.
     * @throws InvalidValueException   If the start or end time is invalid.
     */
    public EventCommand fromInput(String input) throws InvalidValueException,
            InvalidCommandException {
        String[] parts = splitInput(input);
        String taskName = parts[0].trim();
        String timeStr = parts[1].trim();

        String[] timeParts = splitTime(timeStr);
        LocalDateTime startTime = parseTime(timeParts[0]);
        LocalDateTime endTime = parseTime(timeParts[1]);
        validateTimeOrder(startTime, endTime);

        return new EventCommand(taskManager, taskName, startTime, endTime);
    }

    /**
     * Splits the input string by the first keyword and validates the format.
     *
     * @param input the input string to be split
     * @return an array of strings containing the task name and time part
     * @throws InvalidCommandException if the input format is incorrect
     */
    private String[] splitInput(String input) throws InvalidCommandException {
        String[] parts = input.split(KEYWORDS.get(0), 2);
        if (parts[0].trim().isEmpty() || parts.length != 2) {
            throw new InvalidCommandException(
                    String.format(Messages.WRONG_COMMAND_FORMAT, COMMAND_FORMAT));
        }
        return parts;
    }

    /**
     * Splits the time portion of the input string by the second keyword.
     *
     * @param timeStr the time portion of the input string to be split
     * @return an array containing the start time and end time as strings
     * @throws InvalidCommandException if the time format is incorrect
     */
    private String[] splitTime(String timeStr) throws InvalidCommandException {
        String[] timeParts = timeStr.split(KEYWORDS.get(1), 2);
        if (timeParts.length != 2) {
            throw new InvalidCommandException(
                    String.format(Messages.WRONG_COMMAND_FORMAT, COMMAND_FORMAT));
        }
        return timeParts;
    }

    /**
     * Parses the given time string into a LocalDateTime object.
     *
     * @param time the time string to be parsed
     * @return the LocalDateTime representation of the time string
     * @throws InvalidValueException if the time string is not in a valid format
     */
    private LocalDateTime parseTime(String time) throws InvalidValueException {
        try {
            return LocalDateTime.parse(time.trim(), FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidValueException(Messages.FAILED_PARSE_TIME);
        }
    }

    /**
     * Validates that the end time is not before the start time.
     *
     * @param startTime the start time of the event
     * @param endTime   the end time of the event
     * @throws InvalidValueException if the end time is before the start time
     */
    private void validateTimeOrder(LocalDateTime startTime, LocalDateTime endTime)
            throws InvalidValueException {
        if (endTime.isBefore(startTime)) {
            throw new InvalidValueException(Messages.END_TIME_BEFORE_START_TIME);
        }
    }
}
