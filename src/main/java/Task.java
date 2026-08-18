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

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return "[" + this.taskType.getDisplayCode() + "][" + getStatusIcon() + "] " + this.description;
    }
}
