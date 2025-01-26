import data.TaskManager;
import storage.Storage;
import storage.exception.InvalidStoragePathException;
import storage.exception.StorageLoadException;
import storage.exception.StorageWriteException;

import java.util.*;

public class MightyDuck {
    private static final String STORAGE_PATH = "./data/mightyduck.txt";
    private Storage storage;
    private TaskManager taskManager;
    private Printer printer;
    private Scanner scanner;

    public static void main(String[] args) {
        new MightyDuck().run(args);
    }

    public void run(String[] args) {
        start();

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            String[] parts = input.split(" ", 2);
            String commandStr = parts[0].toUpperCase();
            String argument = parts.length > 1 ? parts[1] : "";

            if ("BYE".equals(commandStr)) {
                printer.printFarewellMessage();
                break;
            }

            try {
                Command command = Command.valueOf(commandStr);
                try {
                    command.execute(argument, taskManager, printer);
                    storage.save(taskManager);
                } catch (IllegalArgumentException e) {
                    System.out.println("Something's a-fowl! " + e.getMessage());
                } catch (StorageWriteException e) {
                    printer.printSavingFailedMessage();
                    throw new RuntimeException(e);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("A wild goose chase! I can't help " +
                        "you with that.");
            }

            System.out.println();
        }
    }

    private void start() {
        this.printer = new Printer();
        try {
            this.storage = new Storage(STORAGE_PATH);
            this.taskManager = this.storage.load();
            this.scanner = new Scanner(System.in);
            printer.printWelcomeMessage();

        } catch (InvalidStoragePathException | StorageLoadException e) {
            printer.printInitFailedMessage();
            throw new RuntimeException(e);
        }
    }
}
