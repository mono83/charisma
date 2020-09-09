package com.github.mono83.charisma;

import java.util.Optional;

class EmptyImmutable<T extends Enum<T>> implements Values<T> {
    EmptyImmutable() {
    }

    @Override
    public boolean contains(final T key) {
        return false;
    }

    @Override
    public Optional<Long> get(final T key) {
        return Optional.empty();
    }

    @Override
    public long get(final T key, final long defaultValue) {
        return defaultValue;
    }
}
