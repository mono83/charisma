package com.github.mono83.charisma;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Immutable collection of values.
 */
public interface Values<T extends Enum<T>> extends Iterable<Map.Entry<T, Long>> {
    /**
     * Constructs new immutable values instance from given map.
     *
     * @param values Source values map.
     * @return Values.
     */
    static <T extends Enum<T>> Values<T> from(final Map<T, Long> values) {
        if (values == null || values.size() == 0) {
            return new EmptyImmutable<>();
        }

        return new EnumMapImmutable<>(values);
    }

    /**
     * Constructs new immutable values instance from given values.
     *
     * @param values Source values.
     * @return Values.
     */
    static <T extends Enum<T>> Values<T> from(final Values<T> values) {
        if (values == null) {
            return new EmptyImmutable<>();
        }
        if (values instanceof MutableValues<?>) {
            return from(values.toMap());
        }

        return values;
    }

    /**
     * Returns true if there is value for given key.
     *
     * @param key Key to match.
     * @return True if there is value for given key.
     */
    boolean contains(T key);

    /**
     * Returns value, associated with key.
     *
     * @param key Key to match.
     * @return Associated value.
     */
    Optional<Long> get(T key);

    /**
     * @return Values as map
     */
    default Map<T, Long> toMap() {
        Map<T, Long> map = new HashMap<>();
        for (Map.Entry<T, Long> e : this) {
            map.put(e.getKey(), e.getValue());
        }
        return map;
    }

    /**
     * Returns value, associated with key.
     *
     * @param key Key to match.
     * @return Associated value.
     * @throws KeyMissingException If no key found.
     */
    default long mustGet(final T key) throws KeyMissingException {
        return this.get(key).orElseThrow(() -> new KeyMissingException(key));
    }

    /**
     * Returns value associated with key or default value, if there is no value.
     *
     * @param key          Key to match.
     * @param defaultValue Default value.
     * @return Resulting value.
     */
    default long get(final T key, final long defaultValue) {
        return this.get(key).orElse(defaultValue);
    }
}
