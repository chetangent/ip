import java.util.List;

/**
 * Represents a task that should be completed by a specific time.
 */
public class Deadline extends Task {
    private final TaskDateTime by;

    /**
     * Creates a deadline task with a description and due time.
     *
     * @param description Description of the task.
     * @param by Due time entered by the user.
     */
    public Deadline(String description, TaskDateTime by) {
        super(TaskType.DEADLINE, description);
        this.by = by;
    }

    @Override
    protected List<String> getStorageFields() {
        List<String> storageFields = super.getStorageFields();
        storageFields.add(this.by.toStorageString());
        return storageFields;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + this.by.toDisplayString() + ")";
    }
}
