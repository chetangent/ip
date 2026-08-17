import java.util.Scanner;

/**
 * Rudra is a simple chatbot that echoes user commands until asked to exit.
 */
public class Rudra {
    private static final String LINE = "____________________________________________________________";

    /**
     * Starts the chatbot and handles user input until the user enters {@code bye}.
     *
     * @param args Command-line arguments, which are not used.
     */
    public static void main(String[] args) {
        String banner = " ____            _            \n"
                + "|  _ \\ _   _  __| |_ __ __ _ \n"
                + "| |_) | | | |/ _` | '__/ _` |\n"
                + "|  _ <| |_| | (_| | | | (_| |\n"
                + "|_| \\_\\\\__,_|\\__,_|_|  \\__,_|\n";

        System.out.println(LINE);
        System.out.println(banner);
        System.out.println("Hello! I'm Rudra.");
        System.out.println("What can I do for you?");
        System.out.println(LINE);

        /* Used Codex to generate this logic block as I had the main
        idea but forgot how to collect and use user input in Java.
         */
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            if ("bye".equals(command)) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(LINE);
                break;
            }

            System.out.println(command);
            System.out.println(LINE);
        }
    }
}
