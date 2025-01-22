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

        while (true) {
            String line = sc.nextLine();
            String command = "";
            if (line != null && !line.isEmpty()) {
                String[] parts = line.split(" ");
                if (parts.length > 0) {
                    command = parts[0];
                }
            }

            if (command.equals("bye")) {
                System.out.println("""
                    Farewell, citizens of Duckville!
                    This is Mighty Duck, flying off to new adventures!
                ================================================================
                """);
                break;
            }

            if (command.equals("list")) {
                int i = 0;
                for (Task task: tasks) {
                    i++;
                    System.out.println("\t" + i + "." + task);
                }
            } else if (command.equals("mark")) {
                int index = 0;
                String[] parts = line.split(" ");
                if (parts.length > 1) {
                    try {
                        index = Integer.parseInt(parts[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("\tInvalid number: " + parts[1]);
                    }
                }
                Task task = tasks.get(index - 1);
                task.mark();
                System.out.printf("""
                            Quack-cellent job, citizen! The task is completed.
                                %s
                        """, task);
            } else if (command.equals("unmark")) {
                int index = 0;
                String[] parts = line.split(" ");
                if (parts.length > 1) {
                    try {
                        index = Integer.parseInt(parts[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("\tInvalid number: " + parts[1]);
                    }
                }
                Task task = tasks.get(index - 1);
                task.unmark();
                System.out.printf("""
                            No worries! Waddle at your own pace.
                                %s
                        """, task);
            } else {
                tasks.add(new Task(line));
                System.out.println("\t" + "added: " + line);
            }

            System.out.println();
        }
    }
}
