package com.github.mono83.charisma;

import java.util.Map;

class EnumMapMutable<T extends Enum<T>> extends EnumMapImmutable<T> implements MutableValues<T> {
    EnumMapMutable(final Map<T, Long> values) {
        super(values);
    }

    @Override
    public Long set(final T key, final long value) {
        return super.values.put(key, value);
    }
}
