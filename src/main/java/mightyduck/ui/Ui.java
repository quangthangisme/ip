package mightyduck.ui;

import java.io.PrintStream;
import java.util.Scanner;

import mightyduck.command.CommandResult;
import mightyduck.data.task.Task;
import mightyduck.messages.Messages;
import mightyduck.utils.Pair;

/**
 * Handles user interface operations such as reading commands from the user and displaying results
 * or feedback messages.
 */
public class Ui {

    /**
     * The {@link Scanner} used to read user input from the console.
     */
    private final Scanner scanner;

    /**
     * The {@link PrintStream} used to display output to the console.
     */
    private final PrintStream printer;

    /**
     * Creates a new {@link Ui} object with the specified scanner and printer.
     *
     * @param scanner The scanner used to read input from the user.
     * @param printer The printer used to display output to the user.
     */
    public Ui(Scanner scanner, PrintStream printer) {
        this.scanner = scanner;
        this.printer = printer;
    }

    /**
     * Creates a new {@link Ui} object with the default system input and output.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
        this.printer = System.out;
    }

    /**
     * Reads a command input by the user from the console.
     *
     * @return The command input as a string.
     */
    public String readCommand() {
        this.printer.print("> ");
        return this.scanner.nextLine().trim();
    }

    /**
     * Displays the result of a command execution to the user. This method prints the feedback
     * message and then displays the list of tasks affected by the command, if applicable.
     *
     * @param res The result of the command execution, containing feedback and affected tasks.
     */
    public void displayCommandResult(CommandResult res) {
        this.printer.println(res.feedback());
        for (Pair<Integer, Task> taskPair : res.tasks()) {
            this.printer.println("\t" + (taskPair.index() + 1) + ". " + taskPair.element());
        }
    }

    /**
     * Prints the welcome message to the user when the application starts.
     */
    public void printWelcomeMessage() {
        this.printer.println(Messages.WELCOME);
    }

    /**
     * Prints an error message indicating that the application initialization failed.
     */
    public void printInitFailedMessage() {
        this.printer.println(Messages.FAILED_INIT);
    }

    /**
     * Prints an error message indicating that saving tasks failed.
     */
    public void printSavingFailedMessage() {
        this.printer.println(Messages.FAILED_SAVE);
    }

    /**
     * Prints an exception message to the user.
     *
     * @param message The exception message to be displayed.
     */
    public void printException(String message) {
        this.printer.println(Messages.EXCEPTION + message);
    }
}
