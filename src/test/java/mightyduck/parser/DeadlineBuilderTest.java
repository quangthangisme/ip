package mightyduck.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mightyduck.command.DeadlineCommand;
import mightyduck.exception.InvalidCommandException;
import mightyduck.exception.InvalidValueException;
import mightyduck.task.TaskManager;
import mightyduck.utils.Messages;

public class DeadlineBuilderTest {

    private DeadlineBuilder deadlineBuilder;

    @BeforeEach
    public void setUp() {
        deadlineBuilder = new DeadlineBuilder(new TaskManager());
    }

    @Test
    public void fromInput_validArguments_createsDeadlineCommand() throws InvalidValueException,
            InvalidCommandException {
        String input = "lol xd /by 2023-12-01 12:20";
        assertInstanceOf(DeadlineCommand.class, deadlineBuilder.fromInput(input));
    }

    @Test
    public void fromInput_validArgumentsWithTags_createsEventCommand() throws InvalidValueException,
            InvalidCommandException {
        String input = "lol xd /by 2023-12-01 12:20 /tags hello pop";
        assertInstanceOf(DeadlineCommand.class, deadlineBuilder.fromInput(input));
    }

    @Test
    public void fromInput_numericTag_throwsException() {
        String input = "lol xd /by 2023-12-01 12:20 /tags hello 1";
        Exception exception = assertThrows(InvalidValueException.class, () ->
                deadlineBuilder.fromInput(input));
        assertEquals(String.format(Messages.INVALID_TAG_FORMAT, "1"), exception.getMessage());
    }

    @Test
    public void fromInput_duplicateTags_throwsException() {
        String input = "lol xd /by 2023-12-01 12:20 /tags hello hello";
        Exception exception = assertThrows(InvalidValueException.class, () ->
                deadlineBuilder.fromInput(input));
        assertEquals(String.format(Messages.DUPLICATE_TAG, "hello"), exception.getMessage());
    }

    @Test
    public void fromInput_missingName_throwsException() {
        String input = "/by 2023-12-01 12:20 /tags hello pop";
        Exception exception = assertThrows(InvalidCommandException.class, () ->
                deadlineBuilder.fromInput(input));
        assertEquals(String.format(Messages.WRONG_COMMAND_FORMAT, DeadlineBuilder.COMMAND_FORMAT),
                exception.getMessage());
    }

    @Test
    public void fromInput_missingDeadlineTime_throwsException() {
        String input = "lol xd /tags hello pop";
        Exception exception = assertThrows(InvalidCommandException.class, () ->
                deadlineBuilder.fromInput(input));
        assertEquals(String.format(Messages.WRONG_COMMAND_FORMAT, DeadlineBuilder.COMMAND_FORMAT),
                exception.getMessage());
    }

    @Test
    public void fromInput_invalidDeadlineTime_throwsException() {
        String input = "lol xd /by abc /tags hello pop";
        Exception exception = assertThrows(InvalidValueException.class, () ->
                deadlineBuilder.fromInput(input));
        assertEquals(Messages.FAILED_PARSE_TIME, exception.getMessage());
    }
}
