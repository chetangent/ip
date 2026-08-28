package rudra.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Task}.
 */
public class TaskTest {
    @Test
    public void matchesKeyword_descriptionContainsKeyword_returnsTrue() {
        Task task = new ToDo("return book to library");

        assertTrue(task.matchesKeyword("book"));
    }

    @Test
    public void matchesKeyword_differentLetterCase_returnsTrue() {
        Task task = new ToDo("Read Book");

        assertTrue(task.matchesKeyword("book"));
    }

    @Test
    public void matchesKeyword_descriptionDoesNotContainKeyword_returnsFalse() {
        Task task = new ToDo("submit assignment");

        assertFalse(task.matchesKeyword("book"));
    }
}
