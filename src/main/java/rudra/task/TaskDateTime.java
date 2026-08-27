package rudra.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import rudra.exception.RudraException;

/**
 * Represents a task date or date-time value in parsed form.
 */
public class TaskDateTime {
    private static final DateTimeFormatter DATE_INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter DATE_TIME_INPUT_WITH_COLON_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter DATE_DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy");
    private static final DateTimeFormatter DATE_TIME_DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy h:mma");

    private final LocalDateTime value;
    private final boolean hasTime;

    /**
     * Creates a parsed task date or date-time.
     *
     * @param value Parsed date-time value.
     * @param hasTime Whether the original input included a time component.
     */
    public TaskDateTime(LocalDateTime value, boolean hasTime) {
        this.value = value;
        this.hasTime = hasTime;
    }

    /**
     * Parses a user-entered task date or date-time.
     *
     * @param rawValue User input to parse.
     * @return Parsed task date or date-time.
     * @throws RudraException If the input does not match the accepted formats.
     */
    public static TaskDateTime parse(String rawValue) throws RudraException {
        try {
            if (rawValue.contains(":")) {
                return new TaskDateTime(LocalDateTime.parse(rawValue, DATE_TIME_INPUT_WITH_COLON_FORMATTER), true);
            }

            if (rawValue.contains(" ")) {
                return new TaskDateTime(LocalDateTime.parse(rawValue, DATE_TIME_INPUT_FORMATTER), true);
            }

            LocalDate date = LocalDate.parse(rawValue, DATE_INPUT_FORMATTER);
            return new TaskDateTime(date.atStartOfDay(), false);
        } catch (DateTimeParseException e) {
            throw new RudraException("Please enter dates as yyyy-mm-dd or yyyy-mm-dd HHmm.");
        }
    }

    /**
     * Returns the normalized form used in the save file.
     *
     * @return Save-file representation.
     */
    public String toStorageString() {
        if (this.hasTime) {
            return this.value.format(DATE_TIME_INPUT_FORMATTER);
        }
        return this.value.toLocalDate().format(DATE_INPUT_FORMATTER);
    }

    /**
     * Returns the user-facing formatted date or date-time.
     *
     * @return Display text for the chatbot output.
     */
    public String toDisplayString() {
        if (this.hasTime) {
            return this.value.format(DATE_TIME_DISPLAY_FORMATTER);
        }
        return this.value.toLocalDate().format(DATE_DISPLAY_FORMATTER);
    }
}
