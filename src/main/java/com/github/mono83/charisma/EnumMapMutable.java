package com.github.mono83.charisma;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

class EnumMapMutable<T extends Enum<T>> extends EnumMapImmutable<T> implements MutableValues<T> {
    EnumMapMutable(final Map<T, Long> values) {
        super(values);
    }

    @Override
    public Long set(final T key, final long value) {
        return super.values.put(key, value);
    }

    @Override
    public Map<T, Long> set(final Collection<Map.Entry<T, Long>> pairs) {
        HashMap<T, Long> previous = new HashMap<>(pairs.size());
        synchronized (this) {
            for (Map.Entry<T, Long> pair : pairs) {
                Long prev = super.values.put(pair.getKey(), pair.getValue());
                if (prev != null) {
                    previous.put(pair.getKey(), prev);
                }
            }
        }
        return previous;
    }
}
