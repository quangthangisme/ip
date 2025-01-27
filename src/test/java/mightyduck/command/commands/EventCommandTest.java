package mightyduck.command.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mightyduck.data.task.TaskManager;
import mightyduck.data.task.type.Event;
import mightyduck.exception.InvalidValueException;
import mightyduck.messages.Messages;

public class EventCommandTest {
    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();
    }

    @Test
    void execute_validArguments_addsEventTask()
            throws InvalidValueException {
        assertEquals(0, taskManager.getTasks().size());

        String[] arguments = {"Project", "2025-02-01 10:00", "2025-02-01 12:00"};
        EventCommand command = new EventCommand(taskManager, arguments);
        command.execute();

        assertEquals(1, taskManager.getTasks().size());
        assertInstanceOf(Event.class, taskManager.getTask(0));
    }

    @Test
    void execute_invalidTimes_throwsException() {
        String[] arguments = {"Project", "2025-25-01 10:00", "2025-02-01 12:00"};
        EventCommand command = new EventCommand(taskManager, arguments);

        InvalidValueException exception =
                assertThrows(InvalidValueException.class, command::execute);
        assertEquals(Messages.FAILED_PARSE_TIME, exception.getMessage());
    }

    @Test
    void execute_endTimeBeforeStartTime_throwsException() {
        String[] arguments = {"Project", "2025-02-01 10:00", "2025-02-01 09:00"};
        EventCommand command = new EventCommand(taskManager, arguments);

        InvalidValueException exception =
                assertThrows(InvalidValueException.class, command::execute);
        assertEquals(Messages.END_TIME_BEFORE_START_TIME,
                exception.getMessage());
    }

    @Test
    void execute_invalidArguments_throwsException() {
        String[] arguments = {"BLAH BLAH"};
        EventCommand command = new EventCommand(taskManager, arguments);

        InvalidValueException exception =
                assertThrows(InvalidValueException.class, command::execute);
        assertEquals(String.format(Messages.WRONG_COMMAND_FORMAT,
                EventCommand.COMMAND_FORMAT), exception.getMessage());
    }
}

