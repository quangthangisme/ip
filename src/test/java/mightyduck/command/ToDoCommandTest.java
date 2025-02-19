package mightyduck.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mightyduck.exception.InvalidValueException;
import mightyduck.task.TaskManager;
import mightyduck.task.ToDo;

public class ToDoCommandTest {
    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();
    }

    @Test
    void execute_validArguments_addsDeadlineTask() throws InvalidValueException {
        assertEquals(0, taskManager.getTasks().size());

        ToDoCommand command = new ToDoCommand(taskManager, "test1", List.of());
        command.execute();

        assertEquals(1, taskManager.getTasks().size());
        assertInstanceOf(ToDo.class, taskManager.getTask(0));
    }
}
