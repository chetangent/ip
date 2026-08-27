import java.util.ArrayList;

/**
 * Rudra is a simple chatbot that echoes user commands until asked to exit.
 */
public class Rudra {
    private static final String DATA_FILE_PATH = "data/rudra.txt";

    /**
     * Starts the chatbot and handles user input until the user enters {@code bye}.
     *
     * @param args Command-line arguments, which are not used.
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.showWelcome();

        /* Used Codex to generate this base logic block for echo as I had the main
        idea but forgot how to collect and use user input in Java.
         */
        /*
        * Used Codex to split the input for done as unsure on how
        * to implement that as well as converting String to int
        * as well as the error catching
        * */
        Storage storage = new Storage(DATA_FILE_PATH);
        ArrayList<Task> tasks = loadTasksAtStartup(storage, ui);

        while (ui.hasNextCommand()) {
            String command = ui.readCommand();
            if ("bye".equals(command)) {
                ui.showGoodbye();
                break;
            }

            try {
                handleCommand(command, tasks, storage, ui);
            } catch (RudraException e) {
                ui.showError(e.getMessage());
            }
        }
    }
/*
* I used Codex to refactor this part and split the logic into
* 2 during the exception handling part, originally it was one
* chunk and now there is the handle command part.
* */
    private static void handleCommand(String command, ArrayList<Task> tasks, Storage storage, Ui ui)
            throws RudraException {
        String[] parts = command.split(" ", 2);
        CommandWord commandWord = CommandWord.from(parts[0]).orElseThrow(() -> new RudraException(
                "I don't recognize that command yet. Try todo, deadline, event, list, mark, unmark, or delete."));

        if (commandWord == CommandWord.LIST && parts.length == 1) {
            ui.showTaskList(tasks);
            return;
        }
        // Used Codex to suggest and build in Enums
        switch (commandWord) {
        case MARK:
            int taskIndexToMark = parseTaskNumber(parts, tasks.size());
            markTask(tasks, taskIndexToMark, storage, true);
            ui.showTaskMarked(tasks.get(taskIndexToMark));
            return;
        case UNMARK:
            int taskIndexToUnmark = parseTaskNumber(parts, tasks.size());
            markTask(tasks, taskIndexToUnmark, storage, false);
            ui.showTaskUnmarked(tasks.get(taskIndexToUnmark));
            return;
        case DELETE:
            int taskIndexToDelete = parseTaskNumber(parts, tasks.size());
            Task removedTask = deleteTask(tasks, taskIndexToDelete, storage);
            ui.showTaskDeleted(removedTask, tasks.size());
            return;
        case TODO:
            String todoDescription = requireDescription(parts, "todo");
            Task todoTask = new ToDo(todoDescription);
            addTask(tasks, todoTask, storage);
            ui.showTaskAdded(todoTask, tasks.size());
            return;
        case DEADLINE:
            String descriptionAndBy = requireDescription(parts, "deadline");
            String[] deadlineParts = descriptionAndBy.split(" /by ", 2);
            if (deadlineParts.length < 2 || deadlineParts[0].isBlank() || deadlineParts[1].isBlank()) {
                throw new RudraException("Please use: deadline DESCRIPTION /by WHEN");
            }
            Task deadlineTask = new Deadline(deadlineParts[0], TaskDateTime.parse(deadlineParts[1]));
            addTask(tasks, deadlineTask, storage);
            ui.showTaskAdded(deadlineTask, tasks.size());
            return;
        case EVENT:
            String descriptionAndTime = requireDescription(parts, "event");
            String[] eventParts = descriptionAndTime.split(" /from | /to ", 3);
            if (eventParts.length < 3 || eventParts[0].isBlank()
                    || eventParts[1].isBlank() || eventParts[2].isBlank()) {
                throw new RudraException("Please use: event DESCRIPTION /from START /to END");
            }
            Task eventTask = new Event(eventParts[0], TaskDateTime.parse(eventParts[1]),
                    TaskDateTime.parse(eventParts[2]));
            addTask(tasks, eventTask, storage);
            ui.showTaskAdded(eventTask, tasks.size());
            return;
        case LIST:
            break;
        default:
            break;
        }

        throw new RudraException("I don't recognize that command yet. Try todo, deadline, event, list, mark, unmark,"
                + " or delete.");
    }

    private static int parseTaskNumber(String[] parts, int taskCount) throws RudraException {
        if (parts.length <= 1 || parts[1].isBlank()) {
            throw new RudraException("Please include a task number.");
        }

        try {
            int taskNumber = Integer.parseInt(parts[1]);
            if (taskNumber < 1 || taskNumber > taskCount) {
                throw new RudraException("That task number is out of range.");
            }
            return taskNumber - 1;
        } catch (NumberFormatException e) {
            throw new RudraException("Task numbers should be whole numbers.");
        }
    }

    private static String requireDescription(String[] parts, String taskType) throws RudraException {
        if (parts.length <= 1 || parts[1].isBlank()) {
            throw new RudraException("The description of a " + taskType + " cannot be empty.");
        }
        return parts[1];
    }

    /**
     * Loads tasks from disk when the chatbot starts and reports any recoverable storage issues.
     *
     * @param storage Storage helper used to read saved tasks.
     * @param ui UI helper used to print startup messages.
     * @return Task list to use for this session.
     */
    private static ArrayList<Task> loadTasksAtStartup(Storage storage, Ui ui) {
        try {
            Storage.LoadResult loadResult = storage.loadTasks();
            if (loadResult.getSkippedTaskCount() > 0) {
                ui.showCorruptedTaskWarning(loadResult.getSkippedTaskCount());
            }
            return loadResult.getTasks();
        } catch (RudraException e) {
            ui.showLoadingError(e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Adds a task and rolls back the change if saving fails.
     *
     * @param tasks Current task list.
     * @param task Task to add.
     * @param storage Storage helper used to persist the list.
     * @throws RudraException If saving fails.
     */
    private static void addTask(ArrayList<Task> tasks, Task task, Storage storage) throws RudraException {
        tasks.add(task);
        try {
            storage.saveTasks(tasks);
        } catch (RudraException e) {
            tasks.remove(tasks.size() - 1);
            throw new RudraException(e.getMessage() + " Your task list was left unchanged.");
        }
    }

    /**
     * Marks or unmarks a task and rolls back the change if saving fails.
     *
     * @param tasks Current task list.
     * @param taskIndex Index of the task to update.
     * @param storage Storage helper used to persist the list.
     * @param shouldMarkAsDone Whether the task should be marked done.
     * @throws RudraException If saving fails.
     */
    private static void markTask(ArrayList<Task> tasks, int taskIndex, Storage storage, boolean shouldMarkAsDone)
            throws RudraException {
        Task task = tasks.get(taskIndex);
        boolean wasDone = task.isDone();

        if (shouldMarkAsDone) {
            task.markAsDone();
        } else {
            task.markAsNotDone();
        }

        try {
            storage.saveTasks(tasks);
        } catch (RudraException e) {
            if (wasDone) {
                task.markAsDone();
            } else {
                task.markAsNotDone();
            }
            throw new RudraException(e.getMessage() + " Your task list was left unchanged.");
        }
    }

    /**
     * Deletes a task and restores it if saving fails.
     *
     * @param tasks Current task list.
     * @param taskIndex Index of the task to delete.
     * @param storage Storage helper used to persist the list.
     * @return Removed task.
     * @throws RudraException If saving fails.
     */
    private static Task deleteTask(ArrayList<Task> tasks, int taskIndex, Storage storage) throws RudraException {
        Task removedTask = tasks.remove(taskIndex);

        try {
            storage.saveTasks(tasks);
            return removedTask;
        } catch (RudraException e) {
            tasks.add(taskIndex, removedTask);
            throw new RudraException(e.getMessage() + " Your task list was left unchanged.");
        }
    }
}
