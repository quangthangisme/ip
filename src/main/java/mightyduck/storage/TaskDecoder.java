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

public class TaskDecoder {
    public static TaskManager decodeTasks(List<String> encodedTasks)
            throws StorageLoadException {
        TaskManager taskManager = new TaskManager();

        for (String encodedTask : encodedTasks) {
            String[] parts = encodedTask.split("\t");
            if (parts.length < 3) {
                throw new StorageLoadException(String.format(
                        Messages.INVALID_ENCODED_FORMAT, encodedTask));
            }
            if (!parts[1].equals("1") && !parts[1].equals("0")) {
                throw new StorageLoadException(String.format(
                        Messages.INVALID_ENCODED_FORMAT, encodedTask));
            }
            String signature = parts[0];
            boolean isDone = parts[1].equals("1");
            String name = parts[2];

            List<String> addedInfo = new ArrayList<>(
                    Arrays.asList(parts).subList(3, parts.length));

            Task task;
            try {
                switch (signature) {
                case Deadline.SIGNATURE -> {
                    if (addedInfo.isEmpty()) {
                        throw new StorageLoadException(String.format(
                                Messages.INVALID_ENCODED_FORMAT,
                                encodedTask
                        ));
                    }
                    String deadline = addedInfo.get(0);
                    task = new Deadline(name, deadline);
                }
                case ToDo.SIGNATURE -> task = new ToDo(name);
                case Event.SIGNATURE -> {
                    if (addedInfo.size() < 2) {
                        throw new StorageLoadException(String.format(
                                Messages.INVALID_ENCODED_FORMAT,
                                encodedTask
                        ));
                    }
                    String startTime = addedInfo.get(0);
                    String endTime = addedInfo.get(1);
                    task = new Event(name, startTime, endTime);
                }
                default -> throw new StorageLoadException(String.format(
                        Messages.INVALID_ENCODED_FORMAT,
                        encodedTask
                ));
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
