package mightyduck.storage;

import static mightyduck.utils.DateTimeUtils.FORMATTER;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import mightyduck.exception.StorageLoadException;
import mightyduck.task.Deadline;
import mightyduck.task.Event;
import mightyduck.task.Task;
import mightyduck.task.TaskManager;
import mightyduck.task.ToDo;
import mightyduck.utils.Messages;

/**
 * Decodes a list of encoded task strings into a {@link TaskManager}. The tasks are represented in a
 * specific format, and this class parses the data to reconstruct the corresponding {@link Task}
 * objects.
 */
public class TaskDecoder {

    /**
     * A map of task signature types to corresponding task parsers. This is used to select the
     * appropriate parser for each task based on its signature.
     */
    private static final Map<String, TaskParser> TASK_PARSERS = Map.of(
            Deadline.SIGNATURE, TaskDecoder::parseDeadline,
            ToDo.SIGNATURE, TaskDecoder::parseToDo,
            Event.SIGNATURE, TaskDecoder::parseEvent
    );

    /**
     * Private constructor to prevent instantiation of this utility class
     */
    private TaskDecoder() {
        throw new UnsupportedOperationException("Utility class should not be instantiated.");
    }

    /**
     * Decodes a list of encoded task strings into a {@link TaskManager} object. Each encoded task
     * string must follow a specific format and the method attempts to reconstruct the {@link Task}
     * objects based on the provided information.
     *
     * @param encodedTasks A list of strings, each representing an encoded task.
     * @return A {@link TaskManager} containing the decoded tasks.
     * @throws StorageLoadException If a task cannot be decoded properly.
     */
    public static TaskManager decodeTasks(List<String> encodedTasks) throws StorageLoadException {
        TaskManager taskManager = new TaskManager();

        for (String encodedTask : encodedTasks) {
            String[] parts = encodedTask.split("\\|", -1);
            validateParts(parts, encodedTask);

            String signature = parts[0];
            boolean isDone = parts[1].equals(Task.STATUS_DONE_STORAGE);
            List<String> tags = Arrays.stream(parts[2].split(",")).filter(s -> !s.isEmpty())
                    .distinct().collect(Collectors.toList());
            String name = parts[3];
            List<String> info = Arrays.stream(parts[4].split(",")).filter(s -> !s.isEmpty())
                    .toList();

            Task task = parseTaskBySignature(signature, name, isDone, tags, info, encodedTask);
            taskManager.addTask(task);
        }

        return taskManager;
    }

    /**
     * Validates the structure of the encoded task string. Checks if there are 5 parts and if the
     * status is either "1" (done) or "0" (not done).
     *
     * @param parts       The split parts of the encoded task string.
     * @param encodedTask The original encoded task string for error reporting.
     * @throws StorageLoadException If the encoded task string is invalid.
     */
    private static void validateParts(String[] parts, String encodedTask)
            throws StorageLoadException {
        if (parts.length != 5 || (!parts[1].equals(Task.STATUS_DONE_STORAGE)
                && !parts[1].equals(Task.STATUS_NOT_DONE_STORAGE))) {
            throw new StorageLoadException(
                    String.format(Messages.INVALID_ENCODED_FORMAT, encodedTask));
        }
    }

    /**
     * Selects the appropriate task parser based on the task signature and parses the task.
     *
     * @param signature   The signature of the task.
     * @param name        The name of the task.
     * @param isDone      The completion status of the task.
     * @param tags        The list of tags associated with the task.
     * @param info        The information associated with the task (e.g., name, date, time).
     * @param encodedTask The original encoded task string for error reporting.
     * @return The parsed {@link Task} object.
     * @throws StorageLoadException If the task format is invalid or cannot be parsed.
     */
    private static Task parseTaskBySignature(String signature, String name, boolean isDone,
                                             List<String> tags, List<String> info,
                                             String encodedTask)
            throws StorageLoadException {
        TaskParser parser = TASK_PARSERS.get(signature);
        if (parser == null) {
            throw new StorageLoadException(String.format(Messages.INVALID_ENCODED_FORMAT,
                    encodedTask));
        }
        return parser.parse(name, isDone, tags, info, encodedTask);
    }

