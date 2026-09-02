package rudra.ui;

import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

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
    private final Consumer<String> output;

    /**
     * Creates a UI that reads commands from standard input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
        this.output = System.out::println;
    }

    /**
     * Creates a UI that sends chatbot messages to the given output handler.
     *
     * @param output Handler for chatbot output messages.
     */
    public Ui(Consumer<String> output) {
        this.scanner = null;
        this.output = output;
    }

    /**
     * Prints the startup banner and greeting.
     */
    public void showWelcome() {
        show(LINE);
        show(BANNER);
        show("Hello! I'm Rudra.");
        show("What can I do for you?");
        show(LINE);
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
        show("Bye. Hope to see you again soon!");
        show(LINE);
    }

    /**
     * Prints the current task list.
     *
     * @param tasks Tasks to display.
     */
    public void showTaskList(List<Task> tasks) {
        show("Here are the tasks in your list:");
        printTaskCollection(tasks);
        show(LINE);
    }

    /**
     * Prints the tasks whose descriptions match a search keyword.
     *
     * @param matchingTasks Matching tasks to display.
     */
    public void showMatchingTasks(List<Task> matchingTasks) {
        if (matchingTasks.isEmpty()) {
            show("I couldn't find any matching tasks.");
            show(LINE);
            return;
        }

        show("Here are the matching tasks in your list:");
        printTaskCollection(matchingTasks);
        show(LINE);
    }

    private void printTaskCollection(List<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            show((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Prints the confirmation shown after a task is added.
     *
     * @param task Added task.
     * @param updatedTaskCount Task count after the addition.
     */
    public void showTaskAdded(Task task, int updatedTaskCount) {
        show("Got it. I've added this task:");
        show(task.toString());
        show("Now you have " + updatedTaskCount + " tasks in the list.");
        show(LINE);
    }

    /**
     * Prints the confirmation shown after a task is marked done.
     *
     * @param task Updated task.
     */
    public void showTaskMarked(Task task) {
        show("Nice! I've marked this task as done:");
        show(task.toString());
        show(LINE);
    }

    /**
     * Prints the confirmation shown after a task is marked not done.
     *
     * @param task Updated task.
     */
    public void showTaskUnmarked(Task task) {
        show("OK, I've marked this task as not done yet:");
        show(task.toString());
        show(LINE);
    }

    /**
     * Prints the confirmation shown after a task is deleted.
     *
     * @param removedTask Deleted task.
     * @param updatedTaskCount Task count after the deletion.
     */
    public void showTaskDeleted(Task removedTask, int updatedTaskCount) {
        show("Noted. I've removed this task:");
        show(removedTask.toString());
        show("Now you have " + updatedTaskCount + " tasks in the list.");
        show(LINE);
    }

    /**
     * Prints an error message followed by the divider line.
     *
     * @param message Error message to display.
     */
    public void showError(String message) {
        show(message);
        show(LINE);
    }

    /**
     * Prints a startup warning about corrupted saved tasks.
     *
     * @param skippedTaskCount Number of skipped tasks.
     */
    public void showCorruptedTaskWarning(int skippedTaskCount) {
        show("Warning: I skipped " + skippedTaskCount + " corrupted saved task(s).");
        show(LINE);
    }

    /**
     * Prints the startup message shown when loading saved tasks fails.
     *
     * @param message Storage error message.
     */
    public void showLoadingError(String message) {
        show(message);
        show("I'm starting with an empty task list instead.");
        show(LINE);
    }

    private void show(String message) {
        this.output.accept(message);
    }
}
