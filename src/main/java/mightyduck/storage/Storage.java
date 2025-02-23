package mightyduck.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import mightyduck.exception.InvalidStoragePathException;
import mightyduck.exception.StorageLoadException;
import mightyduck.exception.StorageWriteException;
import mightyduck.task.TaskManager;
import mightyduck.utils.Messages;

/**
 * Represents a storage system that handles reading and writing task data to and from a file. The
 * file must have a ".txt" extension.
 */
public class Storage {

    /**
     * The path to the storage file where task data is saved and loaded from.
     */
    public final Path path;

    /**
     * Constructs a new {@code Storage} object with the specified file path. The file path must have
     * a ".txt" extension.
     *
     * @param filePath The path to the storage file.
     * @throws InvalidStoragePathException If the file path does not end with ".txt".
     */
    public Storage(String filePath) throws InvalidStoragePathException {
        assert filePath != null : "File path should not be null";
        path = Paths.get(filePath);
        if (!path.toString().endsWith(".txt")) {
            throw new InvalidStoragePathException(Messages.INVALID_STORAGE_PATH);
        }
    }

    /**
     * Saves the tasks from the provided {@link TaskManager} to the storage.
     *
     * @param taskManager The {@link TaskManager} containing tasks to be saved.
     * @throws StorageWriteException If an error occurs while writing to the file.
     */
    public void save(TaskManager taskManager) throws StorageWriteException {
        try {
            List<String> encodedTasks = taskManager.encodeTasks();
            assert encodedTasks != null : "Encoded task list should not be null";
            Files.createDirectories(path.getParent());
            Files.write(path, encodedTasks);
        } catch (IOException ioe) {
            throw new StorageWriteException(String.format(Messages.WRITE_ERROR, path));
        }
    }

    /**
     * Loads tasks from the storage file into a {@link TaskManager}. If the file does not exist or
     * is not a regular file, an empty {@link TaskManager} is returned.
     *
     * @return The {@link TaskManager} containing the loaded tasks.
     * @throws StorageLoadException If an error occurs while loading the file.
     */
    public TaskManager load() throws StorageLoadException {
        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            return new TaskManager();
        }

        assert Files.isReadable(path) : "Storage file should be readable: " + path;
        try {
            return TaskDecoder.decodeTasks(Files.readAllLines(path));
        } catch (IOException e) {
            throw new StorageLoadException(String.format(Messages.LOAD_ERROR, path));
        }
    }
}
