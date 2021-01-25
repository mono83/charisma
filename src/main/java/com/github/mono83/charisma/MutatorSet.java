package com.github.mono83.charisma;

public class MutatorSet<T extends Enum<T>> implements Mutator<MutatorSet<T>, T> {
    private final long valueToSet;

    public MutatorSet(final long valueToSet) {
        this.valueToSet = valueToSet;
    }

    @Override
    public int getOrder() {
        return 100;
    }

    @Override
    public MutatorSet<T> reduce(final MutatorSet<T> other) {
        return other == null || other.valueToSet < this.valueToSet
                ? this
                : other;
    }

    @Override
    public Long apply(final Long previous) {
        return valueToSet;
    }
}
