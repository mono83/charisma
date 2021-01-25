package com.github.mono83.charisma;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

class EmptyImmutable<T extends Enum<T>> implements Values<T> {
    EmptyImmutable() {
    }

    @Override
    public boolean contains(final T key) {
        return false;
    }

    @Override
    public Iterator<Map.Entry<T, Long>> iterator() {
        return Collections.emptyIterator();
    }

    @Override
    public Map<T, Long> toMap() {
        return Collections.emptyMap();
    }

    @Override
    public long mustGet(final T key) throws KeyMissingException {
        throw new KeyMissingException(key);
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