    /**
     * Parses a {@link Deadline} task from the provided information.
     *
     * @param name        The name of the task.
     * @param isDone      The completion status of the task.
     * @param tags        The list of tags associated with the task.
     * @param info        The information associated with the task, containing the deadline.
     * @param encodedTask The original encoded task string for error reporting.
     * @return A {@link Deadline} task object.
     * @throws StorageLoadException If the task is in an invalid format.
     */
    private static Task parseDeadline(String name, boolean isDone, List<String> tags,
                                      List<String> info, String encodedTask)
            throws StorageLoadException {
        if (info.size() != 1) {
            throw new StorageLoadException(String.format(Messages.INVALID_ENCODED_FORMAT,
                    encodedTask));
        }
        try {
            LocalDateTime dlTime = LocalDateTime.parse(info.get(0), FORMATTER);
            return new Deadline(name, isDone, dlTime, tags);
        } catch (DateTimeParseException e) {
            throw new StorageLoadException(e.getMessage());
        }
    }

    /**
     * Parses a {@link ToDo} task from the provided information.
     *
     * @param name        The name of the task.
     * @param isDone      The completion status of the task.
     * @param tags        The list of tags associated with the task.
     * @param info        Additional details related to the task, which is empty for a {@link ToDo}
     *                    task.
     * @param encodedTask The original encoded task string for error reporting.
     * @return A {@link ToDo} task object.
     */
    private static Task parseToDo(String name, boolean isDone, List<String> tags,
                                  List<String> info, String encodedTask)
            throws StorageLoadException {
        if (!info.isEmpty()) {
            throw new StorageLoadException(String.format(Messages.INVALID_ENCODED_FORMAT,
                    encodedTask));
        }
        return new ToDo(name, isDone, tags);
    }

    /**
     * Parses an Event task from the provided information.
     *
     * @param name        The name of the task.
     * @param isDone      The completion status of the task.
     * @param tags        The list of tags associated with the task.
     * @param info        Additional details related to the task, containing the start and the end
     *                    time for the event.
     * @param encodedTask The original encoded task string for error reporting.
     * @return An {@link Event} task object.
     * @throws StorageLoadException If the task is in an invalid format.
     */
    private static Task parseEvent(String name, boolean isDone, List<String> tags,
                                   List<String> info, String encodedTask)
            throws StorageLoadException {
        if (info.size() != 2) {
            throw new StorageLoadException(String.format(Messages.INVALID_ENCODED_FORMAT,
                    encodedTask));
        }

        LocalDateTime startTime;
        LocalDateTime endTime;
        try {
            startTime = LocalDateTime.parse(info.get(0), FORMATTER);
            endTime = LocalDateTime.parse(info.get(1), FORMATTER);
        } catch (DateTimeParseException e) {
            throw new StorageLoadException(e.getMessage());
        }
        if (endTime.isBefore(startTime)) {
            throw new StorageLoadException(String.format(Messages.INVALID_ENCODED_FORMAT,
                    encodedTask));
        }

        return new Event(name, isDone, startTime, endTime, tags);
    }

    /**
     * Functional interface for parsing different task types.
     */
    @FunctionalInterface
    private interface TaskParser {
        /**
         * Parses a task from the provided information.
         *
         * @param name        The name of the task.
         * @param isDone      The completion status of the task.
         * @param tags        The list of tags associated with the task.
         * @param info        The information associated with the task (e.g., name, date, time).
         * @param encodedTask The original encoded task string for error reporting.
         * @return The parsed {@link Task}.
         * @throws StorageLoadException If the task cannot be parsed.
         */
        Task parse(String name, boolean isDone, List<String> tags, List<String> info,
                   String encodedTask) throws StorageLoadException;
    }
}
