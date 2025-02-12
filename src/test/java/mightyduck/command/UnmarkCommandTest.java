package mightyduck.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mightyduck.exception.InvalidValueException;
import mightyduck.task.Deadline;
import mightyduck.task.Task;
import mightyduck.task.TaskManager;
import mightyduck.task.ToDo;
import mightyduck.utils.Messages;

public class UnmarkCommandTest {
    private TaskManager taskManager;

    @BeforeEach
    void setUp() throws InvalidValueException {
        taskManager = new TaskManager();
        Task todo = new ToDo("todo");
        todo.mark();
        taskManager.addTask(todo);
        taskManager.addTask(new Deadline("deadline", LocalDateTime.now()));
    }

    @Test
    void execute_validArguments_addsEventTask() throws InvalidValueException {
        assertTrue(taskManager.getTask(0).isMarked());
        UnmarkCommand command = new UnmarkCommand(taskManager, List.of(0));
        command.execute();
        assertFalse(taskManager.getTask(0).isMarked());
    }

    @Test
    void execute_invalidTimes_throwsException() {
        assertEquals(2, taskManager.getTaskCount());
        UnmarkCommand command = new UnmarkCommand(taskManager, List.of(2));
        InvalidValueException exception =
                assertThrows(InvalidValueException.class, command::execute);
        assertEquals(String.format(Messages.OUT_OF_RANGE_INDEX, 2), exception.getMessage());
    }
}
