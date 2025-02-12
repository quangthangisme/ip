package mightyduck.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mightyduck.command.UntagCommand;
import mightyduck.exception.InvalidCommandException;
import mightyduck.exception.InvalidValueException;
import mightyduck.task.TaskManager;
import mightyduck.utils.Messages;

public class UntagBuilderTest {

    private UntagBuilder untagBuilder;

    @BeforeEach
    public void setUp() {
        untagBuilder = new UntagBuilder(new TaskManager());
    }

    @Test
    public void fromInput_validArguments_createsUntagCommand() throws InvalidValueException,
            InvalidCommandException {
        String input = "1 2 lol xd";
        assertInstanceOf(UntagCommand.class, untagBuilder.fromInput(input));
    }

    @Test
    public void fromInput_missingIndices_throwsException() {
        String input = "abc";
        Exception exception = assertThrows(InvalidCommandException.class, () ->
                untagBuilder.fromInput(input));
        assertEquals(String.format(Messages.WRONG_COMMAND_FORMAT, UntagBuilder.COMMAND_FORMAT),
                exception.getMessage());
    }

    @Test
    public void fromInput_missingTags_throwsException() {
        String input = "1 2";
        Exception exception = assertThrows(InvalidCommandException.class, () ->
                untagBuilder.fromInput(input));
        assertEquals(String.format(Messages.WRONG_COMMAND_FORMAT, UntagBuilder.COMMAND_FORMAT),
                exception.getMessage());
    }

    @Test
    public void fromInput_numericTag_throwsException() {
        String input = "1 2 untag 5";
        Exception exception = assertThrows(InvalidValueException.class, () ->
                untagBuilder.fromInput(input));
        assertEquals(String.format(Messages.INVALID_TAG_FORMAT, "5"), exception.getMessage());
    }

    @Test
    public void fromInput_duplicateTag_throwsException() {
        String input = "1 2 untag untag";
        Exception exception = assertThrows(InvalidValueException.class, () ->
                untagBuilder.fromInput(input));
        assertEquals(String.format(Messages.DUPLICATE_TAG, "untag"), exception.getMessage());
    }
}
