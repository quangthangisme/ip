package mightyduck.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mightyduck.exception.InvalidValueException;
import mightyduck.utils.Messages;

/**
 * A utility class that provides common validation methods for various arguments, such as tags and
 * indices.
 */
public class Validation {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Validation() {
        throw new UnsupportedOperationException("Utility class should not be instantiated.");
    }

    /**
     * Validates the tags to ensure that none of the tags are numeric or duplicate.
     *
     * @param tags The list of tags to validate.
     * @throws InvalidValueException If any tag is numeric or is duplicate.
     */
    public static void validateTags(List<String> tags) throws InvalidValueException {
        Set<String> seenTags = new HashSet<>();
        for (String tag : tags) {
            if (tag.matches("\\d+")) {
                throw new InvalidValueException(String.format(Messages.INVALID_TAG_FORMAT, tag));
            }
            if (!seenTags.add(tag)) {
                throw new InvalidValueException(String.format(Messages.DUPLICATE_TAG, tag));
            }
        }
    }

    /**
     * Validates and converts the index strings to integers and subtracts 1 to make them
     * zero-based.
     *
     * @param indexParts The list of index strings to convert.
     * @return A list of integers representing the zero-based indices.
     * @throws InvalidValueException If any index is not a valid integer.
     */
    public static List<Integer> validateAndParseIndices(List<String> indexParts)
            throws InvalidValueException {
        List<Integer> indices = new ArrayList<>();
        for (String part : indexParts) {
            try {
                int index = Integer.parseInt(part) - 1;
                indices.add(index);
            } catch (NumberFormatException e) {
                throw new InvalidValueException(Messages.WRONG_NUMBER_FORMAT);
            }
        }

        return indices;
    }
}
