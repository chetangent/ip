package rudra.command;

import java.util.ArrayList;

import rudra.exception.RudraException;
import rudra.storage.Storage;
import rudra.task.Task;
import rudra.ui.Ui;

/**
 * Marks a task as done.
 */
public class MarkCommand extends Command {
    private final int taskIndex;

    /**
     * Creates a command that marks the task at the given zero-based index.
     *
     * @param taskIndex Task index to mark.
     */
    public MarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(ArrayList<Task> tasks, Ui ui, Storage storage) throws RudraException {
        validateTaskIndex(tasks);
        updateTaskStatus(tasks, this.taskIndex, storage, true);
        ui.showTaskMarked(tasks.get(this.taskIndex));
    }

    private void validateTaskIndex(ArrayList<Task> tasks) throws RudraException {
        if (this.taskIndex >= tasks.size()) {
            throw new RudraException("That task number is out of range.");
        }
    }
}
