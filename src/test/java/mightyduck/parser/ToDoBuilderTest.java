package mightyduck.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mightyduck.command.ToDoCommand;
import mightyduck.exception.InvalidCommandException;
import mightyduck.exception.InvalidValueException;
import mightyduck.task.TaskManager;
import mightyduck.utils.Messages;

public class ToDoBuilderTest {

    private ToDoBuilder toDoBuilder;

    @BeforeEach
    public void setUp() {
        toDoBuilder = new ToDoBuilder(new TaskManager());
    }

    @Test
    public void fromInput_validArguments_createsToDoCommand() throws InvalidValueException,
            InvalidCommandException {
        String input = "lol xd";
        assertInstanceOf(ToDoCommand.class, toDoBuilder.fromInput(input));
    }

    @Test
    public void fromInput_validArgumentsWithTags_createsToDoCommand() throws InvalidValueException,
            InvalidCommandException {
        String input = "lol xd /tags hello ooo";
        assertInstanceOf(ToDoCommand.class, toDoBuilder.fromInput(input));
    }

    @Test
    public void fromInput_numericTag_throwsException() {
        String input = "lol xd /tags hello 1";
        Exception exception = assertThrows(InvalidValueException.class, () ->
                toDoBuilder.fromInput(input));
        assertEquals(String.format(Messages.INVALID_TAG_FORMAT, "1"), exception.getMessage());
    }

    @Test
    public void fromInput_duplicateTags_throwsException() {
        String input = "lol xd /tags lmao lmao";
        Exception exception = assertThrows(InvalidValueException.class, () ->
                toDoBuilder.fromInput(input));
        assertEquals(String.format(Messages.DUPLICATE_TAG, "lmao"), exception.getMessage());
    }

    @Test
    public void fromInput_missingName_throwsException() {
        String input = "/tags lmao lmao";
        Exception exception = assertThrows(InvalidCommandException.class, () ->
                toDoBuilder.fromInput(input));
        assertEquals(String.format(Messages.WRONG_COMMAND_FORMAT, ToDoBuilder.COMMAND_FORMAT),
                exception.getMessage());
    }
}
