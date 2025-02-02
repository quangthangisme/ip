package mightyduck.utils;

/**
 * A container class for a pair of values, used to hold two related objects.
 *
 * @param <T>     The type of the first element (key).
 * @param <U>     The type of the second element (value).
 * @param key   The first object.
 * @param value The second object.
 */
public record Pair<T, U>(T key, U value) {
}
