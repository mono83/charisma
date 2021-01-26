package com.github.mono83.charisma.mutate;

import java.util.Objects;

/**
 * Standard mutator implementation.
 *
 * @param <T> Key type.
 */
class StdMutator<T extends Enum<T>> implements Mutator<T> {
    final T key;
    final short tier;
    final Operation operation;
    final long value;

    StdMutator(
            final T key,
            final Operation operation,
            final short tier,
            final long value
    ) {
        this.key = Objects.requireNonNull(key, "key");
        this.operation = Objects.requireNonNull(operation, "operation");
        this.tier = tier;
        this.value = value;
    }

    protected StdMutator<T> with(final long value) {
        return value == this.value ? this : new StdMutator<T>(key, operation, tier, value);
    }

    @Override
    public T getKey() {
        return key;
    }

    @Override
    public Mutator<T> merge(final Mutator<T> other) {
        if (other == null) {
            return this;
        } else if (!(other instanceof StdMutator<?>)) {
            throw new IllegalArgumentException("Not a StdMutator given. Seems like comparator failure");
        }
        if (compareTo(other) != 0) {
            throw new IllegalArgumentException("Mutators does not match");
        }

        return this.with(operation.merge(this.value, ((StdMutator<?>) other).value));
    }

    @Override
    public Long apply(final Long previous) {
        return operation.apply(previous, this.value);
    }

    @Override
    public int compareTo(final Mutator<T> other) {
        if (other instanceof StdMutator<?>) {
            StdMutator<T> std = (StdMutator<T>) other;
            int r = Short.compare(this.tier, std.tier);
            if (r == 0) {
                r = this.operation.compareTo(std.operation);
                if (r == 0) {
                    r = this.key.compareTo(std.key);
                }
            }
            return r;
        } else {
            // Standard mutators comes first
            return -1;
        }
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof StdMutator<?>)) {
            return false;
        }

        StdMutator<?> m = (StdMutator<?>) other;
        return this.tier == m.tier
                && this.value == m.value
                && Objects.equals(this.key, m.key)
                && Objects.equals(this.operation, m.operation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, operation, tier, value);
    }
}
