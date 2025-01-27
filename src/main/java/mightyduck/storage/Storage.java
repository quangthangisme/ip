package mightyduck.storage;

import mightyduck.data.task.TaskManager;
import mightyduck.exception.InvalidStoragePathException;
import mightyduck.exception.StorageLoadException;
import mightyduck.exception.StorageWriteException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Storage {
    public final Path path;

    public Storage(String filePath) throws InvalidStoragePathException {
        this.path = Paths.get(filePath);
        if (!this.path.toString().endsWith(".txt")) {
            throw new InvalidStoragePathException(
                    "Storage file should end with '.txt'");
        }
    }

    public void save(TaskManager taskManager) throws StorageWriteException {
        try {
            List<String> encodedTasks = taskManager.encodeTasks();
            Files.createDirectories(this.path.getParent());
            Files.write(this.path, encodedTasks);
        } catch (IOException ioe) {
            throw new StorageWriteException("Error writing to file: "
                    + this.path);
        }
    }

    public TaskManager load() throws StorageLoadException {
        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            return new TaskManager();
        }

        try {
            return TaskDecoder.decodeTasks(Files.readAllLines(this.path));
        } catch (IOException e) {
            throw new StorageLoadException("Error loading file: " + this.path);
        }
    }
}
