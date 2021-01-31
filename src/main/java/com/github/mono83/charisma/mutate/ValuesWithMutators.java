package com.github.mono83.charisma.mutate;

import com.github.mono83.charisma.Values;

public interface ValuesWithMutators<T extends Enum<T>> {
    Values<T> getValues();

    Mutators<T> getMutators();
}
