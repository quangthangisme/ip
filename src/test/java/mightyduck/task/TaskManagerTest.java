package mightyduck.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mightyduck.exception.InvalidValueException;
import mightyduck.utils.Messages;
import mightyduck.utils.Pair;

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
        Exception exception = assertThrows(InvalidValueException.class, () ->
                taskManager.getTask(0));
        assertEquals(String.format(Messages.OUT_OF_RANGE_INDEX, 0), exception.getMessage());
    }

    @Test
    void markTasks_success() throws InvalidValueException {
        Task task1 = new ToDo("Task 1");
        Task task2 = new ToDo("Task 2");
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        List<Pair<Integer, Task>> markedTasks = taskManager.markTasks(List.of(0, 1));

        assertTrue(markedTasks.get(0).value().isMarked());
        assertTrue(markedTasks.get(1).value().isMarked());
    }

    @Test
    void unmarkTasks_success() throws InvalidValueException {
        Task task1 = new ToDo("Task 1");
        Task task2 = new ToDo("Task 2");
        task1.mark();
        task2.mark();
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        List<Pair<Integer, Task>> markedTasks = taskManager.unmarkTasks(List.of(0, 1));

        assertFalse(markedTasks.get(0).value().isMarked());
        assertFalse(markedTasks.get(1).value().isMarked());
    }

    @Test
    void markTasks_invalidIndex_throwsException() {
        taskManager.addTask(new ToDo("Task"));

        Exception exception = assertThrows(InvalidValueException.class, () ->
                taskManager.markTasks(List.of(0, 1)));
        assertEquals(String.format(Messages.OUT_OF_RANGE_INDEX, 1), exception.getMessage());
    }

    @Test
    void unmarkTasks_invalidIndex_throwsException() {
        taskManager.addTask(new ToDo("Task"));

        Exception exception = assertThrows(InvalidValueException.class, () ->
                taskManager.unmarkTasks(List.of(0, 1)));
        assertEquals(String.format(Messages.OUT_OF_RANGE_INDEX, 1), exception.getMessage());
    }

    @Test
    void markTask_alreadyMarked_throwsException() throws InvalidValueException {
        Task task1 = new ToDo("Task 1");
        Task task2 = new ToDo("Task 2");
        task2.mark();
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Exception exception = assertThrows(InvalidValueException.class, () ->
                taskManager.markTasks(List.of(0, 1)));
        assertEquals(String.format(Messages.ALREADY_MARKED, "Task 2"), exception.getMessage());

        assertFalse(task1.isMarked());
        assertTrue(task2.isMarked());
    }

    @Test
    void unmarkTask_alreadyUnmarked_throwsException() throws InvalidValueException {
        Task task1 = new ToDo("Task 1");
        Task task2 = new ToDo("Task 2");
        task1.mark();
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Exception exception = assertThrows(InvalidValueException.class, () ->
                taskManager.unmarkTasks(List.of(0, 1)));
        assertEquals(String.format(Messages.ALREADY_UNMARKED, "Task 2"), exception.getMessage());

        assertTrue(task1.isMarked());
        assertFalse(task2.isMarked());
    }

    @Test
    void tagTasks_success() throws InvalidValueException {
        Task task1 = new ToDo("Task 1");
        Task task2 = new ToDo("Task 2");
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        List<Pair<Integer, Task>> tasks = taskManager.tagTasks(List.of(0, 1), List.of("tp", "ip"));

        assertTrue(tasks.get(0).value().hasTag("tp"));
        assertTrue(tasks.get(0).value().hasTag("ip"));
        assertTrue(tasks.get(1).value().hasTag("tp"));
        assertTrue(tasks.get(1).value().hasTag("ip"));
    }

    @Test
    void tagTasks_alreadyHasTag_throwsException() throws InvalidValueException {
        Task task1 = new ToDo("Task 1");
        Task task2 = new ToDo("Task 2");
        task2.addTags(List.of("ip"));
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Exception exception = assertThrows(InvalidValueException.class, () ->
                taskManager.tagTasks(List.of(0, 1), List.of("ip", "tp")));
        assertEquals(String.format(Messages.TAG_ALREADY_EXISTED, "ip", "Task 2"),
                exception.getMessage());

        assertFalse(task1.hasTag("tp"));
        assertFalse(task1.hasTag("ip"));
        assertTrue(task2.hasTag("ip"));
        assertFalse(task2.hasTag("tp"));
    }

    @Test
    void untagTasks_success() throws InvalidValueException {
        Task task1 = new ToDo("Task 1");
        Task task2 = new ToDo("Task 2");
        task1.addTags(List.of("ip", "tp"));
        task2.addTags(List.of("ip", "tp"));
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        List<Pair<Integer, Task>> tasks = taskManager.untagTasks(List.of(0, 1),
                List.of("tp", "ip"));

        assertFalse(tasks.get(0).value().hasTag("tp"));
        assertFalse(tasks.get(0).value().hasTag("ip"));
        assertFalse(tasks.get(1).value().hasTag("tp"));
        assertFalse(tasks.get(1).value().hasTag("ip"));
    }

    @Test
    void untagTasks_missingTag_throwsException() throws InvalidValueException {
        Task task1 = new ToDo("Task 1");
        Task task2 = new ToDo("Task 2");
        task1.addTags(List.of("ip", "tp"));
        task2.addTags(List.of("ip"));
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Exception exception = assertThrows(InvalidValueException.class, () ->
                taskManager.untagTasks(List.of(0, 1), List.of("tp", "ip")));
        assertEquals(String.format(Messages.TAG_NOT_FOUND, "tp", "Task 2"),
                exception.getMessage());

        assertTrue(task1.hasTag("tp"));
        assertTrue(task1.hasTag("ip"));
        assertTrue(task2.hasTag("ip"));
        assertFalse(task2.hasTag("tp"));
    }

    @Test
    void deleteTask_success() throws InvalidValueException {
        Task task1 = new ToDo("Task 1");
        Task task2 = new ToDo("Task 2");
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        taskManager.deleteTasks(List.of(0, 1));
        assertEquals(0, taskManager.getTaskCount());
    }

    @Test
    void deleteTask_invalidIndex_throwsException() {
        Task task1 = new ToDo("Task 1");
        taskManager.addTask(task1);
        Exception exception = assertThrows(InvalidValueException.class, () ->
                taskManager.deleteTasks(List.of(0, 1)));
        assertEquals(String.format(Messages.OUT_OF_RANGE_INDEX, 1), exception.getMessage());
        assertEquals(1, taskManager.getTaskCount());
    }

    @Test
    void searchKeywords_oneKw_success() {
        Task task1 = new ToDo("task 1");
        Task task2 = new ToDo("task 2");
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        assertEquals(2, taskManager.searchKeywords(List.of("task")).size());
    }

    @Test
    void searchKeywords_twoKws_success() {
        Task task1 = new ToDo("task 1");
        Task task2 = new ToDo("task 2");
        Task task3 = new ToDo("haha");
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        assertEquals(3, taskManager.searchKeywords(List.of("task", "ah")).size());
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
