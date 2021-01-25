package com.github.mono83.charisma;

import java.util.function.Function;

public interface Mutator<Z extends Mutator<Z, T>, T extends Enum<T>> extends Function<Long, Long> {
    Z reduce(Z other);

    int getOrder();
}
