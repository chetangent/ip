package rudra;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import rudra.command.Command;
import rudra.command.ExitCommand;
import rudra.exception.RudraException;
import rudra.parser.Parser;
import rudra.storage.Storage;
import rudra.task.Task;
import rudra.ui.Ui;

/**
 * JavaFX interface for interacting with Rudra through the existing command language.
 */
public class RudraGui extends Application {
    private static final String DATA_FILE_PATH = "data/rudra.txt";

    private final ArrayList<Task> tasks = new ArrayList<>();
    private final ArrayList<String> pendingMessages = new ArrayList<>();
    private final VBox conversation = new VBox(12);

    private Ui ui;
    private Storage storage;
    private TextField commandField;
    private Label taskCountLabel;
    private ScrollPane conversationScrollPane;

    /**
     * Creates and shows Rudra's primary application window.
     *
     * @param stage JavaFX stage that hosts the interface.
     */
    @Override
    public void start(Stage stage) {
        this.ui = new Ui(this.pendingMessages::add);
        this.storage = new Storage(DATA_FILE_PATH);

        BorderPane root = new BorderPane();
        root.getStyleClass().add("app-shell");
        root.setLeft(createSidebar());
        root.setCenter(createChatPane());

        Scene scene = new Scene(root, 1050, 700);
        scene.getStylesheets().add(getClass().getResource("/rudra/ui/rudra.css").toExternalForm());
        stage.setTitle("Rudra | Task companion");
        stage.setMinWidth(760);
        stage.setMinHeight(560);
        stage.setScene(scene);

        loadTasks();
        showPendingReply();
        addMessage(createWelcomeMessage(), "bot-message");
        stage.show();
        this.commandField.requestFocus();
    }

    private VBox createSidebar() {
        Label brand = new Label("RUDRA");
        brand.getStyleClass().add("brand");
        Label subtitle = new Label("A calmer way to plan");
        subtitle.getStyleClass().add("subtitle");
        this.taskCountLabel = new Label();
        this.taskCountLabel.getStyleClass().add("task-count");

        Label guideTitle = new Label("COMMAND GUIDE");
        guideTitle.getStyleClass().add("guide-title");
        Label guide = new Label("todo buy milk\n"
                + "deadline submit report /by 2026-09-10\n"
                + "event team sync /from 2026-09-08 1400 /to 2026-09-08 1500\n"
                + "list, find KEYWORD, mark NUMBER");
        guide.setWrapText(true);
        guide.getStyleClass().add("guide");

        Button showTasksButton = new Button("Show my tasks");
        showTasksButton.getStyleClass().add("secondary-button");
        showTasksButton.setMaxWidth(Double.MAX_VALUE);
        showTasksButton.setOnAction(event -> submitCommand("list"));

        VBox sidebar = new VBox(14, brand, subtitle, this.taskCountLabel, guideTitle, guide, showTasksButton);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(270);
        return sidebar;
    }

    private VBox createChatPane() {
        Label title = new Label("Your task conversation");
        title.getStyleClass().add("conversation-title");
        Label hint = new Label("Use natural task commands. Rudra will save changes automatically.");
        hint.getStyleClass().add("conversation-hint");

        VBox heading = new VBox(3, title, hint);
        heading.getStyleClass().add("conversation-heading");

        this.conversation.getStyleClass().add("conversation");
        this.conversationScrollPane = new ScrollPane(this.conversation);
        this.conversationScrollPane.setFitToWidth(true);
        this.conversationScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.conversationScrollPane.getStyleClass().add("chat-scroll");
        VBox.setVgrow(this.conversationScrollPane, Priority.ALWAYS);

        this.commandField = new TextField();
        this.commandField.setPromptText("Try: todo read chapter 4");
        this.commandField.setOnAction(event -> submitCommand(this.commandField.getText()));
        HBox.setHgrow(this.commandField, Priority.ALWAYS);

        Button sendButton = new Button("Send");
        sendButton.getStyleClass().add("send-button");
        sendButton.setOnAction(event -> submitCommand(this.commandField.getText()));
        HBox composer = new HBox(10, this.commandField, sendButton);
        composer.getStyleClass().add("composer");

        VBox chatPane = new VBox(16, heading, this.conversationScrollPane, composer);
        chatPane.getStyleClass().add("chat-pane");
        return chatPane;
    }

    private void loadTasks() {
        try {
            Storage.LoadResult loadResult = this.storage.loadTasks();
            this.tasks.addAll(loadResult.getTasks());
            if (loadResult.getSkippedTaskCount() > 0) {
                this.ui.showCorruptedTaskWarning(loadResult.getSkippedTaskCount());
            }
        } catch (RudraException e) {
            this.ui.showLoadingError(e.getMessage());
        }
        updateTaskCount();
    }

    private void submitCommand(String rawCommand) {
        String command = rawCommand.trim();
        if (command.isEmpty()) {
            return;
        }

        addMessage(command, "user-message");
        this.commandField.clear();
        this.pendingMessages.clear();

        try {
            Command parsedCommand = Parser.isExitCommand(command) ? new ExitCommand() : Parser.parse(command);
            parsedCommand.execute(this.tasks, this.ui, this.storage);
            if (parsedCommand.isExit()) {
                this.commandField.setDisable(true);
            }
        } catch (RudraException e) {
            this.ui.showError(e.getMessage());
        }

        updateTaskCount();
        showPendingReply();
    }

    private void showPendingReply() {
        if (this.pendingMessages.isEmpty()) {
            return;
        }
        addMessage(String.join(System.lineSeparator(), this.pendingMessages), "bot-message");
        this.pendingMessages.clear();
    }

    private void addMessage(String text, String styleClass) {
        Label message = new Label(text);
        message.setWrapText(true);
        message.setMaxWidth(620);
        message.getStyleClass().add("message");
        message.getStyleClass().add(styleClass);

        HBox messageRow = new HBox(message);
        messageRow.getStyleClass().add("message-row");
        messageRow.setAlignment("user-message".equals(styleClass) ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        this.conversation.getChildren().add(messageRow);
        Platform.runLater(() -> {
            this.conversationScrollPane.applyCss();
            this.conversationScrollPane.layout();
            this.conversationScrollPane.setVvalue(1.0);
        });
    }

    private String createWelcomeMessage() {
        return "Welcome to Rudra!\n\n"
                + "How to use this chat\n"
                + "Type a command below, then press Enter or select Send. Your changes are saved automatically.\n\n"
                + "Commands\n"
                + "todo DESCRIPTION\n"
                + "deadline DESCRIPTION /by YYYY-MM-DD [HHmm]\n"
                + "event DESCRIPTION /from YYYY-MM-DD HHmm /to YYYY-MM-DD HHmm\n"
                + "list\n"
                + "find KEYWORD\n"
                + "mark NUMBER\n"
                + "unmark NUMBER\n"
                + "delete NUMBER\n"
                + "bye\n\n"
                + "Example: deadline submit report /by 2026-09-10";
    }

    private void updateTaskCount() {
        this.taskCountLabel.setText(this.tasks.size() + (this.tasks.size() == 1 ? " task" : " tasks") + " in view");
    }
}
