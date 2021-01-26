package com.github.mono83.charisma.mutate;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

class MutatorsSet<T extends Enum<T>> implements Mutators<T> {
    private final Set<Mutator<T>> mutators;

    MutatorsSet(final Iterable<Mutator<T>> source) {
        TreeSet<Mutator<T>> mutators = new TreeSet<>();
        for (Mutator<T> m : source) {
            if (!mutators.add(m)) {
                Mutator<T> prev = mutators.ceiling(m);
                Mutator<T> merged = prev.merge(m);
                mutators.remove(prev);
                mutators.add(merged);
            }
        }
        this.mutators = Collections.unmodifiableSet(mutators);
    }

    @Override
    public Iterator<Mutator<T>> iterator() {
        return mutators.iterator();
    }
}
