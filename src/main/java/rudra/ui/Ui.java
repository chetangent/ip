package rudra.ui;

import java.util.List;
import java.util.Scanner;

import rudra.task.Task;

/**
 * Handles console input and output for the chatbot.
 */
public class Ui {
    private static final String LINE = "____________________________________________________________";
    private static final String BANNER = " ____            _            \n"
            + "|  _ \\ _   _  __| |_ __ __ _ \n"
            + "| |_) | | | |/ _` | '__/ _` |\n"
            + "|  _ <| |_| | (_| | | | (_| |\n"
            + "|_| \\_\\\\__,_|\\__,_|_|  \\__,_|\n";

    private final Scanner scanner;

    /**
     * Creates a UI that reads commands from standard input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prints the startup banner and greeting.
     */
    public void showWelcome() {
        System.out.println(LINE);
        System.out.println(BANNER);
        System.out.println("Hello! I'm Rudra.");
        System.out.println("What can I do for you?");
        System.out.println(LINE);
    }

    /**
     * Returns whether another input line is available.
     *
     * @return True if another command can be read.
     */
    public boolean hasNextCommand() {
        return this.scanner.hasNextLine();
    }

    /**
     * Reads the next user command from standard input.
     *
     * @return Raw command text.
     */
    public String readCommand() {
        return this.scanner.nextLine();
    }

    /**
     * Prints the farewell message shown when the chatbot exits.
     */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    /**
     * Prints the current task list.
     *
     * @param tasks Tasks to display.
     */
    public void showTaskList(List<Task> tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
        System.out.println(LINE);
    }

    /**
     * Prints the confirmation shown after a task is added.
     *
     * @param task Added task.
     * @param updatedTaskCount Task count after the addition.
     */
    public void showTaskAdded(Task task, int updatedTaskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + updatedTaskCount + " tasks in the list.");
        System.out.println(LINE);
    }

    /**
     * Prints the confirmation shown after a task is marked done.
     *
     * @param task Updated task.
     */
    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task);
        System.out.println(LINE);
    }

    /**
     * Prints the confirmation shown after a task is marked not done.
     *
     * @param task Updated task.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(task);
        System.out.println(LINE);
    }

    /**
     * Prints the confirmation shown after a task is deleted.
     *
     * @param removedTask Deleted task.
     * @param updatedTaskCount Task count after the deletion.
     */
    public void showTaskDeleted(Task removedTask, int updatedTaskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println(removedTask);
        System.out.println("Now you have " + updatedTaskCount + " tasks in the list.");
        System.out.println(LINE);
    }

    /**
     * Prints an error message followed by the divider line.
     *
     * @param message Error message to display.
     */
    public void showError(String message) {
        System.out.println(message);
        System.out.println(LINE);
    }

    /**
     * Prints a startup warning about corrupted saved tasks.
     *
     * @param skippedTaskCount Number of skipped tasks.
     */
    public void showCorruptedTaskWarning(int skippedTaskCount) {
        System.out.println("Warning: I skipped " + skippedTaskCount + " corrupted saved task(s).");
        System.out.println(LINE);
    }

    /**
     * Prints the startup message shown when loading saved tasks fails.
     *
     * @param message Storage error message.
     */
    public void showLoadingError(String message) {
        System.out.println(message);
        System.out.println("I'm starting with an empty task list instead.");
        System.out.println(LINE);
    }
}
