package mightyduck.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mightyduck.command.DeleteCommand;
import mightyduck.exception.InvalidCommandException;
import mightyduck.exception.InvalidValueException;
import mightyduck.task.TaskManager;
import mightyduck.utils.Messages;

public class DeleteBuilderTest {

    private DeleteBuilder deleteBuilder;

    @BeforeEach
    public void setUp() {
        deleteBuilder = new DeleteBuilder(new TaskManager());
    }

    @Test
    public void fromInput_validIndex_createsDeleteCommand() throws InvalidValueException,
            InvalidCommandException {
        String input = "1";
        assertInstanceOf(DeleteCommand.class, deleteBuilder.fromInput(input));
    }

    @Test
    public void fromInput_validIndices_createsDeleteCommand() throws InvalidValueException,
            InvalidCommandException {
        String input = "1 2 3";
        assertInstanceOf(DeleteCommand.class, deleteBuilder.fromInput(input));
    }

    @Test
    public void fromInput_nonNumericIndices_throwsException() {
        String input = "abc";
        Exception exception = assertThrows(InvalidValueException.class, () ->
                deleteBuilder.fromInput(input));
        assertEquals(Messages.WRONG_NUMBER_FORMAT, exception.getMessage());
    }
}
