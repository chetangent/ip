package rudra.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import rudra.task.ToDo;

/**
 * Tests for {@link Ui} output adapters.
 */
public class UiTest {
    @Test
    public void showTaskAdded_customOutput_receivesConfirmationLines() {
        List<String> messages = new ArrayList<>();
        Ui ui = new Ui(messages::add);

        ui.showTaskAdded(new ToDo("read chapter 4"), 1);

        assertEquals(List.of(
                "Got it. I've added this task:",
                "[T][ ] read chapter 4",
                "Now you have 1 tasks in the list.",
                "____________________________________________________________"), messages);
    }
}
