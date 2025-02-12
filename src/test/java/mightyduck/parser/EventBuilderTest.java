package mightyduck.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mightyduck.command.EventCommand;
import mightyduck.exception.InvalidCommandException;
import mightyduck.exception.InvalidValueException;
import mightyduck.task.TaskManager;
import mightyduck.utils.Messages;

public class EventBuilderTest {

    private EventBuilder eventBuilder;

    @BeforeEach
    public void setUp() {
        eventBuilder = new EventBuilder(new TaskManager());
    }

    @Test
    public void fromInput_validArguments_createsEventCommand() throws InvalidValueException,
            InvalidCommandException {
        String input = "lol xd /from 2023-12-01 12:20 /to 2023-12-04 14:20";
        assertInstanceOf(EventCommand.class, eventBuilder.fromInput(input));
    }

    @Test
    public void fromInput_validArgumentsWithTags_createsEventCommand() throws InvalidValueException,
            InvalidCommandException {
        String input = "lol xd /from 2023-12-01 12:20 /to 2023-12-04 14:20 /tags hello pop";
        assertInstanceOf(EventCommand.class, eventBuilder.fromInput(input));
    }

    @Test
    public void fromInput_numericTag_throwsException() {
        String input = "lol xd /from 2023-12-01 12:20 /to 2023-12-04 14:20 /tags hello 1";
        Exception exception = assertThrows(InvalidValueException.class, () ->
                eventBuilder.fromInput(input));
        assertEquals(String.format(Messages.INVALID_TAG_FORMAT, "1"), exception.getMessage());
    }

    @Test
    public void fromInput_duplicateTags_throwsException() {
        String input = "lol xd /from 2023-12-01 12:20 /to 2023-12-04 14:20 /tags hello hello";
        Exception exception = assertThrows(InvalidValueException.class, () ->
                eventBuilder.fromInput(input));
        assertEquals(String.format(Messages.DUPLICATE_TAG, "hello"), exception.getMessage());
    }

    @Test
    public void fromInput_missingName_throwsException() {
        String input = "/from 2023-12-01 12:20 /to 2023-12-04 14:20 /tags hello pop";
        Exception exception = assertThrows(InvalidCommandException.class, () ->
                eventBuilder.fromInput(input));
        assertEquals(String.format(Messages.WRONG_COMMAND_FORMAT, EventBuilder.COMMAND_FORMAT),
                exception.getMessage());
    }

    @Test
    public void fromInput_missingStartTime_throwsException() {
        String input = "lol xd /to 2023-12-04 14:20 /tags hello pop";
        Exception exception = assertThrows(InvalidCommandException.class, () ->
                eventBuilder.fromInput(input));
        assertEquals(String.format(Messages.WRONG_COMMAND_FORMAT, EventBuilder.COMMAND_FORMAT),
                exception.getMessage());
    }

    @Test
    public void fromInput_missingEndTime_throwsException() {
        String input = "lol xd /from 2023-12-04 14:20 /tags hello pop";
        Exception exception = assertThrows(InvalidCommandException.class, () ->
                eventBuilder.fromInput(input));
        assertEquals(String.format(Messages.WRONG_COMMAND_FORMAT, EventBuilder.COMMAND_FORMAT),
                exception.getMessage());
    }

    @Test
    public void fromInput_invalidStartTime_throwsException() {
        String input = "lol xd /from abc /to 2023-12-02 12:20 /tags hello pop";
        Exception exception = assertThrows(InvalidValueException.class, () ->
                eventBuilder.fromInput(input));
        assertEquals(Messages.FAILED_PARSE_TIME, exception.getMessage());
    }

    @Test
    public void fromInput_invalidEndTime_throwsException() {
        String input = "lol xd /from 2023-12-02 12:20 /to bcd /tags hello pop";
        Exception exception = assertThrows(InvalidValueException.class, () ->
                eventBuilder.fromInput(input));
        assertEquals(Messages.FAILED_PARSE_TIME, exception.getMessage());
    }

    @Test
    public void fromInput_endTimeBeforeStartTime_throwsException() {
        String input = "lol xd /from 2023-12-02 12:20 /to 2023-12-02 11:20 /tags hello pop";
        Exception exception = assertThrows(InvalidValueException.class, () ->
                eventBuilder.fromInput(input));
        assertEquals(Messages.END_TIME_BEFORE_START_TIME, exception.getMessage());
    }


    @Test
    public void fromInput_wrongOrderOfArguments_throwsException() {
        String input = "lol xd /to 2023-12-02 12:20 /from 2023-12-02 11:20 /tags hello pop";
        Exception exception = assertThrows(InvalidCommandException.class, () ->
                eventBuilder.fromInput(input));
        assertEquals(String.format(Messages.WRONG_COMMAND_FORMAT, EventBuilder.COMMAND_FORMAT),
                exception.getMessage());
    }
}
