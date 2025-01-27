package mightyduck.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mightyduck.command.CommandResult;
import mightyduck.data.task.Task;
import mightyduck.data.task.type.ToDo;
import mightyduck.utils.Pair;

public class UiTest {
    private ByteArrayOutputStream outputStream;
    private Ui ui;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        PrintStream testPrinter = new PrintStream(outputStream);
        Scanner testScanner = new Scanner("test input\n");
        ui = new Ui(testScanner, testPrinter);
    }

    @Test
    void testDisplayCommandResult() {
        Task task1 = new ToDo("Buy groceries");
        Task task2 = new ToDo("Finish homework");
        task2.mark();

        List<Pair<Integer, Task>> tasks = List.of(
                new Pair<>(0, task1),
                new Pair<>(1, task2)
        );

        CommandResult commandResult = new CommandResult("Tasks:", tasks);
        ui.displayCommandResult(commandResult);

        String expectedOutput = """
                Tasks:
                \t1. [T][ ] Buy groceries
                \t2. [T][X] Finish homework
                """
                .replaceAll("\\n|\\r\\n", System.lineSeparator());
        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }
}
