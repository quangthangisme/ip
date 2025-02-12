package mightyduck.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import mightyduck.exception.InvalidValueException;
import mightyduck.exception.StorageLoadException;
import mightyduck.task.Deadline;
import mightyduck.task.Event;
import mightyduck.task.Task;
import mightyduck.task.TaskManager;
import mightyduck.task.ToDo;

public class TaskDecoderTest {
    @Test
    void testDecodeTasks_validTasks() throws StorageLoadException,
            InvalidValueException {
        List<String> encodedTasks = Arrays.asList(
                "T|1||hello|",
                "D|0||dltest|2025-01-26 12:20",
                "E|1|ba|eventtest|2025-01-26 12:20,2025-01-27 12:20"
        );

        TaskManager taskManager = TaskDecoder.decodeTasks(encodedTasks);

        assertEquals(3, taskManager.getTaskCount());

        Task task1 = taskManager.getTask(0);
        assertInstanceOf(ToDo.class, task1);
        assertEquals("hello", task1.getName());
        assertTrue(task1.isMarked());

        Task task2 = taskManager.getTask(1);
        assertInstanceOf(Deadline.class, task2);
        assertEquals("dltest", task2.getName());
        assertFalse(task2.isMarked());

        Task task3 = taskManager.getTask(2);
        assertInstanceOf(Event.class, task3);
        assertEquals("eventtest", task3.getName());
        assertTrue(task3.isMarked());
    }

    @Test
    void testDecodeTasks_invalidFormat() {
        List<String> encodedTasks = Arrays.asList(
                "InvalidFormatTask",
                "T||Buy milk|",
                "E||1|Meeting|"
        );

        assertThrows(StorageLoadException.class, () -> TaskDecoder.decodeTasks(encodedTasks));
    }

    @Test
    void testDecodeTasks_invalidSignature() {
        List<String> encodedTasks = List.of("X|1||Unknown|");

        assertThrows(StorageLoadException.class, () -> TaskDecoder.decodeTasks(encodedTasks));
    }

    @Test
    void testDecodeTasks_invalidCompletionStatus() {
        List<String> encodedTasks = List.of("T|2||Task|");

        assertThrows(StorageLoadException.class, () -> TaskDecoder.decodeTasks(encodedTasks));
    }

    @Test
    void testDecodeTasks_incompleteEventInfo() {
        List<String> encodedTasks = List.of("E|1||Event|2025-01-28 10:00");

        assertThrows(StorageLoadException.class, () -> TaskDecoder.decodeTasks(encodedTasks));
    }
}

