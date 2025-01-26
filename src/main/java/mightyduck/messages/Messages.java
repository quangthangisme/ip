package mightyduck.messages;

public class Messages {
    public static final String EMPTY_LIST =
            "Woo-hoo! There are no tasks currently. Enjoy your day, citizen!";

    public static final String LIST =
            "Here are the tasks in your list:";

    public static final String MARK =
            "Quack-cellent job, citizen! The task is completed.";

    public static final String UNMARK =
            "No worries! Waddle at your own pace.";

    public static final String DELETE =
            "One less thing to pond-er about!";

    public static final String EMPTY_TODO =
            "A todo task must have a description!";

    public static final String ADD_TASK =
            "Let's get pro-duck-tive!";

    public static final String INVALID_COMMAND =
            "This is a wild goose chase!";

    public static final String WRONG_COMMAND_FORMAT =
            "Please use the correct format: %s";

    public static final String WRONG_NUMBER_FORMAT =
            "I cannot parse the index!";

    public static final String WELCOME =
            """
                    ============================================================
                    Greetings, citizens of Duckville! This is Mighty Duck!
                    How can I serve you today?
                    """;

    public static final String BYE =
            """
                    Farewell, citizens of Duckville!
                    This is Mighty Duck, flying off to new adventures!
                    ============================================================
                    """;

    public static final String FAILED_INIT =
            """
                    ============================================================
                    Oh duck, the file loading failed.
                    ============================================================
                    """;

    public static final String FAILED_SAVE =
            """
                    Oh duck, the file saving failed.
                    ============================================================
                    """;

    public static final String EXCEPTION =
            "Something's a-fowl! ";

    public static final String OUT_OF_RANGE_INDEX =
            "The task number is out of range!";
}
