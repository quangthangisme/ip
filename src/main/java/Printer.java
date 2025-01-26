import data.task.Task;

import java.util.List;

public class Printer {
    public void displayTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Woo-hoo! There are no tasks currently. " +
                    "Enjoy your day, citizen!");
            return;
        }

        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("    %d. %s%n", i + 1, tasks.get(i));
        }
    }

    public void displayTaskAdded(Task task, int totalTasks) {
        System.out.printf("""
                Let's get pro-duck-tive!
                    %s
                You now have %d task%s.
                """, task, totalTasks, totalTasks == 1 ? "" : "s");
    }

    public void displayTaskDeleted(Task task, int totalTasks) {
        System.out.printf("""
                One less thing to pond-er about!
                    %s
                You now have %d task%s.
                """, task, totalTasks, totalTasks == 1 ? "" : "s");
    }

    public void displayTaskMarked(Task task, boolean completed) {
        if (completed) {
            System.out.printf("""
                    Quack-cellent job, citizen! The task is completed.
                        %s
                    """, task);
        } else {
            System.out.printf("""
                    No worries! Waddle at your own pace.
                        %s
                    """, task);
        }
    }

    public void printWelcomeMessage() {
        System.out.println("""
                ================================================================
                Greetings, citizens of Duckville! This is Mighty Duck!
                How can I serve you today?
                """);
    }

    public void printFarewellMessage() {
        System.out.println("""
                Farewell, citizens of Duckville!
                This is Mighty Duck, flying off to new adventures!
                ================================================================
                """);
    }

    public void printInitFailedMessage() {
        System.out.println("""
                ================================================================
                Oh duck, the file loading failed.
                ================================================================
                """);
    }

    public void printSavingFailedMessage() {
        System.out.println("""
                Oh duck, the file saving failed.
                ================================================================
                """);
    }
}

