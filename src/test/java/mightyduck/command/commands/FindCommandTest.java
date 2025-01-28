package mightyduck.command.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mightyduck.command.CommandResult;
import mightyduck.data.task.TaskManager;
import mightyduck.data.task.type.ToDo;
import mightyduck.exception.InvalidValueException;
import mightyduck.messages.Messages;

public class FindCommandTest {
    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();
        taskManager.addTask(new ToDo("hello"));
        taskManager.addTask(new ToDo("apple"));
        taskManager.addTask(new ToDo("llama"));
    }

    @Test
    void execute_validArguments_find() throws InvalidValueException {
        assertEquals(3, taskManager.getTasks().size());

        String[] arguments = {"ll"};
        FindCommand command = new FindCommand(taskManager, arguments);
        CommandResult commandResult = command.execute();

        assertEquals(2, commandResult.tasks().size());
        assertEquals(Messages.FIND, commandResult.feedback());
    }

    @Test
    void execute_validArguments_emptyFind() throws InvalidValueException {
        assertEquals(3, taskManager.getTasks().size());

        String[] arguments = {"goodbye"};
        FindCommand command = new FindCommand(taskManager, arguments);
        CommandResult commandResult = command.execute();

        assertEquals(0, commandResult.tasks().size());
        assertEquals(Messages.EMPTY_FIND, commandResult.feedback());
    }

    @Test
    void execute_invalidArguments_throwsException() {
        String[] arguments = {};
        FindCommand command = new FindCommand(taskManager, arguments);

        InvalidValueException exception =
                assertThrows(InvalidValueException.class, command::execute);
        assertEquals(String.format(Messages.WRONG_COMMAND_FORMAT,
                FindCommand.COMMAND_FORMAT), exception.getMessage());
    }
}
