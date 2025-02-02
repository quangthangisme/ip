package mightyduck.command.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mightyduck.data.task.Task;
import mightyduck.data.task.TaskManager;
import mightyduck.data.task.type.Deadline;
import mightyduck.data.task.type.ToDo;
import mightyduck.exception.InvalidValueException;
import mightyduck.utils.Messages;

public class UnmarkCommandTest {
    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();
        Task todo = new ToDo("todo");
        todo.mark();
        taskManager.addTask(todo);
        taskManager.addTask(new Deadline("deadline", LocalDateTime.now()));
    }

    @Test
    void execute_validArguments_addsEventTask() throws InvalidValueException {
        assertTrue(taskManager.getTask(0).isMarked());
        UnmarkCommand command = new UnmarkCommand(taskManager, 0);
        command.execute();
        assertFalse(taskManager.getTask(0).isMarked());
    }

    @Test
    void execute_invalidTimes_throwsException() {
        assertEquals(2, taskManager.getTaskCount());
        UnmarkCommand command = new UnmarkCommand(taskManager, 2);
        InvalidValueException exception =
                assertThrows(InvalidValueException.class, command::execute);
        assertEquals(Messages.OUT_OF_RANGE_INDEX, exception.getMessage());
    }
}
