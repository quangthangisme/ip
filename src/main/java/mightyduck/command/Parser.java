package mightyduck.command;

import java.util.List;

import mightyduck.command.commands.ByeCommand;
import mightyduck.command.commands.DeadlineCommand;
import mightyduck.command.commands.DeleteCommand;
import mightyduck.command.commands.EventCommand;
import mightyduck.command.commands.FindCommand;
import mightyduck.command.commands.ListCommand;
import mightyduck.command.commands.MarkCommand;
import mightyduck.command.commands.ToDoCommand;
import mightyduck.command.commands.UnmarkCommand;
import mightyduck.data.task.TaskManager;
import mightyduck.exception.InvalidCommandException;
import mightyduck.messages.Messages;

/**
 * The {@code Parser} class is responsible for interpreting user input and converting it into
 * executable {@link Command} objects.
 */
public class Parser {

    /**
     * The {@link TaskManager} used for managing tasks.
     */
    private final TaskManager taskManager;

    /**
     * Constructs a {@code Parser} with the given {@link TaskManager}.
     *
     * @param taskManager The {@link TaskManager} to be used for managing tasks.
     */
    public Parser(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    /**
     * Parses the user input and converts it into a {@link Command} object.
     *
     * @param input The raw user input as a {@link String}.
     * @return The corresponding {@link Command} object.
     * @throws InvalidCommandException If the input is invalid or does not match any known command.
     */
    public Command parse(String input) throws InvalidCommandException {
        String[] parts = input.trim().split(" ", 2);
        if (parts.length == 0) {
            throw new InvalidCommandException(Messages.INVALID_COMMAND);
        }
        String commandStr = parts[0];
        String argumentsStr = parts.length > 1 ? parts[1] : "";

        switch (commandStr) {
        case ByeCommand.COMMAND_WORD:
            return new ByeCommand(taskManager);
        case ListCommand.COMMAND_WORD:
            return new ListCommand(taskManager);
        case MarkCommand.COMMAND_WORD: {
            int index = parseIndex(argumentsStr);
            return new MarkCommand(taskManager, index);
        }
        case UnmarkCommand.COMMAND_WORD: {
            int index = parseIndex(argumentsStr);
            return new UnmarkCommand(taskManager, index);
        }
        case DeleteCommand.COMMAND_WORD: {
            int index = parseIndex(argumentsStr);
            return new DeleteCommand(taskManager, index);
        }
        case ToDoCommand.COMMAND_WORD: {
            String[] todoArguments = parseArguments(argumentsStr, ToDoCommand.COMMAND_FORMAT,
                    ToDoCommand.KEYWORDS);
            return new ToDoCommand(taskManager, todoArguments);
        }
        case DeadlineCommand.COMMAND_WORD: {
            String[] dlArguments = parseArguments(argumentsStr, DeadlineCommand.COMMAND_FORMAT,
                    DeadlineCommand.KEYWORDS);
            return new DeadlineCommand(taskManager, dlArguments);
        }
        case EventCommand.COMMAND_WORD: {
            String[] eventArguments = parseArguments(argumentsStr, EventCommand.COMMAND_FORMAT,
                    EventCommand.KEYWORDS);
            return new EventCommand(taskManager, eventArguments);
        }
        case FindCommand.COMMAND_WORD: {
            String[] findArguments = parseArguments(argumentsStr, FindCommand.COMMAND_FORMAT,
                    FindCommand.KEYWORDS);
            return new FindCommand(taskManager, findArguments);
        }
        default:
            throw new InvalidCommandException(Messages.INVALID_COMMAND);
        }
    }

    /**
     * Parses the provided argument string to extract an index.
     *
     * @param argumentsStr The string containing the index argument.
     * @return The zero-based index parsed from the argument string.
     * @throws InvalidCommandException If the argument is not a valid number.
     */
    private int parseIndex(String argumentsStr) throws InvalidCommandException {
        try {
            return Integer.parseInt(argumentsStr.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidCommandException(Messages.WRONG_NUMBER_FORMAT);
        }
    }

    /**
     * Parses the arguments string for a command, extracting arguments based on the specified format
     * and keywords.
     *
     * @param argumentsStr The string containing all arguments for the command.
     * @param format       The expected format of the command.
     * @param keywords     A list of keywords used to delimit arguments.
     * @return An array of extracted arguments.
     * @throws InvalidCommandException If the arguments do not match the expected format.
     */
    private String[] parseArguments(String argumentsStr, String format, List<String> keywords)
            throws InvalidCommandException {
        String[] result = new String[keywords.size() + 1];
        int currentIndex = 0;

        for (int i = 0; i < keywords.size(); i++) {
            int nextIndex = argumentsStr.indexOf(keywords.get(i));
            if (nextIndex == -1) {
                throw new InvalidCommandException(
                        String.format(Messages.WRONG_COMMAND_FORMAT, format));
            }
            String argument = argumentsStr.substring(currentIndex, nextIndex).trim();
            if (argument.isEmpty()) {
                throw new InvalidCommandException(
                        String.format(Messages.WRONG_COMMAND_FORMAT, format));
            }
            result[i] = argument;
            currentIndex = nextIndex + keywords.get(i).length();
        }
        String argument = argumentsStr.substring(currentIndex).trim();
        if (argument.isEmpty()) {
            throw new InvalidCommandException(String.format(Messages.WRONG_COMMAND_FORMAT, format));
        }
        result[keywords.size()] = argumentsStr.substring(currentIndex).trim();

        return result;
    }
}
