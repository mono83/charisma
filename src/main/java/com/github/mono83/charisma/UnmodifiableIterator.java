package com.github.mono83.charisma;

import java.util.Iterator;
import java.util.Map;

class UnmodifiableIterator<T extends Enum<T>> implements Iterator<Map.Entry<T, Long>> {
    private final Iterator<Map.Entry<T, Long>> iterator;

    UnmodifiableIterator(final Iterator<Map.Entry<T, Long>> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Map.Entry<T, Long> next() {
        return iterator.next();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove");
    }
}
