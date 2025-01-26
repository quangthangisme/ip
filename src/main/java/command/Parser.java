package command;

import command.commands.ByeCommand;
import command.commands.DeadlineCommand;
import command.commands.DeleteCommand;
import command.commands.EventCommand;
import command.commands.ListCommand;
import command.commands.MarkCommand;
import command.commands.ToDoCommand;
import command.commands.UnmarkCommand;
import data.TaskManager;
import exception.InvalidCommandException;
import messages.Messages;

import java.util.List;

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

        return switch (commandStr) {
            case ByeCommand.COMMAND_WORD -> new ByeCommand(this.taskManager);
            case ListCommand.COMMAND_WORD -> new ListCommand(this.taskManager);
            case MarkCommand.COMMAND_WORD -> {
                int index = parseIndex(argumentsStr);
                yield new MarkCommand(this.taskManager, index);
            }
            case UnmarkCommand.COMMAND_WORD -> {
                int index = parseIndex(argumentsStr);
                yield new UnmarkCommand(this.taskManager, index);
            }
            case DeleteCommand.COMMAND_WORD -> {
                int index = parseIndex(argumentsStr);
                yield new DeleteCommand(this.taskManager, index);
            }
            case ToDoCommand.COMMAND_WORD -> {
                String[] todoArguments = parseArguments(argumentsStr,
                        ToDoCommand.COMMAND_FORMAT, ToDoCommand.KEYWORDS);
                yield new ToDoCommand(this.taskManager, todoArguments);
            }
            case DeadlineCommand.COMMAND_WORD -> {
                String[] dlArguments = parseArguments(argumentsStr,
                        DeadlineCommand.COMMAND_FORMAT,
                        DeadlineCommand.KEYWORDS);
                yield new DeadlineCommand(this.taskManager, dlArguments);
            }
            case EventCommand.COMMAND_WORD -> {
                String[] eventArguments = parseArguments(argumentsStr,
                        EventCommand.COMMAND_FORMAT, EventCommand.KEYWORDS);
                yield new EventCommand(this.taskManager, eventArguments);
            }
            default ->
                    throw new InvalidCommandException(Messages.INVALID_COMMAND);
        };
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
