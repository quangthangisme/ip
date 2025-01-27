package mightyduck.storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mightyduck.data.task.Task;
import mightyduck.data.task.TaskManager;
import mightyduck.data.task.type.Deadline;
import mightyduck.data.task.type.Event;
import mightyduck.data.task.type.ToDo;
import mightyduck.exception.InvalidValueException;
import mightyduck.exception.StorageLoadException;
import mightyduck.messages.Messages;

/**
 * Decodes a list of encoded task strings into a {@link TaskManager}. The tasks are represented in a
 * specific format, and this class parses the data to reconstruct the corresponding {@link Task}
 * objects.
 */
public class TaskDecoder {

    private TaskDecoder() {
    }

    /**
     * Decodes a list of encoded task strings into a {@link TaskManager} object. Each encoded task
     * string must follow a specific format, and the method attempts to reconstruct the {@link Task}
     * objects based on the provided information.
     *
     * @param encodedTasks A list of strings, each representing an encoded task.
     * @return A {@link TaskManager} containing the decoded tasks.
     * @throws StorageLoadException If any of the tasks are in an invalid format or if a task cannot
     *                              be decoded properly.
     */
    public static TaskManager decodeTasks(List<String> encodedTasks) throws StorageLoadException {
        TaskManager taskManager = new TaskManager();

        for (String encodedTask : encodedTasks) {
            String[] parts = encodedTask.split("\t");
            if (parts.length < 3) {
                throw new StorageLoadException(
                        String.format(Messages.INVALID_ENCODED_FORMAT, encodedTask));
            }
            if (!parts[1].equals("1") && !parts[1].equals("0")) {
                throw new StorageLoadException(
                        String.format(Messages.INVALID_ENCODED_FORMAT, encodedTask));
            }
            String signature = parts[0];
            boolean isDone = parts[1].equals("1");
            String name = parts[2];

            List<String> addedInfo = new ArrayList<>(Arrays.asList(parts).subList(3, parts.length));

            Task task;
            try {
                switch (signature) {
                case Deadline.SIGNATURE -> {
                    if (addedInfo.isEmpty()) {
                        throw new StorageLoadException(
                                String.format(Messages.INVALID_ENCODED_FORMAT, encodedTask));
                    }
                    String deadline = addedInfo.get(0);
                    task = new Deadline(name, deadline);
                }
                case ToDo.SIGNATURE -> task = new ToDo(name);
                case Event.SIGNATURE -> {
                    if (addedInfo.size() < 2) {
                        throw new StorageLoadException(
                                String.format(Messages.INVALID_ENCODED_FORMAT, encodedTask));
                    }
                    String startTime = addedInfo.get(0);
                    String endTime = addedInfo.get(1);
                    task = new Event(name, startTime, endTime);
                }
                default -> throw new StorageLoadException(
                        String.format(Messages.INVALID_ENCODED_FORMAT, encodedTask));
                }
            } catch (InvalidValueException e) {
                throw new StorageLoadException(e.getMessage());
            }

            if (isDone) {
                task.mark();
            }
            taskManager.addTask(task);
        }
        return taskManager;
    }
}
