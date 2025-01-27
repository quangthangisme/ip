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

public class Parser {
    private final TaskManager taskManager;

    public Parser(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public Command parse(String input) throws InvalidCommandException {
        String[] parts = input.trim().split(" ", 2);
        if (parts.length == 0) {
            throw new InvalidCommandException(Messages.INVALID_COMMAND);
        }
        String commandStr = parts[0];
        String argumentsStr = parts.length > 1 ? parts[1] : "";

        switch (commandStr) {
        case ByeCommand.COMMAND_WORD:
            return new ByeCommand(this.taskManager);
        case ListCommand.COMMAND_WORD:
            return new ListCommand(this.taskManager);
        case MarkCommand.COMMAND_WORD: {
            int index = parseIndex(argumentsStr);
            return new MarkCommand(this.taskManager, index);
        }
        case UnmarkCommand.COMMAND_WORD: {
            int index = parseIndex(argumentsStr);
            return new UnmarkCommand(this.taskManager, index);
        }
        case DeleteCommand.COMMAND_WORD: {
            int index = parseIndex(argumentsStr);
            return new DeleteCommand(this.taskManager, index);
        }
        case ToDoCommand.COMMAND_WORD: {
            String[] todoArguments = parseArguments(argumentsStr, ToDoCommand.COMMAND_FORMAT,
                    ToDoCommand.KEYWORDS);
            return new ToDoCommand(this.taskManager, todoArguments);
        }
        case DeadlineCommand.COMMAND_WORD: {
            String[] dlArguments = parseArguments(argumentsStr, DeadlineCommand.COMMAND_FORMAT,
                    DeadlineCommand.KEYWORDS);
            return new DeadlineCommand(this.taskManager, dlArguments);
        }
        case EventCommand.COMMAND_WORD: {
            String[] eventArguments = parseArguments(argumentsStr, EventCommand.COMMAND_FORMAT,
                    EventCommand.KEYWORDS);
            return new EventCommand(this.taskManager, eventArguments);
        }
        case FindCommand.COMMAND_WORD: {
            String[] findArguments = parseArguments(argumentsStr, FindCommand.COMMAND_FORMAT,
                    FindCommand.KEYWORDS);
            return new FindCommand(this.taskManager, findArguments);
        }
        default:
            throw new InvalidCommandException(Messages.INVALID_COMMAND);
        }
    }

    private int parseIndex(String argumentsStr) throws InvalidCommandException {
        try {
            return Integer.parseInt(argumentsStr.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidCommandException(Messages.WRONG_NUMBER_FORMAT);
        }
    }

    private String[] parseArguments(String argumentsStr, String format,
                                    List<String> keywords)
            throws InvalidCommandException {
        String[] result = new String[keywords.size() + 1];
        int currentIndex = 0;

        for (int i = 0; i < keywords.size(); i++) {
            int nextIndex = argumentsStr.indexOf(keywords.get(i));
            if (nextIndex == -1) {
                throw new InvalidCommandException(
                        String.format(Messages.WRONG_COMMAND_FORMAT, format));
            }
            String argument = argumentsStr.substring(currentIndex, nextIndex)
                    .trim();
            if (argument.isEmpty()) {
                throw new InvalidCommandException(
                        String.format(Messages.WRONG_COMMAND_FORMAT, format));
            }
            result[i] = argument;
            currentIndex = nextIndex + keywords.get(i).length();
        }
        String argument = argumentsStr.substring(currentIndex).trim();
        if (argument.isEmpty()) {
            throw new InvalidCommandException(
                    String.format(Messages.WRONG_COMMAND_FORMAT, format));
        }
        result[keywords.size()] = argumentsStr.substring(currentIndex).trim();

        return result;
    }
}
