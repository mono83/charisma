package com.github.mono83.charisma;

import java.util.Comparator;
import java.util.Objects;

public interface Modifier<Z extends Mutator<Z, T>, T extends Enum<T>> extends Comparable<Modifier<?, T>> {
    static <Z extends Mutator<Z, T>, T extends Enum<T>> Modifier<Z, T> of(
            final T key,
            final Z operator,
            final int tier
    ) {
        return new ModifierImpl<>(key, operator, tier);
    }

    Z getOperator();

    int getTier();

    T getKey();

    Comparator<Modifier<?, ?>> comparator = (o1, o2) -> {
        Objects.requireNonNull(o1, "o1");
        Objects.requireNonNull(o2, "o2");
        if (o1.getTier() < o2.getTier()) {
            return -1;
        } else if (o1.getTier() > o2.getTier()) {
            return 1;
        }

        if (o1.getOperator().getOrder() < o2.getOperator().getOrder()) {
            return -1;
        } else if (o1.getOperator().getOrder() > o2.getOperator().getOrder()) {
            return 1;
        }

        return 0;
    };
}
