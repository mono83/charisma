package com.github.mono83.charisma;

import java.util.Map;
import java.util.Optional;

/**
 * Standard implementation of value container.
 *
 * @param <T> Key type.
 */
public abstract class StdMutableValuesContainer<T extends Enum<T>> {
    protected final MutableValues<T> values;

    /**
     * Constructs value container from given values source.
     *
     * @param source Source data.
     */
    protected StdMutableValuesContainer(final MutableValues<T> source) {
        this.values = source == null
                ? MutableValues.from(null)
                : source;
    }

    /**
     * Constructs value container from given values source.
     *
     * @param source Source data.
     */
    protected StdMutableValuesContainer(final Map<T, Long> source) {
        this.values = MutableValues.from(source);
    }

    /**
     * Returns value, associated with key.
     *
     * @param key Key to match.
     * @return Associated value.
     */
    protected Optional<Long> get(final T key) {
        return values.get(key);
    }

    /**
     * Returns value, associated with key.
     *
     * @param key Key to match.
     * @return Associated value.
     * @throws KeyMissingException If no key found.
     */
    protected long mustGet(final T key) throws KeyMissingException {
        return values.mustGet(key);
    }

    /**
     * Returns value associated with key or default value, if there is no value.
     *
     * @param key          Key to match.
     * @param defaultValue Default value.
     * @return Resulting value.
     */
    protected long get(final T key, final long defaultValue) {
        return values.get(key, defaultValue);
    }

    /**
     * Inserts or replaces value.
     *
     * @param key   Key to match.
     * @param value Value to set.
     * @return Previous value or null if none.
     */
    protected Long set(final T key, long value) {
        return values.set(key, value);
    }

    /**
     * Inserts or replaces multiple values atomically.
     *
     * @param pairs Key value pairs.
     * @return Map of previous values for modified keys.
     */
    protected Map<T, Long> set(final Map<T, Long> pairs) {
        return values.set(pairs);
    }
}