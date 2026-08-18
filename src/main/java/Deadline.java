/**
 * Represents a task that should be completed by a specific time.
 */
public class Deadline extends Task {
    private final String by;

    /**
     * Creates a deadline task with a description and due time.
     *
     * @param description Description of the task.
     * @param by Due time entered by the user.
     */
    public Deadline(String description, String by) {
        super(TaskType.DEADLINE, description);
        this.by = by;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + this.by + ")";
    }
}
