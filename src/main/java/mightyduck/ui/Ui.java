package mightyduck.ui;

import mightyduck.command.CommandResult;
import mightyduck.data.task.Task;
import mightyduck.messages.Messages;
import mightyduck.utils.Pair;

import java.io.PrintStream;
import java.util.Scanner;

public class Ui {
    private final Scanner scanner;
    private final PrintStream printer;

    public Ui(Scanner scanner, PrintStream printer) {
        this.scanner = scanner;
        this.printer = printer;
    }

    public Ui() {
        this.scanner = new Scanner(System.in);
        this.printer = System.out;
    }

    public String readCommand() {
        this.printer.print("> ");
        return this.scanner.nextLine().trim();
    }

    public void displayCommandResult(CommandResult res) {
        this.printer.println(res.feedback());
        for (Pair<Integer, Task> taskPair: res.tasks()) {
            this.printer.println("\t" + (taskPair.index() + 1) + ". "
                    + taskPair.element());
        }
    }

    public void printWelcomeMessage() {
        this.printer.println(Messages.WELCOME);
    }

    public void printInitFailedMessage() {
        this.printer.println(Messages.FAILED_INIT);
    }

    public void printSavingFailedMessage() {
        this.printer.println(Messages.FAILED_SAVE);
    }

    public void printException(String message) {
        this.printer.println(Messages.EXCEPTION + message);
    }
}

