package mightyduck.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mightyduck.command.ByeCommand;
import mightyduck.command.Command;
import mightyduck.command.DeadlineCommand;
import mightyduck.command.DeleteCommand;
import mightyduck.command.EventCommand;
import mightyduck.command.FindCommand;
import mightyduck.command.ListCommand;
import mightyduck.command.MarkCommand;
import mightyduck.command.ToDoCommand;
import mightyduck.command.UnmarkCommand;
import mightyduck.exception.InvalidCommandException;
import mightyduck.exception.InvalidValueException;
import mightyduck.task.TaskManager;
import mightyduck.utils.Messages;

public class ParserTest {
    private Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Parser(new TaskManager());
    }

    @Test
    void parse_byeCommand_success() throws InvalidCommandException, InvalidValueException {
        Command command = parser.parse("bye");
        assertInstanceOf(ByeCommand.class, command);
    }

    @Test
    void parse_listCommand_success() throws InvalidCommandException, InvalidValueException {
        Command command = parser.parse("list");
        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    void parse_markCommand_success() throws InvalidCommandException, InvalidValueException {
        Command command = parser.parse("mark 1");
        assertInstanceOf(MarkCommand.class, command);
    }

    @Test
    void parse_unmarkCommand_success() throws InvalidCommandException, InvalidValueException {
        Command command = parser.parse("unmark 1");
        assertInstanceOf(UnmarkCommand.class, command);
    }

    @Test
    void parse_deleteCommand_success() throws InvalidCommandException, InvalidValueException {
        Command command = parser.parse("delete 1");
        assertInstanceOf(DeleteCommand.class, command);
    }

    @Test
    void parse_todoCommand_success() throws InvalidCommandException, InvalidValueException {
        Command command = parser.parse("todo read book");
        assertInstanceOf(ToDoCommand.class, command);
    }

    @Test
    void parse_deadlineCommand_success() throws InvalidCommandException, InvalidValueException {
        Command command = parser.parse("deadline project /by 2025-02-02 11:20");
        assertInstanceOf(DeadlineCommand.class, command);
    }

    @Test
    void parse_eventCommand_success() throws InvalidCommandException, InvalidValueException {
        Command command = parser.parse("event meeting /from 2025-02-02 11:20 /to 2025-02-03 11:20");
        assertInstanceOf(EventCommand.class, command);
    }

    @Test
    void parse_findCommand_success() throws InvalidCommandException, InvalidValueException {
        Command command = parser.parse("find hello");
        assertInstanceOf(FindCommand.class, command);
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
        assertEquals(String.format(Messages.WRONG_COMMAND_FORMAT, MarkBuilder.COMMAND_FORMAT),
                exception.getMessage());
    }

    @Test
    void parse_unmarkCommandInvalidIndex_throwsException() {
        Exception exception = assertThrows(InvalidValueException.class, () ->
                parser.parse("unmark abc"));
        assertEquals(Messages.WRONG_NUMBER_FORMAT, exception.getMessage());
    }

    @Test
    void parse_deleteCommandInvalidIndex_throwsException() {
        Exception exception = assertThrows(InvalidValueException.class, () ->
                parser.parse("delete xyz"));
        assertEquals(Messages.WRONG_NUMBER_FORMAT, exception.getMessage());
    }

    @Test
    void parse_todoCommandMissingDescription_throwsException() {
        Exception exception = assertThrows(InvalidCommandException.class, () ->
                parser.parse("todo"));
        assertEquals(String.format(Messages.WRONG_COMMAND_FORMAT,
                ToDoBuilder.COMMAND_FORMAT), exception.getMessage());
    }

    @Test
    void parse_deadlineCommandMissingKeyword_throwsException() {
        Exception exception = assertThrows(InvalidCommandException.class, () ->
                parser.parse("deadline submit report"));
        assertEquals(String.format(Messages.WRONG_COMMAND_FORMAT,
                DeadlineBuilder.COMMAND_FORMAT), exception.getMessage());
    }

    @Test
    void parse_deadlineCommandMissingName_throwsException() {
        Exception exception = assertThrows(InvalidCommandException.class, () ->
                parser.parse("deadline /by 10pm"));
        assertEquals(String.format(Messages.WRONG_COMMAND_FORMAT,
                DeadlineBuilder.COMMAND_FORMAT), exception.getMessage());
    }

    @Test
    void parse_eventCommandMissingKeyword_throwsException() {
        Exception exception = assertThrows(InvalidCommandException.class, () ->
                parser.parse("event meeting"));
        assertEquals(String.format(Messages.WRONG_COMMAND_FORMAT,
                EventBuilder.COMMAND_FORMAT), exception.getMessage());
    }

    @Test
    void parse_eventCommandMissingName_throwsException() {
        Exception exception = assertThrows(InvalidCommandException.class, () ->
                parser.parse("event /from 10pm /to 11pm"));
        assertEquals(String.format(Messages.WRONG_COMMAND_FORMAT,
                EventBuilder.COMMAND_FORMAT), exception.getMessage());
    }

    @Test
    void parse_eventCommandEndTimeBeforeStartTime_throwsException() {
        Exception exception = assertThrows(InvalidValueException.class, () ->
                parser.parse("event meeting /from 2025-02-02 11:20 /to 2025-02-01 11:20"));
        assertEquals(Messages.END_TIME_BEFORE_START_TIME, exception.getMessage());
    }

    @Test
    void parse_findCommandMissingWord_throwsException() {
        Exception exception = assertThrows(InvalidCommandException.class, () ->
                parser.parse("find"));
        assertEquals(String.format(Messages.WRONG_COMMAND_FORMAT,
                FindBuilder.COMMAND_FORMAT), exception.getMessage());
    }
}
