package mightyduck.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mightyduck.command.UnmarkCommand;
import mightyduck.exception.InvalidCommandException;
import mightyduck.exception.InvalidValueException;
import mightyduck.task.TaskManager;
import mightyduck.utils.Messages;

public class UnmarkBuilderTest {

    private UnmarkBuilder unmarkBuilder;

    @BeforeEach
    public void setUp() {
        unmarkBuilder = new UnmarkBuilder(new TaskManager());
    }

    @Test
    public void fromInput_validIndex_createsUnmarkCommand() throws InvalidValueException,
            InvalidCommandException {
        String input = "1";
        assertInstanceOf(UnmarkCommand.class, unmarkBuilder.fromInput(input));
    }

    @Test
    public void fromInput_validIndices_createsUnmarkCommand() throws InvalidValueException,
            InvalidCommandException {
        String input = "1 2 3";
        assertInstanceOf(UnmarkCommand.class, unmarkBuilder.fromInput(input));
    }

    @Test
    public void fromInput_nonNumericIndices_throwsException() {
        String input = "abc";
        Exception exception = assertThrows(InvalidValueException.class, () ->
                unmarkBuilder.fromInput(input));
        assertEquals(Messages.WRONG_NUMBER_FORMAT, exception.getMessage());
    }
}
