package mightyduck.utils;

import static mightyduck.utils.DateTimeUtils.PATTERN;

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
    public static final String LIST = "Here's what's on your agen-duck:";

    /**
     * Message displayed when a task is marked as completed.
     */
    public static final String MARK = "Egg-cellent job, citizen!";

    /**
     * Message displayed when a task is unmarked (incomplete).
     */
    public static final String UNMARK = "No worries! Waddle at your own pace.";

    /**
     * Message displayed when a task is deleted.
     */
    public static final String DELETE = "No need to pond-er so many things!";

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
     * General exception message displayed when an error occurs.
     */
    public static final String EXCEPTION = "Something's a-fowl! ";

    /**
     * Message displayed when the task index is out of range.
     */
    public static final String OUT_OF_RANGE_INDEX = "The task number is out of range: %s";

    /**
     * Message displayed when the end time of a task is before its start time.
     */
    public static final String END_TIME_BEFORE_START_TIME =
            "The end time must be after the start time!";

    /**
     * Message displayed when the time format is invalid during parsing.
     */
    public static final String FAILED_PARSE_TIME =
            "I cannot parse the time format! Expected '" + PATTERN + "'.";

    /**
     * Message displayed when an encoded task format is invalid.
     */
    public static final String INVALID_ENCODED_FORMAT = "Invalid encoded task format: %s";

    /**
     * Message displayed when at least one matching task was found.
     */
    public static final String FIND = "Here's what I can find:";

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

    /**
     * Message displayed when some tasks is tagged.
     */
    public static final String TAG = "Duck duck goose, all tasks are tagged!";

    /**
     * Message displayed when attempting to add an already existing tag to a task.
     */
    public static final String TAG_ALREADY_EXISTED =
            "Duck duck goose, this task is already tagged with %s: %s";

    /**
     * Message displayed when attempting to remove a non-existent tag from a task.
     */
    public static final String TAG_NOT_FOUND =
            "Duck duck goose, this task is not tagged with %s: %s";

    /**
     * Message displayed when a task is untagged.
     */
    public static final String UNTAG = "Duck duck goose, all tasks are untagged!";

    /**
     * Message displayed when attempting to mark an already marked task.
     */
    public static final String ALREADY_MARKED = "This task is already duck-umented as done: %s";

    /**
     * Message displayed when attempting to unmark an already unmarked task.
     */
    public static final String ALREADY_UNMARKED =
            "This task is already duck-umented as not done: %s";

    /**
     * Message displayed when attempting to add a numeric tag.
     */
    public static final String INVALID_TAG_FORMAT = "The tag cannot be numeric: %s";

    /**
     * Message displayed when there are duplicate tags in the command.
     */
    public static final String DUPLICATE_TAG = "This tag is duck-plicated: %s";

    /**
     * Message displayed when providing a link to the user guide.
     */
    public static final String HELP_MESSAGE = "See the guide at ";

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Messages() {
        throw new UnsupportedOperationException("Utility class should not be instantiated.");
    }
}
