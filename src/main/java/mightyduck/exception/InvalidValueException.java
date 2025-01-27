package mightyduck.exception;

/**
 * Represents an exception that is thrown when an invalid value is encountered. This exception is
 * used to indicate that a provided value (e.g., input, argument, etc.) is invalid or does not meet
 * the expected criteria.
 */
public class InvalidValueException extends Exception {

    /**
     * Constructs a new {@code InvalidValueException} with the specified detail message.
     *
     * @param message The detail message to be included with the exception, explaining the invalid
     *                value.
     */
    public InvalidValueException(String message) {
        super(message);
    }
}
