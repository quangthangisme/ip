package mightyduck.command.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mightyduck.data.task.TaskManager;
import mightyduck.data.task.type.Deadline;
import mightyduck.exception.InvalidValueException;
import mightyduck.messages.Messages;

public class DeadlineCommandTest {
    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();
    }

    @Test
    void execute_validArguments_addsDeadlineTask()
            throws InvalidValueException {
        assertEquals(0, taskManager.getTasks().size());

        String[] arguments = {"Project", "2025-02-01 10:00"};
        DeadlineCommand command = new DeadlineCommand(taskManager, arguments);
        command.execute();

        assertEquals(1, taskManager.getTasks().size());
        assertInstanceOf(Deadline.class, taskManager.getTask(0));
    }

    @Test
    void execute_invalidArguments_throwsException() {
        String[] arguments = {"BLAH BLAH"};
        DeadlineCommand command = new DeadlineCommand(taskManager, arguments);

        InvalidValueException exception =
                assertThrows(InvalidValueException.class, command::execute);
        assertEquals(String.format(Messages.WRONG_COMMAND_FORMAT,
                DeadlineCommand.COMMAND_FORMAT), exception.getMessage());
    }
}
