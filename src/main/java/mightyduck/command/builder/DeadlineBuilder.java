package mightyduck.command.builder;

import static mightyduck.utils.DateTimeUtils.FORMATTER;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

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
    public static final String COMMAND_FORMAT = "deadline <name> /by <time>";

    /**
     * The keyword of the "deadline" command.
     */
    private static final String KEYWORD = "/by";

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
        String[] parts = input.split(KEYWORD, 2);
        if (parts[0].trim().isEmpty() || parts.length != 2) {
            throw new InvalidCommandException(
                    String.format(Messages.WRONG_COMMAND_FORMAT, COMMAND_FORMAT));
        }

        String taskName = parts[0].trim();
        String deadlineTimeStr = parts[1].trim();
        LocalDateTime dlTime;
        try {
            dlTime = LocalDateTime.parse(deadlineTimeStr, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidValueException(Messages.FAILED_PARSE_TIME);
        }

        return new DeadlineCommand(taskManager, taskName, dlTime);
    }
}
