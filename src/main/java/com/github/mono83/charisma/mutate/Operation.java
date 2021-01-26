package com.github.mono83.charisma.mutate;

import java.util.Objects;

enum Operation {
    SET,
    ADD,
    X1000;

    /**
     * Parses given string representation of operation.
     *
     * @param s String to parse.
     * @return Matched value.
     */
    public static Operation parse(final String s) {
        switch (Objects.requireNonNull(s, "source").trim().toLowerCase()) {
            case "set":
                return SET;
            case "plus":
            case "+":
            case "add":
                return ADD;
            case "x1000":
            case "*1000":
                return X1000;
        }

        throw new IllegalArgumentException("Unknown operation " + s);
    }

    public long merge(final long a, final long b) {
        switch (this) {
            case SET:
                return Math.max(a, b);
            case ADD:
                return a + b;
            case X1000:
                return a + b - 1000;
        }

        throw new UnsupportedOperationException("Unsupported operation " + this);
    }

    public Long apply(final Long value, final long mutation) {
        switch (this) {
            case SET:
                return mutation;
            case ADD:
                return value == null ? mutation : Long.sum(value, mutation);
            case X1000:
                return value == null ? null : (long) (value / 1000. * mutation);
        }

        throw new UnsupportedOperationException("Unsupported operation " + this);
    }
}
