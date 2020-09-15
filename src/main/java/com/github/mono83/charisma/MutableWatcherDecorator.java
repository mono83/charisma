package com.github.mono83.charisma;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Decorator over mutable values collection, that invokes listener on value change.
 */
class MutableWatcherDecorator<T extends Enum<T>> implements MutableValues<T> {
    private final MutableValues<T> origin;
    private final Consumer<ValueChange<T>> listener;

    MutableWatcherDecorator(final MutableValues<T> origin, final Consumer<ValueChange<T>> listener) {
        this.origin = Objects.requireNonNull(origin, "origin");
        this.listener = Objects.requireNonNull(listener, "listener");
    }


    @Override
    public Long set(final T key, final long value) {
        Long previous = origin.set(key, value);
        if (previous == null || previous != value) {
            listener.accept(new ValueChange<>(key, previous, value));
        }
        return null;
    }

    @Override
    public boolean contains(final T key) {
        return origin.contains(key);
    }

    @Override
    public Optional<Long> get(final T key) {
        return origin.get(key);
    }

    @Override
    public long get(final T key, final long defaultValue) {
        return origin.get(key, defaultValue);
    }
}
