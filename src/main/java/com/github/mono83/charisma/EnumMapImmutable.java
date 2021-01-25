package com.github.mono83.charisma;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

class EnumMapImmutable<T extends Enum<T>> implements Values<T> {
    protected final EnumMap<T, Long> values;

    EnumMapImmutable(final Map<T, Long> values) {
        this.values = new EnumMap<>(values);
    }

    @Override
    public boolean contains(final T key) {
        return values.containsKey(key);
    }

    @Override
    public Iterator<Map.Entry<T, Long>> iterator() {
        return new UnmodifiableIterator<>(values.entrySet().iterator());
    }

    @Override
    public Optional<Long> get(final T key) {
        return Optional.ofNullable(values.get(key));
    }

    @Override
    public long mustGet(final T key) throws KeyMissingException {
        Long v = values.get(key);
        if (v == null) {
            throw new KeyMissingException(key);
        }
        return v;
    }
}
