package mightyduck.command.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mightyduck.data.task.TaskManager;
import mightyduck.data.task.type.ToDo;
import mightyduck.exception.InvalidValueException;
import mightyduck.messages.Messages;

public class ToDoCommandTest {
    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();
    }

    @Test
    void execute_validArguments_addsDeadlineTask() throws InvalidValueException {
        assertEquals(0, taskManager.getTasks().size());

        String[] arguments = {"Project"};
        ToDoCommand command = new ToDoCommand(taskManager, arguments);
        command.execute();

        assertEquals(1, taskManager.getTasks().size());
        assertInstanceOf(ToDo.class, taskManager.getTask(0));
    }

    @Test
    void execute_invalidArguments_throwsException() {
        String[] arguments = {};
        ToDoCommand command = new ToDoCommand(taskManager, arguments);

        InvalidValueException exception =
                assertThrows(InvalidValueException.class, command::execute);
        assertEquals(
                String.format(Messages.WRONG_COMMAND_FORMAT, ToDoCommand.COMMAND_FORMAT),
                exception.getMessage());
    }
}
