package com.github.mono83.charisma;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Mutable values collection.
 */
public interface MutableValues<T extends Enum<T>> extends Values<T> {
    /**
     * Constructs new mutable values instance from given map.
     *
     * @param values Source values map.
     * @return Values.
     */
    static <T extends Enum<T>> MutableValues<T> from(final Map<T, Long> values) {
        return new EnumMapMutable<>(Objects.requireNonNull(values, "values"));
    }

    /**
     * Constructs new mutable values instance with changes watcher from given map.
     *
     * @param values   Source values map.
     * @param listener Changes listener.
     * @return Values.
     */
    static <T extends Enum<T>> MutableValues<T> from(final Map<T, Long> values, final Consumer<ValueChange<T>> listener) {
        return new MutableWatcherDecorator<>(
                new EnumMapMutable<>(Objects.requireNonNull(values, "values")),
                listener
        );
    }

    /**
     * Inserts or replaces value.
     *
     * @param key   Key to match.
     * @param value Value to set.
     * @return Previous value or null if none.
     */
    Long set(T key, long value);

    /**
     * Inserts or replaces multiple values atomically.
     *
     * @param pairs Key value pairs.
     * @return Map of previous values for modified keys.
     */
    Map<T, Long> set(Map<T, Long> pairs);
}
