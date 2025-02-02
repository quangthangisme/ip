package mightyduck.command;

import mightyduck.command.command.Command;

/**
 * Enum representing the possible types of results returned by a {@link Command}.
 * These result types indicate the outcome of a command execution.
 */
public enum CommandResultType {

    /**
     * Indicates a successful execution.
     */
    SUCCESS,

    /**
     * Indicates an error result due to incorrect user input or other invalid command issues.
     */
    ERROR,

    /**
     * Indicates the termination of the application or program.
     */
    TERMINATION,

    /**
     * Indicates a runtime error that occurs during the command execution.
     */
    TERMINATING_ERROR
}
