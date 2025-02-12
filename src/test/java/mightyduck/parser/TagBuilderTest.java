package mightyduck.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mightyduck.command.TagCommand;
import mightyduck.exception.InvalidCommandException;
import mightyduck.exception.InvalidValueException;
import mightyduck.task.TaskManager;
import mightyduck.utils.Messages;

public class TagBuilderTest {

    private TagBuilder tagBuilder;

    @BeforeEach
    public void setUp() {
        tagBuilder = new TagBuilder(new TaskManager());
    }

    @Test
    public void fromInput_validArguments_createsTagCommand() throws InvalidValueException,
            InvalidCommandException {
        String input = "1 2 lol xd";
        assertInstanceOf(TagCommand.class, tagBuilder.fromInput(input));
    }

    @Test
    public void fromInput_missingIndices_throwsException() {
        String input = "abc";
        Exception exception = assertThrows(InvalidCommandException.class, () ->
                tagBuilder.fromInput(input));
        assertEquals(String.format(Messages.WRONG_COMMAND_FORMAT, TagBuilder.COMMAND_FORMAT),
                exception.getMessage());
    }

    @Test
    public void fromInput_missingTags_throwsException() {
        String input = "1 2";
        Exception exception = assertThrows(InvalidCommandException.class, () ->
                tagBuilder.fromInput(input));
        assertEquals(String.format(Messages.WRONG_COMMAND_FORMAT, TagBuilder.COMMAND_FORMAT),
                exception.getMessage());
    }

    @Test
    public void fromInput_numericTag_throwsException() {
        String input = "1 2 tag 5";
        Exception exception = assertThrows(InvalidValueException.class, () ->
                tagBuilder.fromInput(input));
        assertEquals(String.format(Messages.INVALID_TAG_FORMAT, "5"), exception.getMessage());
    }

    @Test
    public void fromInput_duplicateTag_throwsException() {
        String input = "1 2 tag tag";
        Exception exception = assertThrows(InvalidValueException.class, () ->
                tagBuilder.fromInput(input));
        assertEquals(String.format(Messages.DUPLICATE_TAG, "tag"), exception.getMessage());
    }
}
