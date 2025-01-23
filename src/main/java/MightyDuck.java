import java.util.*;

public class MightyDuck {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();
        Printer taskView = new Printer();

        printWelcomeMessage();

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            String[] parts = input.split(" ", 2);
            String commandStr = parts[0].toUpperCase();
            String argument = parts.length > 1 ? parts[1] : "";

            if ("BYE".equals(commandStr)) {
                printFarewellMessage();
                break;
            }

            try {
                Command command = Command.valueOf(commandStr);
                try {
                    command.execute(argument, taskManager, taskView);
                } catch (IllegalArgumentException e) {
                    System.out.println("Something's a-fowl! " + e.getMessage());
                }
            } catch (IllegalArgumentException e) {
                System.out.println("A wild goose chase! I can't help " +
                        "you with that.");
            }

            System.out.println();
        }
    }

    private static void printWelcomeMessage() {
        System.out.println("""
                ================================================================
                Greetings, citizens of Duckville! This is Mighty Duck!
                How can I serve you today?
                """);
    }

    private static void printFarewellMessage() {
        System.out.println("""
                Farewell, citizens of Duckville!
                This is Mighty Duck, flying off to new adventures!
                ================================================================
                """);
    }
}
