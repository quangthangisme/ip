package mightyduck.command.commands;

import mightyduck.data.task.TaskManager;
import mightyduck.data.task.type.ToDo;
import mightyduck.exception.InvalidValueException;
import mightyduck.messages.Messages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeleteCommandTest {
    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();
        taskManager.addTask(new ToDo("todo"));
    }

    @Test
    void execute_validArguments_deletesTask()
            throws InvalidValueException {
        assertEquals(1, taskManager.getTasks().size());
        DeleteCommand command = new DeleteCommand(taskManager, 0);
        command.execute();

        assertEquals(0, taskManager.getTasks().size());
    }

    @Test
    void execute_invalidArguments_throwsException() {
        assertEquals(1, taskManager.getTasks().size());
        DeleteCommand command = new DeleteCommand(taskManager, 1);
        InvalidValueException exception =
                assertThrows(InvalidValueException.class, command::execute);
        assertEquals(Messages.OUT_OF_RANGE_INDEX, exception.getMessage());
        assertEquals(1, taskManager.getTasks().size());
    }
}
