import command.Command;
import command.CommandResult;
import command.Parser;
import data.TaskManager;
import exception.InvalidCommandException;
import exception.InvalidValueException;
import storage.Storage;
import exception.InvalidStoragePathException;
import exception.StorageLoadException;
import exception.StorageWriteException;
import ui.Ui;

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
