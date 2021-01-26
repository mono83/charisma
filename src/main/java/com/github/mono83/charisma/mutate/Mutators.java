package com.github.mono83.charisma.mutate;

import com.github.mono83.charisma.MutableValues;

import java.util.HashMap;
import java.util.Objects;

/**
 * Represents values mutator collection.
 *
 * @param <T> Key type.
 */
public interface Mutators<T extends Enum<T>> extends Iterable<Mutator<T>> {
    /**
     * Static constructor, that builds mutators collection from given
     * iterable source.
     *
     * @param source Iterable source with mutators.
     * @param <T>    Key type.
     * @return Mutators.
     */
    static <T extends Enum<T>> Mutators<T> of(final Iterable<Mutator<T>> source) {
        if (source instanceof MutatorsSet) {
            return (Mutators<T>) source;
        }
        return new MutatorsSet<>(source);
    }

    /**
     * Applies all mutators on given mutable values collection.
     *
     * @param values Mutable values
     */
    default void apply(final MutableValues<T> values) {
        Objects.requireNonNull(values, "values");
        HashMap<T, Long> changes = new HashMap<>();
        for (Mutator<T> mutator : this) {
            Long previous = values.get(mutator.getKey()).orElse(null);
            Long mutated = mutator.apply(previous);
            if (previous != null) {
                if (!previous.equals(mutated)) {
                    changes.put(mutator.getKey(), mutated);
                }
            } else if (mutated != null) {
                changes.put(mutator.getKey(), mutated);
            }
        }
        values.set(changes);
    }
}
