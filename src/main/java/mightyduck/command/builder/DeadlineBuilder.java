package mightyduck.command.builder;

import static mightyduck.utils.DateTimeUtils.FORMATTER;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import mightyduck.command.command.DeadlineCommand;
import mightyduck.data.task.TaskManager;
import mightyduck.exception.InvalidCommandException;
import mightyduck.exception.InvalidValueException;
import mightyduck.utils.Messages;

/**
 * A builder class for constructing a {@link DeadlineCommand} from user input.
 */
public class DeadlineBuilder extends Builder {

    /**
     * The command word used to invoke the "deadline" command.
     */
    public static final String COMMAND_WORD = "deadline";

    /**
     * The format of the "deadline" command.
     */
    public static final String COMMAND_FORMAT =
            "deadline <name> /by <time> [/tags <tag1> <tag2> ...]";

    /**
     * The keyword indicating the deadline time.
     */
    private static final String BY_KEYWORD = "/by";

    /**
     * The keyword indicating the tags.
     */
    private static final String TAGS_KEYWORD = "/tags";

    /**
     * Constructs a new {@link DeadlineBuilder} with the specified {@link TaskManager}.
     *
     * @param taskManager The {@link TaskManager} instance that manages tasks.
     */
    public DeadlineBuilder(TaskManager taskManager) {
        super(taskManager);
    }

    /**
     * Creates a {@code DeadlineCommand} from user-provided input.
     *
     * @param input The user-provided input string, expected to contain the task name and deadline
     *              time in the format "task_name /by yyyy-MM-dd HH:mm".
     * @return A new {@code DeadlineCommand} instance.
     * @throws InvalidCommandException If the arguments are missing, incorrectly formatted.
     * @throws InvalidValueException   If the deadline time is invalid.
     */
    public DeadlineCommand fromInput(String input)
            throws InvalidValueException, InvalidCommandException {
        String[] parsedInput = parseTaskAndRemainingInput(input);
        String taskName = parsedInput[0];
        String remaining = parsedInput[1];

        String[] deadlineAndTags = extractDeadlineAndTags(remaining);
        LocalDateTime deadline = parseDeadline(deadlineAndTags[0]);
        List<String> tags = deadlineAndTags.length > 1
                ? new ArrayList<>(
                        new HashSet<>(Arrays.asList(deadlineAndTags[1].trim().split("\\s+"))))
                : new ArrayList<>();

        return new DeadlineCommand(taskManager, taskName, deadline, tags);
    }

    /**
     * Splits the input string by the first keyword and validates the format.
     *
     * @param input The input string to be split.
     * @return An array of strings containing the task name and time part.
     * @throws InvalidCommandException If the input format is incorrect.
     */
    private String[] parseTaskAndRemainingInput(String input) throws InvalidCommandException {
        String[] byParts = input.split(BY_KEYWORD, 2);
        if (byParts.length != 2 || byParts[0].trim().isEmpty()) {
            throw new InvalidCommandException(
                    String.format(Messages.WRONG_COMMAND_FORMAT, COMMAND_FORMAT));
        }
        return new String[]{byParts[0].trim(), byParts[1].trim()};
    }

    /**
     * Splits the time and tags portion of the input string.
     *
     * @param remaining The time and tags portion of the input string to be split.
     * @return An array containing the time portion and tags portion as strings.
     */
    private String[] extractDeadlineAndTags(String remaining) {
        return remaining.contains(TAGS_KEYWORD)
                ? remaining.split(TAGS_KEYWORD, 2) : new String[]{remaining};
    }

    /**
     * Parses the given time string into a {@link LocalDateTime} object.
     *
     * @param deadlineTimeStr The time string to be parsed.
     * @return The {@link LocalDateTime} representation of the time string.
     * @throws InvalidValueException If the time string is not in a valid format.
     */
    private LocalDateTime parseDeadline(String deadlineTimeStr) throws InvalidValueException {
        try {
            return LocalDateTime.parse(deadlineTimeStr.trim(), FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidValueException(Messages.FAILED_PARSE_TIME);
        }
    }
}
