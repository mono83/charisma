package com.github.mono83.charisma;

import java.util.Map;
import java.util.Optional;

/**
 * Standard implementation of value container.
 *
 * @param <T> Key type.
 */
public abstract class StdValuesContainer<T extends Enum<T>> {
    protected final Values<T> values;

    /**
     * Constructs value container from given values source.
     *
     * @param source Source data.
     */
    protected StdValuesContainer(final Values<T> source) {
        this.values = Values.from(source);
    }

    /**
     * Constructs value container from given values source.
     *
     * @param source Source data.
     */
    protected StdValuesContainer(final Map<T, Long> source) {
        this.values = Values.from(source);
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
}
