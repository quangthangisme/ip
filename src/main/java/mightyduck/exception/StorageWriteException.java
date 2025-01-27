package mightyduck.exception;

/**
 * Represents an exception that is thrown when there is an issue writing data to storage. This
 * exception is used to signal errors encountered while writing to storage, such as file write
 * failures or insufficient permissions.
 */
public class StorageWriteException extends Exception {

    /**
     * Constructs a new {@code StorageWriteException} with the specified detail message.
     *
     * @param message The detail message to be included with the exception, explaining the cause of
     *                the failure.
     */
    public StorageWriteException(String message) {
        super(message);
    }
}
