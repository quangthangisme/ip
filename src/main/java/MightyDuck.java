import java.util.Scanner;

public class MightyDuck {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("""
                ================================================================
                    Greetings, citizens of Duckville! \
                This is Mighty Duck!
                    How can I serve you today?
                """);

        while (true) {
            String command = sc.nextLine();

            if (command.equals("bye")) {
                break;
            }

            System.out.println("\t" + command + "\n");
        }

        System.out.println("""
                    Farewell, citizens of Duckville!
                    This is Mighty Duck, flying off to new adventures!
                ================================================================
                """);
    }
}
