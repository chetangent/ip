import java.util.ArrayList;

/**
 * Shows all tasks currently in the list.
 */
public class ListCommand extends Command {
    @Override
    public void execute(ArrayList<Task> tasks, Ui ui, Storage storage) {
        ui.showTaskList(tasks);
    }
}
