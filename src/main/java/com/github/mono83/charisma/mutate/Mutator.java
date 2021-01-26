package com.github.mono83.charisma.mutate;

import java.util.function.Function;

/**
 * Represents value mutator.
 *
 * @param <T> Key type.
 */
public interface Mutator<T extends Enum<T>>
        extends Function<Long, Long>, Comparable<Mutator<T>> {

    /**
     * @return Key, this mutator should be applied on.
     */
    T getKey();

    /**
     * Merges two mutators with same type.
     *
     * @param other Other mutator to merge with.
     * @return Merged.
     */
    Mutator<T> merge(Mutator<T> other);
}
