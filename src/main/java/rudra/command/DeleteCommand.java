package rudra.command;

import java.util.ArrayList;

import rudra.exception.RudraException;
import rudra.storage.Storage;
import rudra.task.Task;
import rudra.ui.Ui;

/**
 * Deletes a task from the task list.
 */
public class DeleteCommand extends Command {
    private final int taskIndex;

    /**
     * Creates a command that deletes the task at the given zero-based index.
     *
     * @param taskIndex Task index to delete.
     */
    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(ArrayList<Task> tasks, Ui ui, Storage storage) throws RudraException {
        validateTaskIndex(tasks);
        Task removedTask = deleteTask(tasks, this.taskIndex, storage);
        ui.showTaskDeleted(removedTask, tasks.size());
    }

    private void validateTaskIndex(ArrayList<Task> tasks) throws RudraException {
        if (this.taskIndex >= tasks.size()) {
            throw new RudraException("That task number is out of range.");
        }
    }
}
