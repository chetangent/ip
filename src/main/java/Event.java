import java.util.List;

/**
 * Represents an event task with a start and end time.
 */
public class Event extends Task {
    private final String from;
    private final String to;

    /**
     * Creates an event task with the given time range.
     *
     * @param description Description of the event.
     * @param from Start time entered by the user.
     * @param to End time entered by the user.
     */
    public Event(String description, String from, String to) {
        super(TaskType.EVENT, description);
        this.from = from;
        this.to = to;
    }

    @Override
    protected List<String> getStorageFields() {
        List<String> storageFields = super.getStorageFields();
        storageFields.add(this.from);
        storageFields.add(this.to);
        return storageFields;
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + this.from + " to: " + this.to + ")";
    }
}
