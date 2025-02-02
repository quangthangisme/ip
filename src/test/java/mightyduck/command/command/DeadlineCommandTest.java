package mightyduck.command.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mightyduck.data.task.TaskManager;
import mightyduck.data.task.type.Deadline;
import mightyduck.exception.InvalidValueException;

public class DeadlineCommandTest {
    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();
    }

    @Test
    void execute_validArguments_addsDeadlineTask() throws InvalidValueException {
        assertEquals(0, taskManager.getTasks().size());

        DeadlineCommand command = new DeadlineCommand(taskManager, "test", LocalDateTime.now());
        command.execute();

        assertEquals(1, taskManager.getTasks().size());
        assertInstanceOf(Deadline.class, taskManager.getTask(0));
    }

}
