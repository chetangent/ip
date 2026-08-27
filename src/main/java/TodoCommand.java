import java.util.ArrayList;

/**
 * Adds a todo task to the task list.
 */
public class TodoCommand extends Command {
    private final String description;

    /**
     * Creates a command that adds a todo with the given description.
     *
     * @param description Todo description.
     */
    public TodoCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(ArrayList<Task> tasks, Ui ui, Storage storage) throws RudraException {
        Task todoTask = new ToDo(this.description);
        addTask(tasks, todoTask, storage);
        ui.showTaskAdded(todoTask, tasks.size());
    }
}
