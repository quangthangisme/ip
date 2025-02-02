package mightyduck.messages;

/**
 * Contains user-visible messages used throughout the application.
 */
public class Messages {

    /**
     * Message displayed when the task list is empty.
     */
    public static final String EMPTY_LIST =
            "Woo-hoo! There are no tasks currently. Enjoy your day, citizen!";

    /**
     * Message displayed when listing tasks.
     */
    public static final String LIST = "Here are the tasks in your list:";

    /**
     * Message displayed when a task is marked as completed.
     */
    public static final String MARK = "Quack-cellent job, citizen! The task is completed.";

    /**
     * Message displayed when a task is unmarked (incomplete).
     */
    public static final String UNMARK = "No worries! Waddle at your own pace.";

    /**
     * Message displayed when a task is deleted.
     */
    public static final String DELETE = "One less thing to pond-er about!";

    /**
     * Message displayed when a new task is added.
     */
    public static final String ADD_TASK = "Let's get pro-duck-tive!";

    /**
     * Message displayed when an invalid command is entered.
     */
    public static final String INVALID_COMMAND = "The command is quack! I cannot understand that.";

    /**
     * Message displayed when the user enters a wrong command format.
     */
    public static final String WRONG_COMMAND_FORMAT = "Please use the correct format: %s";

    /**
     * Message displayed when the task index format is incorrect.
     */
    public static final String WRONG_NUMBER_FORMAT = "I cannot parse the index!";

    /**
     * Welcome message displayed when the application starts.
     */
    public static final String WELCOME =
            """
                    Greetings, citizens of Duckville! This is Mighty Duck!
                    How can I serve you today?
                    """;

    /**
     * Goodbye message displayed when the application exits.
     */
    public static final String BYE =
            """
                    Farewell, citizens of Duckville!
                    This is Mighty Duck, flying off to new adventures!
                    """;

    /**
     * Message displayed when file loading fails.
     */
    public static final String FAILED_INIT =
            """
                    Oh duck, the file loading failed.
                    """;

    /**
     * Message displayed when file saving fails.
     */
    public static final String FAILED_SAVE =
            """
                    Oh duck, the file saving failed.
                    """;

    /**
     * General exception message displayed when an error occurs.
     */
    public static final String EXCEPTION = "Something's a-fowl! ";

    /**
     * Message displayed when the task index is out of range.
     */
    public static final String OUT_OF_RANGE_INDEX = "The task number is out of range!";

    /**
     * Message displayed when the end time of a task is before its start time.
     */
    public static final String END_TIME_BEFORE_START_TIME =
            "The end time must be after the start time!";

    /**
     * Message displayed when the time format is invalid during parsing.
     */
    public static final String FAILED_PARSE_TIME =
            "I cannot parse the time format! Expected 'yyyy-MM-dd HH:mm'.";

    /**
     * Message displayed when an encoded task format is invalid.
     */
    public static final String INVALID_ENCODED_FORMAT = "Invalid encoded task format: %s";

    /**
     * Message displayed when at least one matching task was found.
     */
    public static final String FIND = "Here are the matching tasks:";

    /**
     * Message displayed when no matching tasks were found.
     */
    public static final String EMPTY_FIND = "This is a wild goose chase! I cannot find anything.";

    /**
     * Message displayed when the storage file path is invalid.
     */
    public static final String INVALID_STORAGE_PATH = "Storage file should end with '.txt'.";

    /**
     * Message displayed when an error occurs while writing to the storage.
     */
    public static final String WRITE_ERROR = "Error writing to file: %s";

    /**
     * Message displayed when an error occurs while loading data from storage.
     */
    public static final String LOAD_ERROR = "Error loading file: %s";

    private Messages() {
    }
}
