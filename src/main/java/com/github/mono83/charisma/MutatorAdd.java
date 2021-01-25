package com.github.mono83.charisma;

public class MutatorAdd<T extends Enum<T>> implements Mutator<MutatorAdd<T>, T> {
    private final long valueToAdd;

    public MutatorAdd(final long valueToAdd) {
        this.valueToAdd = valueToAdd;
    }

    @Override
    public int getOrder() {
        return 200;
    }

    @Override
    public MutatorAdd<T> reduce(final MutatorAdd<T> other) {
        return other == null || other.valueToAdd == 0
                ? this
                : new MutatorAdd<>(valueToAdd + other.valueToAdd);
    }

    @Override
    public Long apply(final Long previous) {
        return previous == null
                ? valueToAdd
                : previous + valueToAdd;
    }
}
