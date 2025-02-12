package mightyduck.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mightyduck.task.TaskManager;
import mightyduck.task.ToDo;
import mightyduck.utils.Messages;

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
    void execute_validArguments_find() {
        assertEquals(3, taskManager.getTasks().size());

        FindCommand command = new FindCommand(taskManager, List.of("ll", "pp"));
        CommandResult commandResult = command.execute();

        assertEquals(3, commandResult.tasks().size());
        assertEquals(Messages.FIND, commandResult.feedback());
    }

    @Test
    void execute_validArguments_emptyFind() {
        assertEquals(3, taskManager.getTasks().size());

        FindCommand command = new FindCommand(taskManager, List.of("goodbye"));
        CommandResult commandResult = command.execute();

        assertEquals(0, commandResult.tasks().size());
        assertEquals(Messages.EMPTY_FIND, commandResult.feedback());
    }
}
