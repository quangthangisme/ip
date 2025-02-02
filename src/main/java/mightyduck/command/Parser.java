package mightyduck.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mightyduck.command.builder.Builder;
import mightyduck.command.builder.ByeBuilder;
import mightyduck.command.builder.DeadlineBuilder;
import mightyduck.command.builder.DeleteBuilder;
import mightyduck.command.builder.EventBuilder;
import mightyduck.command.builder.FindBuilder;
import mightyduck.command.builder.ListBuilder;
import mightyduck.command.builder.MarkBuilder;
import mightyduck.command.builder.ToDoBuilder;
import mightyduck.command.builder.UnmarkBuilder;
import mightyduck.command.command.Command;
import mightyduck.data.task.TaskManager;
import mightyduck.exception.InvalidCommandException;
import mightyduck.exception.InvalidValueException;
import mightyduck.utils.Messages;
import mightyduck.utils.Pair;

/**
 * The {@code Parser} class is responsible for interpreting user input and converting it into
 * executable {@link Command} objects.
 */
public class Parser {

    /**
     * A map of command words to their corresponding functions responsible for parsing user input
     * and generating the appropriate {@link Command} objects.
     */
    private final Map<String, ParsingFunc> commandMap;

    /**
     * Constructs a {@code Parser} with the given {@link TaskManager}. This constructor initializes
     * a map of command words (such as "bye", "delete", etc.) to their corresponding builder classes
     * responsible for parsing user input and generating the appropriate {@link Command} objects.
     *
     * @param taskManager The {@link TaskManager} to be used for managing tasks.
     */
    public Parser(TaskManager taskManager) {
        this.commandMap = new HashMap<>();

        List<Pair<String, Builder>> builders = List.of(
                new Pair<>(ByeBuilder.COMMAND_WORD, new ByeBuilder(taskManager)),
                new Pair<>(DeleteBuilder.COMMAND_WORD, new DeleteBuilder(taskManager)),
                new Pair<>(DeadlineBuilder.COMMAND_WORD, new DeadlineBuilder(taskManager)),
                new Pair<>(EventBuilder.COMMAND_WORD, new EventBuilder(taskManager)),
                new Pair<>(FindBuilder.COMMAND_WORD, new FindBuilder(taskManager)),
                new Pair<>(ListBuilder.COMMAND_WORD, new ListBuilder(taskManager)),
                new Pair<>(MarkBuilder.COMMAND_WORD, new MarkBuilder(taskManager)),
                new Pair<>(ToDoBuilder.COMMAND_WORD, new ToDoBuilder(taskManager)),
                new Pair<>(UnmarkBuilder.COMMAND_WORD, new UnmarkBuilder(taskManager))
        );

        for (Pair<String, Builder> builder : builders) {
            commandMap.put(builder.key(), builder.value()::fromInput);
        }
    }

    /**
     * Parses the user input and converts it into a {@link Command} object.
     *
     * @param input The raw user input as a {@link String}.
     * @return The corresponding {@link Command} object.
     * @throws InvalidCommandException If the input is invalid or does not match any known command.
     * @throws InvalidValueException   If the input contains invalid values.
     */
    public Command parse(String input) throws InvalidCommandException, InvalidValueException {
        String[] parts = input.trim().split(" ", 2);
        String commandStr = parts[0];
        String argumentsStr = parts.length > 1 ? parts[1] : "";

        ParsingFunc parsingFunc = commandMap.get(commandStr);
        if (parsingFunc == null) {
            throw new InvalidCommandException(Messages.INVALID_COMMAND);
        }
        return parsingFunc.apply(argumentsStr);
    }

    /**
     * A functional interface representing a function that parses user input and returns a
     * {@link Command}.
     */
    @FunctionalInterface
    private interface ParsingFunc {
        /**
         * Parses the given input string and returns the corresponding {@link Command}.
         *
         * @param str The string to parse, which contains the arguments after the command word.
         * @return The corresponding {@link Command} object created from the input.
         * @throws InvalidCommandException If the input is incorrectly formatted.
         * @throws InvalidValueException   If the input contains invalid values.
         */
        Command apply(String str) throws InvalidCommandException, InvalidValueException;
    }
}
