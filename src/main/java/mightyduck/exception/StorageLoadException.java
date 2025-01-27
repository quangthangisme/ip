package mightyduck.exception;

/**
 * Represents an exception that is thrown when there is an issue loading data from storage. This
 * exception is used to signal errors encountered while reading from storage, such as file loading
 * failures or data corruption.
 */
public class StorageLoadException extends Exception {

    /**
     * Constructs a new {@code StorageLoadException} with the specified detail message.
     *
     * @param message The detail message to be included with the exception, explaining the cause of
     *                the failure.
     */
    public StorageLoadException(String message) {
        super(message);
    }
}
