/*
* Already baked in the logic into code during previous levels
* so used Codex to create and integrate exception class
* */

/**
 * Represents an error caused by invalid user input to Rudra.
 */
public class RudraException extends Exception {
    /**
     * Creates a Rudra-specific exception with a user-facing message.
     *
     * @param message Explanation of what went wrong.
     */
    public RudraException(String message) {
        super(message);
    }
}
