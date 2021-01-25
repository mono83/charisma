package com.github.mono83.charisma;

import java.util.Objects;

class ModifierImpl<Z extends Mutator<Z, T>, T extends Enum<T>> implements Modifier<Z, T> {
    private final T key;
    private final Z operator;
    private final int tier;

    ModifierImpl(final T key, final Z operator, final int tier) {
        this.key = Objects.requireNonNull(key, "key");
        this.operator = Objects.requireNonNull(operator, "operator");
        this.tier = tier;
    }

    @Override
    public Z getOperator() {
        return operator;
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public T getKey() {
        return key;
    }

    @Override
    public int compareTo(final Modifier<?, T> o) {
        return comparator.compare(this, o);
    }
}
