package com.github.mono83.charisma.mutate;

import com.github.mono83.charisma.Values;

class StdValuesWithMutators<T extends Enum<T>> implements ValuesWithMutators<T> {
    private final Values<T> values;
    private final Mutators<T> mutators;

    StdValuesWithMutators(final Values<T> values, final Mutators<T> mutators) {
        this.values = Values.from(values);
        this.mutators = mutators;
    }

    @Override
    public Values<T> getValues() {
        return values;
    }

    @Override
    public Mutators<T> getMutators() {
        return mutators;
    }
}
