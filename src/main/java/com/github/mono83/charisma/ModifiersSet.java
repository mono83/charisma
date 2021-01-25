package com.github.mono83.charisma;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

class ModifiersSet<T extends Enum<T>> implements Modifiers<T> {
    private final Set<Modifier<?, T>> modifiers;

    ModifiersSet(final Collection<Modifier<?, T>> source) {
        this.modifiers = Collections.unmodifiableSet(new TreeSet<>(source));
    }

    @Override
    public Iterator<Modifier<?, T>> iterator() {
        return modifiers.iterator();
    }

    @Override
    public Values<T> apply(final Values<T> target) {
        HashMap<T, Long> proto = target == null
                ? new HashMap<>()
                : new HashMap<>(target.toMap());

        for (Modifier<?, T> modifier : this) {
            proto.put(
                    modifier.getKey(),
                    modifier.getOperator().apply(proto.get(modifier.getKey()))
            );
        }

        return Values.from(proto);
    }

    @Override
    public void apply(final MutableValues<T> target) {
        for (Modifier<?, T> modifier : this) {
            target.set(
                    modifier.getKey(),
                    modifier.getOperator().apply(target.get(modifier.getKey()).orElse(null))
            );
        }
    }
}
