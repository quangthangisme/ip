import java.util.*;

public class MightyDuck {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> tasks = new ArrayList<>();

        System.out.println("""
                ================================================================
                    Greetings, citizens of Duckville! \
                This is Mighty Duck!
                    How can I serve you today?
                """);

        while (true) {
            String command = sc.nextLine();

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
                for (String task: tasks) {
                    i++;
                    System.out.println("\t" + i + ". " + task);
                }
            } else {
                tasks.add(command);
                System.out.println("\t" + "added: " + command);
            }

            System.out.println();
        }
    }
}
