package mightyduck;

import mightyduck.command.Command;
import mightyduck.command.CommandResult;
import mightyduck.command.Parser;
import mightyduck.data.task.TaskManager;
import mightyduck.exception.InvalidCommandException;
import mightyduck.exception.InvalidStoragePathException;
import mightyduck.exception.InvalidValueException;
import mightyduck.exception.StorageLoadException;
import mightyduck.exception.StorageWriteException;
import mightyduck.storage.Storage;
import mightyduck.ui.Ui;

public class MightyDuck {
    private static final String STORAGE_PATH = "./data/mightyduck.txt";

    private Storage storage;
    private TaskManager taskManager;
    private Ui ui;
    private Parser parser;

    public static void main(String[] args) {
        new MightyDuck().run();
    }

    public void run() {
        start();

        while (true) {
            try {
                Command command = parser.parse(ui.readCommand());
                CommandResult commandResult = command.execute();
                storage.save(taskManager);
                ui.displayCommandResult(commandResult);
                if (command.isBye()) {
                    break;
                }
            } catch (StorageWriteException e) {
                ui.printSavingFailedMessage();
                throw new RuntimeException(e);
            } catch (InvalidValueException | InvalidCommandException e) {
                ui.printException(e.getMessage());
            }
            System.out.println();
        }
    }

    private void start() {
        this.ui = new Ui();
        try {
            this.storage = new Storage(STORAGE_PATH);
            this.taskManager = this.storage.load();
            this.parser = new Parser(this.taskManager);
            ui.printWelcomeMessage();

        } catch (InvalidStoragePathException | StorageLoadException e) {
            ui.printInitFailedMessage();
            throw new RuntimeException(e);
        }
    }
}
