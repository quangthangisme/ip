package mightyduck.utils;

/**
 * A container class for a pair of values, used to hold two related objects.
 *
 * @param <T>     The type of the first element (index).
 * @param <U>     The type of the second element (element).
 * @param index   The first object.
 * @param element The second object.
 */
public record Pair<T, U>(T index, U element) {
}
