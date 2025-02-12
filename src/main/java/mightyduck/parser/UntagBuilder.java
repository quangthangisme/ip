package mightyduck.parser;

import java.util.Arrays;
import java.util.List;

import mightyduck.command.UntagCommand;
import mightyduck.exception.InvalidCommandException;
import mightyduck.exception.InvalidValueException;
import mightyduck.task.TaskManager;
import mightyduck.utils.Messages;

/**
 * A builder class for constructing a {@link UntagCommand} from user input.
 */
public class UntagBuilder extends Builder {

    /**
     * The command word used to invoke the "untag" command.
     */
    public static final String COMMAND_WORD = "untag";

    /**
     * The format of the "untag" command.
     */
    public static final String COMMAND_FORMAT = "untag <index1> <index2> ... <tag1> <tag2> ...";

    /**
     * Constructs a new {@link UntagBuilder} with the specified {@link TaskManager}.
     *
     * @param taskManager The {@link TaskManager} instance that manages tasks.
     */
    public UntagBuilder(TaskManager taskManager) {
        super(taskManager);
    }

    /**
     * Creates a {@code UntagCommand} from user-provided input.
     *
     * @param input The user-provided input string, expected to contain task indices followed by
     *              tags.
     * @return A new {@code UntagCommand} instance.
     * @throws InvalidCommandException If the input is incorrectly formatted.
     * @throws InvalidValueException   If any index is invalid or a tag is numeric.
     */
    public UntagCommand fromInput(String input) throws InvalidCommandException,
            InvalidValueException {
        String[] parts = input.trim().split("\\s+");

        List<String> indexParts = extractIndices(parts);
        List<String> tagParts = extractTags(parts, indexParts.size());

        if (indexParts.isEmpty()) {
            throw new InvalidCommandException(String.format(Messages.WRONG_COMMAND_FORMAT,
                    COMMAND_FORMAT));
        }
        List<Integer> indices = Validation.validateAndParseIndices(indexParts);

        if (tagParts.isEmpty()) {
            throw new InvalidCommandException(String.format(Messages.WRONG_COMMAND_FORMAT,
                    COMMAND_FORMAT));
        }
        Validation.validateTags(tagParts);

        return new UntagCommand(taskManager, indices, tagParts);
    }

    /**
     * Extracts the indices (numeric values) from the beginning of the input string.
     *
     * @param parts The parts of the input string, split by whitespace.
     * @return A list of strings containing the indices.
     */
    private List<String> extractIndices(String[] parts) {
        return Arrays.stream(parts).takeWhile(part -> part.matches("\\d+")).toList();
    }

    /**
     * Extracts the tags (non-numeric values) from the input string after the indices.
     *
     * @param parts      The parts of the input string, split by whitespace.
     * @param indexCount The number of index parts that have been extracted.
     * @return A list of strings containing the tags.
     */
    private List<String> extractTags(String[] parts, int indexCount) {
        return Arrays.asList(Arrays.copyOfRange(parts, indexCount, parts.length));
    }
}
