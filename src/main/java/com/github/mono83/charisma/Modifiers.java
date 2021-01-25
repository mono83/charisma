package com.github.mono83.charisma;

public interface Modifiers<T extends Enum<T>> extends Iterable<Modifier<?, T>> {
    Values<T> apply(Values<T> target);

    void apply(MutableValues<T> target);
}
