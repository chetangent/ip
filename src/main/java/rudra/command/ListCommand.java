package rudra.command;

import java.util.ArrayList;

import rudra.storage.Storage;
import rudra.task.Task;
import rudra.ui.Ui;

/**
 * Shows all tasks currently in the list.
 */
public class ListCommand extends Command {
    @Override
    public void execute(ArrayList<Task> tasks, Ui ui, Storage storage) {
        ui.showTaskList(tasks);
    }
}
