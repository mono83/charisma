package com.github.mono83.charisma;

import java.util.Objects;
import java.util.Optional;

/**
 * Contains value change data.
 */
public class ValueChange<T> {
    private final T key;
    private final Long previous;
    private final long current;

    /**
     * Constructor.
     *
     * @param key      Value key.
     * @param previous Previous value, optional.
     * @param current  New value.
     */
    public ValueChange(final T key, final Long previous, final long current) {
        this.key = Objects.requireNonNull(key, "key");
        this.previous = previous;
        this.current = current;
    }

    public T getKey() {
        return this.key;
    }

    public Optional<Long> getPrevious() {
        return Optional.ofNullable(previous);
    }

    public long getCurrent() {
        return this.current;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValueChange<?> that = (ValueChange<?>) o;
        return current == that.current &&
                key.equals(that.key) &&
                Objects.equals(previous, that.previous);
    }

    @Override
    public String toString() {
        return String.format(
                "{%s %s -> %d}",
                this.key,
                this.previous == null ? "<null>" : String.valueOf(this.previous),
                this.current
        );
    }
}
