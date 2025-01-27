package mightyduck.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mightyduck.command.commands.ByeCommand;
import mightyduck.command.commands.DeadlineCommand;
import mightyduck.command.commands.DeleteCommand;
import mightyduck.command.commands.EventCommand;
import mightyduck.command.commands.ListCommand;
import mightyduck.command.commands.MarkCommand;
import mightyduck.command.commands.ToDoCommand;
import mightyduck.command.commands.UnmarkCommand;
import mightyduck.data.task.TaskManager;
import mightyduck.exception.InvalidCommandException;
import mightyduck.messages.Messages;

public class ParserTest {
    private Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Parser(new TaskManager());
    }

    @Test
    void parse_byeCommand_success() throws InvalidCommandException {
        Command command = parser.parse("bye");
        assertInstanceOf(ByeCommand.class, command);
    }

    @Test
    void parse_listCommand_success() throws InvalidCommandException {
        Command command = parser.parse("list");
        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    void parse_markCommand_success() throws InvalidCommandException {
        Command command = parser.parse("mark 1");
        assertInstanceOf(MarkCommand.class, command);
    }

    @Test
    void parse_unmarkCommand_success() throws InvalidCommandException {
        Command command = parser.parse("unmark 1");
        assertInstanceOf(UnmarkCommand.class, command);
    }

    @Test
    void parse_deleteCommand_success() throws InvalidCommandException {
        Command command = parser.parse("delete 1");
        assertInstanceOf(DeleteCommand.class, command);
    }

    @Test
    void parse_todoCommand_success() throws InvalidCommandException {
        Command command = parser.parse("todo read book");
        assertInstanceOf(ToDoCommand.class, command);
    }

    @Test
    void parse_deadlineCommand_success() throws InvalidCommandException {
        Command command = parser.parse("deadline project /by Sunday");
        assertInstanceOf(DeadlineCommand.class, command);
    }

    @Test
    void parse_eventCommand_success() throws InvalidCommandException {
        Command command = parser.parse("event meeting /from 10am /to 12pm");
        assertInstanceOf(EventCommand.class, command);
    }

    @Test
    void parse_invalidCommand_throwsException() {
        Exception exception = assertThrows(InvalidCommandException.class, () ->
                parser.parse("invalidCommand"));
        assertEquals(Messages.INVALID_COMMAND, exception.getMessage());
    }

    @Test
    void parse_emptyInput_throwsException() {
        Exception exception = assertThrows(InvalidCommandException.class, () ->
                parser.parse(" "));
        assertEquals(Messages.INVALID_COMMAND, exception.getMessage());
    }

    @Test
    void parse_markCommandMissingIndex_throwsException() {
        Exception exception = assertThrows(InvalidCommandException.class, () ->
                parser.parse("mark"));
        assertEquals(Messages.WRONG_NUMBER_FORMAT, exception.getMessage());
    }

    @Test
    void parse_unmarkCommandInvalidIndex_throwsException() {
        Exception exception = assertThrows(InvalidCommandException.class, () ->
                parser.parse("unmark abc"));
        assertEquals(Messages.WRONG_NUMBER_FORMAT, exception.getMessage());
    }

    @Test
    void parse_deleteCommandInvalidIndex_throwsException() {
        Exception exception = assertThrows(InvalidCommandException.class, () ->
                parser.parse("delete xyz"));
        assertEquals(Messages.WRONG_NUMBER_FORMAT, exception.getMessage());
    }

    @Test
    void parse_todoCommandMissingDescription_throwsException() {
        Exception exception = assertThrows(InvalidCommandException.class, () ->
                parser.parse("todo"));
        assertEquals(String.format(Messages.WRONG_COMMAND_FORMAT,
                ToDoCommand.COMMAND_FORMAT), exception.getMessage());
    }

    @Test
    void parse_deadlineCommandMissingKeyword_throwsException() {
        Exception exception = assertThrows(InvalidCommandException.class, () ->
                parser.parse("deadline submit report"));
        assertEquals(String.format(Messages.WRONG_COMMAND_FORMAT,
                DeadlineCommand.COMMAND_FORMAT), exception.getMessage());
    }

    @Test
    void parse_deadlineCommandMissingName_throwsException() {
        Exception exception = assertThrows(InvalidCommandException.class, () ->
                parser.parse("deadline /by 10pm"));
        assertEquals(String.format(Messages.WRONG_COMMAND_FORMAT,
                DeadlineCommand.COMMAND_FORMAT), exception.getMessage());
    }

    @Test
    void parse_eventCommandMissingKeyword_throwsException() {
        Exception exception = assertThrows(InvalidCommandException.class, () ->
                parser.parse("event meeting"));
        assertEquals(String.format(Messages.WRONG_COMMAND_FORMAT,
                EventCommand.COMMAND_FORMAT), exception.getMessage());
    }

    @Test
    void parse_eventCommandMissingName_throwsException() {
        Exception exception = assertThrows(InvalidCommandException.class, () ->
                parser.parse("event /from 10pm /to 11pm"));
        assertEquals(String.format(Messages.WRONG_COMMAND_FORMAT,
                EventCommand.COMMAND_FORMAT), exception.getMessage());
    }
}
