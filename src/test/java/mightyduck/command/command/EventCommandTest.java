package mightyduck.command.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mightyduck.data.task.TaskManager;
import mightyduck.data.task.type.Event;
import mightyduck.exception.InvalidValueException;

public class EventCommandTest {
    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();
    }

    @Test
    void execute_validArguments_addsEventTask() throws InvalidValueException {
        assertEquals(0, taskManager.getTasks().size());

        EventCommand command = new EventCommand(taskManager, "test", LocalDateTime.now(),
                LocalDateTime.now().plusDays(1));
        command.execute();

        assertEquals(1, taskManager.getTasks().size());
        assertInstanceOf(Event.class, taskManager.getTask(0));
    }
}
