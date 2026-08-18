import java.util.ArrayList;
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
        ArrayList<Task> tasks = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            if ("bye".equals(command)) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(LINE);
                break;
            }

            try {
                handleCommand(command, tasks);
            } catch (RudraException e) {
                System.out.println(e.getMessage());
                System.out.println(LINE);
            }
        }
    }
/*
* I used Codex to refactor this part and split the logic into
* 2 during the exception handling part, originally it was one
* chunk and now there is the handle command part.
* */
    private static void handleCommand(String command, ArrayList<Task> tasks) throws RudraException {
        String[] parts = command.split(" ", 2);

        if ("list".equals(command)) {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "." + tasks.get(i));
            }
            System.out.println(LINE);
            return;
        }

        if ("mark".equals(parts[0])) {
            int taskIndex = parseTaskNumber(parts, tasks.size());
            tasks.get(taskIndex).markAsDone();
            System.out.println("Nice! I've marked this task as done:");
            System.out.println(tasks.get(taskIndex));
            System.out.println(LINE);
            return;
        }

        if ("unmark".equals(parts[0])) {
            int taskIndex = parseTaskNumber(parts, tasks.size());
            tasks.get(taskIndex).markAsNotDone();
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println(tasks.get(taskIndex));
            System.out.println(LINE);
            return;
        }

        if ("delete".equals(parts[0])) {
            int taskIndex = parseTaskNumber(parts, tasks.size());
            Task removedTask = tasks.remove(taskIndex);
            System.out.println("Noted. I've removed this task:");
            System.out.println(removedTask);
            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
            System.out.println(LINE);
            return;
        }

        if ("todo".equals(parts[0])) {
            String description = requireDescription(parts, "todo");
            Task newTask = new ToDo(description);
            tasks.add(newTask);
            printTaskAdded(newTask, tasks.size());
            return;
        }

        if ("deadline".equals(parts[0])) {
            String descriptionAndBy = requireDescription(parts, "deadline");
            String[] deadlineParts = descriptionAndBy.split(" /by ", 2);
            if (deadlineParts.length < 2 || deadlineParts[0].isBlank() || deadlineParts[1].isBlank()) {
                throw new RudraException("Please use: deadline DESCRIPTION /by WHEN");
            }
            Task newTask = new Deadline(deadlineParts[0], deadlineParts[1]);
            tasks.add(newTask);
            printTaskAdded(newTask, tasks.size());
            return;
        }

        if ("event".equals(parts[0])) {
            String descriptionAndTime = requireDescription(parts, "event");
            String[] eventParts = descriptionAndTime.split(" /from | /to ", 3);
            if (eventParts.length < 3 || eventParts[0].isBlank()
                    || eventParts[1].isBlank() || eventParts[2].isBlank()) {
                throw new RudraException("Please use: event DESCRIPTION /from START /to END");
            }
            Task newTask = new Event(eventParts[0], eventParts[1], eventParts[2]);
            tasks.add(newTask);
            printTaskAdded(newTask, tasks.size());
            return;
        }

        throw new RudraException(
                "I don't recognize that command yet. Try todo, deadline, event, list, mark, unmark, or delete.");
    }

    private static int parseTaskNumber(String[] parts, int taskCount) throws RudraException {
        if (parts.length <= 1 || parts[1].isBlank()) {
            throw new RudraException("Please include a task number.");
        }

        try {
            int taskNumber = Integer.parseInt(parts[1]);
            if (taskNumber < 1 || taskNumber > taskCount) {
                throw new RudraException("That task number is out of range.");
            }
            return taskNumber - 1;
        } catch (NumberFormatException e) {
            throw new RudraException("Task numbers should be whole numbers.");
        }
    }

    private static String requireDescription(String[] parts, String taskType) throws RudraException {
        if (parts.length <= 1 || parts[1].isBlank()) {
            throw new RudraException("The description of a " + taskType + " cannot be empty.");
        }
        return parts[1];
    }

    private static void printTaskAdded(Task task, int updatedTaskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + updatedTaskCount + " tasks in the list.");
        System.out.println(LINE);
    }
}
