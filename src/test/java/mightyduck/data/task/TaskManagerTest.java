package mightyduck.data.task;

import mightyduck.data.task.type.ToDo;
import mightyduck.exception.InvalidValueException;
import mightyduck.messages.Messages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();
    }

    @Test
    void addTask_success() throws InvalidValueException {
        assertEquals(0, taskManager.getTaskCount());
        Task task = new ToDo("todo");
        int index = taskManager.addTask(task);
        assertEquals(0, index);
        assertEquals(1, taskManager.getTaskCount());
        assertEquals(task, taskManager.getTask(0));
    }

    @Test
    void getTask_validIndex_success() throws InvalidValueException {
        Task task = new ToDo("todo");
        taskManager.addTask(task);
        assertEquals(task, taskManager.getTask(0));
    }

    @Test
    void getTask_invalidIndex_throwsException() {
        Exception exception = assertThrows(InvalidValueException.class,
                () -> taskManager.getTask(0));
        assertEquals(Messages.OUT_OF_RANGE_INDEX, exception.getMessage());
    }

    @Test
    void markTask_success() throws InvalidValueException {
        Task task = new ToDo("task");
        taskManager.addTask(task);
        Task markedTask = taskManager.markTask(0);
        assertTrue(markedTask.isMarked());
    }

    @Test
    void unmarkTask_success() throws InvalidValueException {
        Task task = new ToDo("task");
        task.mark();
        taskManager.addTask(task);
        Task unmarkedTask = taskManager.unmarkTask(0);
        assertFalse(unmarkedTask.isMarked());
    }

    @Test
    void markTask_invalidIndex_throwsException() {
        Exception exception = assertThrows(InvalidValueException.class,
                () -> taskManager.markTask(0));
        assertEquals(Messages.OUT_OF_RANGE_INDEX, exception.getMessage());
    }

    @Test
    void unmarkTask_invalidIndex_throwsException() {
        Exception exception = assertThrows(InvalidValueException.class,
                () -> taskManager.unmarkTask(0));
        assertEquals(Messages.OUT_OF_RANGE_INDEX, exception.getMessage());
    }

    @Test
    void deleteTask_success() throws InvalidValueException {
        Task task = new ToDo("Test task");
        taskManager.addTask(task);
        Task deletedTask = taskManager.deleteTask(0);
        assertEquals(task, deletedTask);
        assertEquals(0, taskManager.getTaskCount());
    }

    @Test
    void deleteTask_invalidIndex_throwsException() {
        Exception exception = assertThrows(InvalidValueException.class,
                () -> taskManager.deleteTask(0));
        assertEquals(Messages.OUT_OF_RANGE_INDEX, exception.getMessage());
    }

    @Test
    void searchKeyword_success() {
        Task task1 = new ToDo("task 1");
        Task task2 = new ToDo("task 2");
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        assertEquals(2, taskManager.searchKeyword("task").size());
    }

    @Test
    void encodeTasks_success() {
        Task task1 = new ToDo("task 1");
        Task task2 = new ToDo("task 2");
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        List<String> encodedTasks = taskManager.encodeTasks();
        assertEquals(2, encodedTasks.size());
        assertEquals(task1.encode(), encodedTasks.get(0));
        assertEquals(task2.encode(), encodedTasks.get(1));
    }

    @Test
    void getTasks_returnsCopyOfList() {
        Task task = new ToDo("task");
        taskManager.addTask(task);

        List<Task> tasks = taskManager.getTasks();
        assertEquals(1, tasks.size());
        assertEquals(task, tasks.get(0));

        tasks.add(new ToDo("New task"));
        assertEquals(1, taskManager.getTaskCount());
    }
}
