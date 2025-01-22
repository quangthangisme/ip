import task.*;

import java.util.*;

public class MightyDuck {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Task> tasks = new ArrayList<>();

        System.out.println("""
                ================================================================
                    Greetings, citizens of Duckville! \
                This is Mighty Duck!
                    How can I serve you today?
                """);

        label:
        while (true) {
            String line = sc.nextLine();
            String[] parts = line.split(" ", 2);
            String command = parts[0];
            String argument = parts.length > 1 ? parts[1] : "";

            switch (command) {
                case "bye":
                    System.out.println("""
                                Farewell, citizens of Duckville!
                                This is Mighty Duck, flying off to new \
                            adventures!
                            ===================================================\
                            =============
                            """);
                    break label;
                case "list":
                    System.out.println("\tHere are the tasks in your list:");
                    int i = 0;
                    for (Task task : tasks) {
                        i++;
                        System.out.println("\t" + i + "." + task);
                    }
                    break;
                case "mark": {
                    int index = 0;
                    try {
                        index = Integer.parseInt(argument);
                    } catch (NumberFormatException e) {
                        System.out.println("\tInvalid number: " + argument);
                    }
                    Task task = tasks.get(index - 1);
                    task.mark();
                    System.out.printf("""
                                Quack-cellent job, citizen! \
                                The task is completed.
                                    %s
                            """, task);
                    break;
                }
                case "unmark": {
                    int index = 0;
                    try {
                        index = Integer.parseInt(argument);
                    } catch (NumberFormatException e) {
                        System.out.println("\tInvalid number: " + argument);
                    }
                    Task task = tasks.get(index - 1);
                    task.unmark();
                    System.out.printf("""
                                No worries! Waddle at your own pace.
                                    %s
                            """, task);
                    break;
                }
                case "todo": {
                    Task todo = new ToDo(argument);
                    tasks.add(todo);
                    printCf("Don't duck the todo list!", todo, tasks.size());
                    break;
                }
                case "deadline": {
                    String[] argParts = argument.split(" /by ");
                    String name = argParts[0];
                    String dl = argParts[1];
                    Task dlTask = new Deadline(name, dl);
                    tasks.add(dlTask);
                    printCf("The deadline will come quack-ly!", dlTask,
                            tasks.size());
                    break;
                }
                case "event": {
                    String[] argParts = argument.split(" /from ");
                    String name = argParts[0];
                    String[] fromToParts = argParts[1].split(" /to ");
                    String fromTime = fromToParts[0];
                    String toTime = fromToParts[1];
                    Task event = new Event(name, fromTime, toTime);
                    tasks.add(event);
                    printCf("The event is going to be a real splash!", event,
                            tasks.size());
                    break;
                }
            }

            System.out.println();
        }
    }

    private static void printCf(String message, Task task, int size) {
        System.out.printf("""
                            %s
                                %s
                            You have %d task%s in the list.
                        """,
                message, task, size, size == 1 ? "" : "s");
    }
}
