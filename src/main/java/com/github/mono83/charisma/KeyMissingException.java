package com.github.mono83.charisma;

public class KeyMissingException extends RuntimeException {
    public KeyMissingException(final Object key) {
        super(String.format("Key %s not found but requested", key));
    }
}
