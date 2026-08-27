package rudra.command;

import java.util.ArrayList;

import rudra.exception.RudraException;
import rudra.storage.Storage;
import rudra.task.Task;
import rudra.ui.Ui;

/**
 * Represents one user command that can be executed against the chatbot state.
 */
public abstract class Command {
    /**
     * Executes the command against the current task list and collaborators.
     *
     * @param tasks Current task list.
     * @param ui UI helper used to show output.
     * @param storage Storage helper used to persist changes.
     * @throws RudraException If the command cannot be completed.
     */
    public abstract void execute(ArrayList<Task> tasks, Ui ui, Storage storage) throws RudraException;

    /**
     * Returns whether running this command should end the chatbot session.
     *
     * @return True if this command exits the application.
     */
    public boolean isExit() {
        return false;
    }

    /**
     * Adds a task and rolls back the change if saving fails.
     *
     * @param tasks Current task list.
     * @param task Task to add.
     * @param storage Storage helper used to persist the list.
     * @throws RudraException If saving fails.
     */
    protected void addTask(ArrayList<Task> tasks, Task task, Storage storage) throws RudraException {
        tasks.add(task);
        try {
            storage.saveTasks(tasks);
        } catch (RudraException e) {
            tasks.remove(tasks.size() - 1);
            throw new RudraException(e.getMessage() + " Your task list was left unchanged.");
        }
    }

    /**
     * Marks or unmarks a task and rolls back the change if saving fails.
     *
     * @param tasks Current task list.
     * @param taskIndex Index of the task to update.
     * @param storage Storage helper used to persist the list.
     * @param shouldMarkAsDone Whether the task should be marked done.
     * @throws RudraException If saving fails.
     */
    protected void updateTaskStatus(ArrayList<Task> tasks, int taskIndex, Storage storage, boolean shouldMarkAsDone)
            throws RudraException {
        Task task = tasks.get(taskIndex);
        boolean wasDone = task.isDone();

        if (shouldMarkAsDone) {
            task.markAsDone();
        } else {
            task.markAsNotDone();
        }

        try {
            storage.saveTasks(tasks);
        } catch (RudraException e) {
            if (wasDone) {
                task.markAsDone();
            } else {
                task.markAsNotDone();
            }
            throw new RudraException(e.getMessage() + " Your task list was left unchanged.");
        }
    }

    /**
     * Deletes a task and restores it if saving fails.
     *
     * @param tasks Current task list.
     * @param taskIndex Index of the task to delete.
     * @param storage Storage helper used to persist the list.
     * @return Removed task.
     * @throws RudraException If saving fails.
     */
    protected Task deleteTask(ArrayList<Task> tasks, int taskIndex, Storage storage) throws RudraException {
        Task removedTask = tasks.remove(taskIndex);

        try {
            storage.saveTasks(tasks);
            return removedTask;
        } catch (RudraException e) {
            tasks.add(taskIndex, removedTask);
            throw new RudraException(e.getMessage() + " Your task list was left unchanged.");
        }
    }
}
