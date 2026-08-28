package rudra.task;

import java.util.ArrayList;
import java.util.List;

/*
* Got codex to create this class based on the partial code
* as well as integrate this into Rudra.
* */

/**
 * Represents a task with a description and completion status.
 */
public class Task {
    private final TaskType taskType;
    private final String description;
    private boolean isDone;

    /**
     * Creates a task that is not done yet.
     *
     * @param taskType Type of task being created.
     * @param description Description of the task.
     */
    public Task(TaskType taskType, String description) {
        this.taskType = taskType;
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns the status icon used when displaying the task.
     *
     * @return {@code X} if the task is done, otherwise a space.
     */
    public String getStatusIcon() {
        return this.isDone ? "X" : " ";
    }

    /**
     * Returns the task description text entered by the user.
     *
     * @return Task description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the task type for storage and display purposes.
     *
     * @return Task type of this task.
     */
    public TaskType getTaskType() {
        return this.taskType;
    }

    /**
     * Returns whether this task has been marked as done.
     *
     * @return {@code true} when the task is done, otherwise {@code false}.
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Converts this task into a plain-text storage line.
     *
     * @return Text representation suitable for saving to disk.
     */
    public String toStorageString() {
        List<String> storageFields = getStorageFields();
        for (int i = 0; i < storageFields.size(); i++) {
            storageFields.set(i, escapeStorageField(storageFields.get(i)));
        }

        return String.join(" | ", storageFields);
    }

    /**
     * Returns the fields that should be stored on disk for this task.
     *
     * @return Storage fields for this task in file order.
     */
    protected List<String> getStorageFields() {
        ArrayList<String> storageFields = new ArrayList<>();
        storageFields.add(this.taskType.getDisplayCode());
        storageFields.add(this.isDone ? "1" : "0");
        storageFields.add(this.description);
        return storageFields;
    }

    /**
     * Escapes storage separators so task details can be loaded back reliably.
     *
     * @param value Raw field value.
     * @return Escaped field value.
     */
    protected static String escapeStorageField(String value) {
        return value.replace("\\", "\\\\").replace("|", "\\|");
    }

    /**
     * Returns the user-facing text for this task.
     *
     * @return Formatted task description with type and completion status.
     */
    @Override
    public String toString() {
        return "[" + this.taskType.getDisplayCode() + "][" + getStatusIcon() + "] " + this.description;
    }
}
