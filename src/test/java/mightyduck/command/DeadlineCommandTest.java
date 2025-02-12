package mightyduck.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mightyduck.exception.InvalidValueException;
import mightyduck.task.Deadline;
import mightyduck.task.TaskManager;

public class DeadlineCommandTest {
    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();
    }

    @Test
    void execute_validArguments_addsDeadlineTask() throws InvalidValueException {
        assertEquals(0, taskManager.getTasks().size());

        DeadlineCommand command = new DeadlineCommand(taskManager, "test", LocalDateTime.now(),
                List.of());
        command.execute();

        assertEquals(1, taskManager.getTasks().size());
        assertInstanceOf(Deadline.class, taskManager.getTask(0));
    }

}
