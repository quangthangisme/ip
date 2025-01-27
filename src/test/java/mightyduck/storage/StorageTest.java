package mightyduck.storage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mightyduck.data.task.TaskManager;
import mightyduck.data.task.type.ToDo;
import mightyduck.exception.InvalidStoragePathException;
import mightyduck.exception.InvalidValueException;
import mightyduck.exception.StorageLoadException;
import mightyduck.exception.StorageWriteException;

class StorageTest {
    private static final String TEST_FILE_PATH = "./data/test.txt";
    private Storage storage;

    @BeforeEach
    void setUp() throws InvalidStoragePathException {
        storage = new Storage(TEST_FILE_PATH);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(TEST_FILE_PATH));
    }

    @Test
    void constructor_invalidFilePath_throwsInvalidStoragePathException() {
        assertThrows(InvalidStoragePathException.class,
                () -> new Storage("invalid_path"));
    }

    @Test
    void save_validTaskManager_success() throws IOException {
        TaskManager taskManager = new TaskManager();
        taskManager.addTask(new ToDo("task"));

        assertDoesNotThrow(() -> storage.save(taskManager));
        List<String> lines = Files.readAllLines(Path.of(TEST_FILE_PATH));

        assertEquals(1, lines.size());
        assertTrue(lines.get(0).contains("task"));
    }

    @Test
    void save_ioError_throwsStorageWriteException() throws IOException {
        File file = new File(TEST_FILE_PATH);
        assertTrue(file.createNewFile());
        assertTrue(file.setWritable(false));

        TaskManager taskManager = new TaskManager();
        taskManager.addTask(new ToDo("task"));

        assertThrows(StorageWriteException.class,
                () -> storage.save(taskManager));
        assertTrue(file.setWritable(true));
    }

    @Test
    void load_nonExistentFile_returnsEmptyTaskManager()
            throws StorageLoadException {
        TaskManager loadedTaskManager = storage.load();
        assertNotNull(loadedTaskManager);
        assertEquals(0, loadedTaskManager.getTaskCount());
    }

    @Test
    void load_existingFile_success() throws StorageWriteException,
            StorageLoadException, InvalidValueException {
        TaskManager taskManager = new TaskManager();
        taskManager.addTask(new ToDo("task 1"));
        taskManager.addTask(new ToDo("task 2"));
        storage.save(taskManager);

        TaskManager loadedTaskManager = storage.load();
        assertNotNull(loadedTaskManager);
        assertEquals(2, loadedTaskManager.getTaskCount());
        assertEquals("task 1", loadedTaskManager.getTask(0).getName());
        assertEquals("task 2", loadedTaskManager.getTask(1).getName());
    }
}

