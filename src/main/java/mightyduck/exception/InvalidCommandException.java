package mightyduck.exception;

/**
 * Represents an exception that is thrown when an invalid command is encountered. This exception is
 * used to indicate that the user input does not match the expected format or is not a recognized
 * command in the application.
 */
public class InvalidCommandException extends Exception {

    /**
     * Constructs a new {@code InvalidCommandException} with the specified detail message.
     *
     * @param message The detail message to be included with the exception.
     */
    public InvalidCommandException(String message) {
        super(message);
    }
}
