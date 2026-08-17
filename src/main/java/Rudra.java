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

        /* Used Codex to generate this base logic block for echo as I had the main
        idea but forgot how to collect and use user input in Java.
         */
        /*
        * Used Codex to split the input for done as unsure on how
        * to implement that as well as converting String to int
        * as well as the error catching
        * */
        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            String[] parts = command.split(" ", 2);
            if ("bye".equals(command)) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(LINE);
                break;
            }

            else if ("list".equals(command)) {
                System.out.println("Here are the tasks in your list:");
                for (int i=1; i < taskCount + 1; i++) {
                    System.out.println(i + "." + tasks[i - 1]);
                }
                System.out.println(LINE);
                continue;
            }

            else if ("mark".equals(parts[0])) {
                if (parts.length > 1) {
                    try {
                        int num = Integer.parseInt(parts[1]);
                        if (num <= taskCount && num >= 1) {
                            tasks[num - 1].markAsDone();
                            System.out.println("Nice! I've marked this task as done:");
                            System.out.println(tasks[num - 1]);
                            System.out.println(LINE);
                        } else {
                            System.out.println("Oops, that task doesn't exist. Please choose another number.");
                            System.out.println(LINE);
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid task number.");
                        System.out.println(LINE);
                        continue;
                    }
                } else {
                    System.out.println("Oops, please enter a number as well.");
                    System.out.println(LINE);
                    continue;
                }
                continue;
            }

            else if ("unmark".equals(parts[0])) {
                if (parts.length > 1) {
                    try {
                        int num = Integer.parseInt(parts[1]);
                        if (num <= taskCount && num >= 1) {
                            tasks[num - 1].markAsNotDone();
                            System.out.println("OK, I've marked this task as not done yet:");
                            System.out.println(tasks[num - 1]);
                            System.out.println(LINE);
                        } else {
                            System.out.println("Oops, that task doesn't exist. Please choose another number.");
                            System.out.println(LINE);
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid task number.");
                        System.out.println(LINE);
                        continue;
                    }
                } else {
                    System.out.println("Oops, please enter a number as well.");
                    System.out.println(LINE);
                    continue;
                }
                continue;
            }

            else if ("todo".equals(parts[0])) {
                if (parts.length <= 1 || parts[1].isBlank()) {
                    System.out.println("Oops, the description of a todo cannot be empty.");
                    System.out.println(LINE);
                    continue;
                }
                tasks[taskCount] = new ToDo(parts[1]);
                System.out.println("Got it. I've added this task:");
                System.out.println(tasks[taskCount]);
                taskCount++;
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println(LINE);
                continue;
            }

            else if ("deadline".equals(parts[0])) {
                if (parts.length <= 1 || parts[1].isBlank()) {
                    System.out.println("Oops, the description of a deadline cannot be empty.");
                    System.out.println(LINE);
                    continue;
                }
                String[] reparts = parts[1].split(" /by ", 2);
                if (reparts.length < 2 || reparts[0].isBlank() || reparts[1].isBlank()) {
                    System.out.println("Oops, use the format: deadline DESCRIPTION /by WHEN");
                    System.out.println(LINE);
                    continue;
                }
                tasks[taskCount] = new Deadline(reparts[0], reparts[1]);
                System.out.println("Got it. I've added this task:");
                System.out.println(tasks[taskCount]);
                taskCount++;
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println(LINE);
                continue;
            }

            else if ("event".equals(parts[0])) {
                if (parts.length <= 1 || parts[1].isBlank()) {
                    System.out.println("Oops, the description of an event cannot be empty.");
                    System.out.println(LINE);
                    continue;
                }
                String[] reparts = parts[1].split(" /from | /to ", 3);
                if (reparts.length < 3 || reparts[0].isBlank()
                        || reparts[1].isBlank() || reparts[2].isBlank()) {
                    System.out.println("Oops, use the format: event DESCRIPTION /from START /to END");
                    System.out.println(LINE);
                    continue;
                }
                tasks[taskCount] = new Event(reparts[0], reparts[1], reparts[2]);
                System.out.println("Got it. I've added this task:");
                System.out.println(tasks[taskCount]);
                taskCount++;
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println(LINE);
                continue;
            }

            System.out.println("Oops, I don't recognize that command.");
            System.out.println(LINE);
        }
    }
}
