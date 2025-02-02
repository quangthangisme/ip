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
     * @param input       The user-provided input string, expected to contain the task name, the
     *                    start time, and the end time in the format "task_name /by yyyy-MM-dd
     *                    HH:mm".
     * @return A new {@code EventCommand} instance.
     * @throws InvalidCommandException If the arguments are missing, incorrectly formatted.
     * @throws InvalidValueException   If the start or end time is invalid.
     */
    public EventCommand fromInput(String input)
            throws InvalidValueException, InvalidCommandException {
        String[] parts = input.split(KEYWORDS.get(0), 2);
        if (parts[0].trim().isEmpty() || parts.length != 2) {
            throw new InvalidCommandException(
                    String.format(Messages.WRONG_COMMAND_FORMAT, COMMAND_FORMAT));
        }

        String taskName = parts[0].trim();
        String timeStr = parts[1].trim();

        String[] timeParts = timeStr.split(KEYWORDS.get(1), 2);
        if (timeParts.length != 2) {
            throw new InvalidCommandException(
                    String.format(Messages.WRONG_COMMAND_FORMAT, COMMAND_FORMAT));
        }

        LocalDateTime eTime;
        LocalDateTime sTime;
        try {
            sTime = LocalDateTime.parse(timeParts[0].trim(), FORMATTER);
            eTime = LocalDateTime.parse(timeParts[1].trim(), FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidValueException(Messages.FAILED_PARSE_TIME);
        }

        if (eTime.isBefore(sTime)) {
            throw new InvalidValueException(Messages.END_TIME_BEFORE_START_TIME);
        }

        return new EventCommand(taskManager, taskName, sTime, eTime);
    }
}
