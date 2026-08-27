import java.util.ArrayList;

/**
 * Adds an event task to the task list.
 */
public class EventCommand extends Command {
    private final String description;
    private final TaskDateTime from;
    private final TaskDateTime to;

    /**
     * Creates a command that adds an event task.
     *
     * @param description Event description.
     * @param from Event start time.
     * @param to Event end time.
     */
    public EventCommand(String description, TaskDateTime from, TaskDateTime to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    @Override
    public void execute(ArrayList<Task> tasks, Ui ui, Storage storage) throws RudraException {
        Task eventTask = new Event(this.description, this.from, this.to);
        addTask(tasks, eventTask, storage);
        ui.showTaskAdded(eventTask, tasks.size());
    }
}
