package mightyduck;

import java.util.List;

import mightyduck.command.Command;
import mightyduck.command.CommandResult;
import mightyduck.command.CommandResultType;
import mightyduck.command.Parser;
import mightyduck.data.task.TaskManager;
import mightyduck.exception.InvalidCommandException;
import mightyduck.exception.InvalidStoragePathException;
import mightyduck.exception.InvalidValueException;
import mightyduck.exception.StorageLoadException;
import mightyduck.exception.StorageWriteException;
import mightyduck.messages.Messages;
import mightyduck.storage.Storage;

/**
 * The entry point for the MightyDuck application, which handles user commands, task management, and
 * storage operations.
 */
public class MightyDuck {
    private static final String STORAGE_PATH = "./data/mightyduck.txt";

    private final Storage storage;
    private final TaskManager taskManager;
    private final Parser parser;

    /**
     * Creates a {@code MightyDuck} instance.
     *
     * @throws InvalidStoragePathException If the file path is invalid.
     * @throws StorageLoadException        If an error occurs while loading the file.
     */
    public MightyDuck() throws InvalidStoragePathException, StorageLoadException {
        storage = new Storage(STORAGE_PATH);
        taskManager = storage.load();
        parser = new Parser(taskManager);
    }

    /**
     * Parses and runs the given command.
     *
     * @param commandStr The command the user inputted.
     * @return The result of the execution.
     */
    public CommandResult runCommand(String commandStr) {
        try {
            Command command = parser.parse(commandStr);
            storage.save(taskManager);
            return command.execute();
        } catch (StorageWriteException e) {
            return new CommandResult(
                    CommandResultType.TERMINATING_ERROR,
                    Messages.EXCEPTION + e.getMessage(),
                    List.of()
            );
        } catch (InvalidValueException | InvalidCommandException e) {
            return new CommandResult(
                    CommandResultType.ERROR,
                    Messages.EXCEPTION + e.getMessage(),
                    List.of()
            );
        }
    }
}
