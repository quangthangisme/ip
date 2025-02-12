package mightyduck.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mightyduck.command.MarkCommand;
import mightyduck.exception.InvalidCommandException;
import mightyduck.exception.InvalidValueException;
import mightyduck.task.TaskManager;
import mightyduck.utils.Messages;

public class MarkBuilderTest {

    private MarkBuilder markBuilder;

    @BeforeEach
    public void setUp() {
        markBuilder = new MarkBuilder(new TaskManager());
    }

    @Test
    public void fromInput_validIndex_createsMarkCommand() throws InvalidValueException,
            InvalidCommandException {
        String input = "1";
        assertInstanceOf(MarkCommand.class, markBuilder.fromInput(input));
    }

    @Test
    public void fromInput_validIndices_createsMarkCommand() throws InvalidValueException,
            InvalidCommandException {
        String input = "1 2 3";
        assertInstanceOf(MarkCommand.class, markBuilder.fromInput(input));
    }

    @Test
    public void fromInput_nonNumericIndices_throwsException() {
        String input = "abc";
        Exception exception = assertThrows(InvalidValueException.class, () ->
                markBuilder.fromInput(input));
        assertEquals(Messages.WRONG_NUMBER_FORMAT, exception.getMessage());
    }
}
