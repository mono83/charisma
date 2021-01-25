package com.github.mono83.charisma;

import java.util.Map;

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
}