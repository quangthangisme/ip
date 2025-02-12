package mightyduck.parser;

import static mightyduck.utils.DateTimeUtils.FORMATTER;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mightyduck.command.EventCommand;
import mightyduck.exception.InvalidCommandException;
import mightyduck.exception.InvalidValueException;
import mightyduck.task.TaskManager;
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
    public static final String COMMAND_FORMAT = "event <name> /from <time> /to <time> "
            + "[/tags <tag1> <tag2> ...]";

    /**
     * The keyword indicating the start time.
     */
    private static final String FROM_KEYWORD = "/from";

    /**
     * The keyword indicating the end time.
     */
    private static final String TO_KEYWORD = "/to";

    /**
     * The keyword indicating the tags.
     */
    private static final String TAGS_KEYWORD = "/tags";

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
        String[] parsedInput = splitInput(input);
        String taskName = parsedInput[0].trim();
        String remaining = parsedInput[1].trim();

        String[] timeAndTags = extractTimeAndTags(remaining);
        String timeStr = timeAndTags[0];
        List<String> tags = timeAndTags.length > 1
                ? Arrays.asList(timeAndTags[1].trim().split("\\s+"))
                : new ArrayList<>();
        Validation.validateTags(tags);

        String[] timeParts = splitTime(timeStr);
        LocalDateTime startTime = parseTime(timeParts[0]);
        LocalDateTime endTime = parseTime(timeParts[1]);
        validateTimeOrder(startTime, endTime);

        return new EventCommand(taskManager, taskName, startTime, endTime, tags);
    }

    /**
     * Splits the input string by the first keyword and validates the format.
     *
     * @param input The input string to be split.
     * @return An array of strings containing the task name and time part.
     * @throws InvalidCommandException If the input format is incorrect.
     */
    private String[] splitInput(String input) throws InvalidCommandException {
        String[] parts = input.split(FROM_KEYWORD, 2);
        if (parts.length != 2 || parts[0].trim().isEmpty()) {
            throw new InvalidCommandException(String.format(Messages.WRONG_COMMAND_FORMAT,
                    COMMAND_FORMAT));
        }
        return new String[]{parts[0].trim(), parts[1].trim()};
    }

    /**
     * Splits the time and tags portion of the input string.
     *
     * @param remaining The time and tags portion of the input string to be split.
     * @return An array containing the time portion and tags portion as strings.
     */
    private String[] extractTimeAndTags(String remaining) {
        return remaining.contains(TAGS_KEYWORD)
                ? remaining.split(TAGS_KEYWORD, 2) : new String[]{remaining};
    }

    /**
     * Splits the time portion of the input string.
     *
     * @param timeStr The time portion of the input string to be split.
     * @return An array containing the start time and end time as strings.
     * @throws InvalidCommandException If the time format is incorrect.
     */
    private String[] splitTime(String timeStr) throws InvalidCommandException {
        String[] timeParts = timeStr.split(TO_KEYWORD, 2);
        if (timeParts.length != 2) {
            throw new InvalidCommandException(String.format(Messages.WRONG_COMMAND_FORMAT,
                    COMMAND_FORMAT));
        }
        return timeParts;
    }

    /**
     * Parses the given time string into a {@link LocalDateTime} object.
     *
     * @param time The time string to be parsed.
     * @return The {@link LocalDateTime} representation of the time string.
     * @throws InvalidValueException If the time string is not in a valid format.
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
     * @param startTime The start time of the event.
     * @param endTime   The end time of the event.
     * @throws InvalidValueException If the end time is before the start time.
     */
    private void validateTimeOrder(LocalDateTime startTime, LocalDateTime endTime)
            throws InvalidValueException {
        if (endTime.isBefore(startTime)) {
            throw new InvalidValueException(Messages.END_TIME_BEFORE_START_TIME);
        }
    }
}
