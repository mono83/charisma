package com.github.mono83.charisma;

import java.util.EnumMap;
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
    public Optional<Long> get(final T key) {
        return Optional.ofNullable(values.get(key));
    }
}
