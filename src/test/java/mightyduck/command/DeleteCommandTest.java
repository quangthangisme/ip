package mightyduck.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mightyduck.exception.InvalidValueException;
import mightyduck.task.TaskManager;
import mightyduck.task.ToDo;
import mightyduck.utils.Messages;

public class DeleteCommandTest {
    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();
        taskManager.addTask(new ToDo("todo"));
    }

    @Test
    void execute_validArguments_deletesTask() throws InvalidValueException {
        assertEquals(1, taskManager.getTasks().size());
        DeleteCommand command = new DeleteCommand(taskManager, List.of(0));
        command.execute();

        assertEquals(0, taskManager.getTasks().size());
    }

    @Test
    void execute_invalidArguments_throwsException() {
        assertEquals(1, taskManager.getTasks().size());
        DeleteCommand command = new DeleteCommand(taskManager, List.of(1));
        InvalidValueException exception = assertThrows(InvalidValueException.class,
                command::execute);
        assertEquals(String.format(Messages.OUT_OF_RANGE_INDEX, 1), exception.getMessage());
        assertEquals(1, taskManager.getTasks().size());
    }
}
