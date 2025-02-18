package mightyduck.command;

/**
 * Enum representing the possible types of results returned by a {@link Command}.
 * These result types indicate the outcome of a command execution.
 */
public enum CommandResultType {

    /**
     * Indicates that the command was executed successfully.
     */
    SUCCESS,

    /**
     * Indicates that the command failed due to incorrect user input or other issues.
     */
    ERROR,

    /**
     * Indicates that the application or program should terminate.
     */
    TERMINATION,

    /**
     * Indicates a runtime error that occurred during the execution of the command.
     * This is used for errors that prevent the application from continuing as expected.
     */
    TERMINATING_ERROR,

    /**
     * Indicates that the help command was executed.
     */
    HELP
}
