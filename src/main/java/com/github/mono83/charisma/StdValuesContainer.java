package com.github.mono83.charisma;

import java.util.Map;

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
}
