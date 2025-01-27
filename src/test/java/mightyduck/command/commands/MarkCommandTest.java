package mightyduck.command.commands;

import mightyduck.data.task.TaskManager;
import mightyduck.data.task.type.Deadline;
import mightyduck.data.task.type.ToDo;
import mightyduck.exception.InvalidValueException;
import mightyduck.messages.Messages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MarkCommandTest {
    private TaskManager taskManager;

    @BeforeEach
    void setUp() throws InvalidValueException {
        taskManager = new TaskManager();
        taskManager.addTask(new ToDo("todo"));
        taskManager.addTask(new Deadline("deadline", "2025-01-01 12:00"));
    }

    @Test
    void execute_validArguments_addsEventTask()
            throws InvalidValueException {
        assertFalse(taskManager.getTask(1).isMarked());
        MarkCommand command = new MarkCommand(taskManager, 1);
        command.execute();
        assertTrue(taskManager.getTask(1).isMarked());
    }

    @Test
    void execute_invalidTimes_throwsException() {
        assertEquals(2, taskManager.getTaskCount());
        MarkCommand command = new MarkCommand(taskManager, 2);
        InvalidValueException exception =
                assertThrows(InvalidValueException.class, command::execute);
        assertEquals(Messages.OUT_OF_RANGE_INDEX, exception.getMessage());
    }
}
