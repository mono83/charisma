package com.github.mono83.charisma;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

public class TemplatedValues<T extends Enum<T>> implements MutableValues<T> {
    private final Values<T> template;
    private final MutableValues<T> values;

    public TemplatedValues(final Values<T> template, final MutableValues<T> values) {
        this.template = Values.from(template);
        this.values = values == null
                ? MutableValues.from(null)
                : values;
    }

    public Values<T> getInnerTemplate() {
        return template;
    }

    public MutableValues<T> getInnerValues() {
        return values;
    }

    @Override
    public Long set(final T key, final long value) {
        return values.set(key, value);
    }

    @Override
    public Map<T, Long> set(final Map<T, Long> pairs) {
        return values.set(pairs);
    }

    @Override
    public boolean contains(final T key) {
        return values.contains(key) || template.contains(key);
    }

    @Override
    public Optional<Long> get(final T key) {
        return values.get(key).or(() -> template.get(key));
    }

    @Override
    public Iterator<Map.Entry<T, Long>> iterator() {
        throw new AssertionError("not implemented");
    }
}
