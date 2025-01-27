package mightyduck.exception;

/**
 * Represents an exception that is thrown when an invalid storage path is encountered. This
 * exception is used to indicate that the specified path for storage operations is invalid or cannot
 * be accessed.
 */
public class InvalidStoragePathException extends Exception {

    /**
     * Constructs a new {@code InvalidStoragePathException} with the specified detail message.
     *
     * @param message The detail message to be included with the exception.
     */
    public InvalidStoragePathException(String message) {
        super(message);
    }
}
