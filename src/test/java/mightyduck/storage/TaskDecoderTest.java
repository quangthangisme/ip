package mightyduck.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import mightyduck.data.task.Task;
import mightyduck.data.task.TaskManager;
import mightyduck.data.task.type.Deadline;
import mightyduck.data.task.type.Event;
import mightyduck.data.task.type.ToDo;
import mightyduck.exception.InvalidValueException;
import mightyduck.exception.StorageLoadException;

public class TaskDecoderTest {
    @Test
    void testDecodeTasks_validTasks() throws StorageLoadException,
            InvalidValueException {
        List<String> encodedTasks = Arrays.asList(
                "T\t1\tBuy milk",
                "D\t0\tSubmit assignment\t2025-01-28 10:20",
                "E\t1\tTeam meeting\t2025-01-26 10:00\t2025-01-26 12:00"
        );

        TaskManager taskManager = TaskDecoder.decodeTasks(encodedTasks);

        assertEquals(3, taskManager.getTaskCount());

        Task task1 = taskManager.getTask(0);
        assertInstanceOf(ToDo.class, task1);
        assertEquals("Buy milk", task1.getName());
        assertTrue(task1.isMarked());

        Task task2 = taskManager.getTask(1);
        assertInstanceOf(Deadline.class, task2);
        assertEquals("Submit assignment", task2.getName());
        assertFalse(task2.isMarked());

        Task task3 = taskManager.getTask(2);
        assertInstanceOf(Event.class, task3);
        assertEquals("Team meeting", task3.getName());
        assertTrue(task3.isMarked());
    }

    @Test
    void testDecodeTasks_invalidFormat() {
        List<String> encodedTasks = Arrays.asList(
                "InvalidFormatTask",
                "T\tBuy milk",
                "E\t1\tMeeting"
        );

        assertThrows(StorageLoadException.class,
                () -> TaskDecoder.decodeTasks(encodedTasks));
    }

    @Test
    void testDecodeTasks_invalidSignature() {
        List<String> encodedTasks = List.of("X\t1\tUnknown");

        assertThrows(StorageLoadException.class,
                () -> TaskDecoder.decodeTasks(encodedTasks));
    }

    @Test
    void testDecodeTasks_invalidCompletionStatus() {
        List<String> encodedTasks = List.of("T\t2\tTask");

        assertThrows(StorageLoadException.class,
                () -> TaskDecoder.decodeTasks(encodedTasks));
    }

    @Test
    void testDecodeTasks_incompleteEventInfo() {
        List<String> encodedTasks = List.of("E\t1\tEvent\t2025-01-28 10:00");

        assertThrows(StorageLoadException.class,
                () -> TaskDecoder.decodeTasks(encodedTasks));
    }
}

