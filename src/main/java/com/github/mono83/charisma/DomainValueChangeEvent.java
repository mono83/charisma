package com.github.mono83.charisma;

import java.util.Objects;

public class DomainValueChangeEvent<T> {
    private final Class<?> domainClass;
    private final ValueChange<T> change;

    /**
     * Constructor.
     *
     * @param domainClass Object class, that has changed values.
     * @param change      Changed data.
     */
    public DomainValueChangeEvent(final Class<?> domainClass, final ValueChange<T> change) {
        this.domainClass = Objects.requireNonNull(domainClass, "domainClass");
        this.change = Objects.requireNonNull(change, "change");
    }

    public Class<?> getDomainClass() {
        return this.domainClass;
    }

    public ValueChange<T> getChange() {
        return change;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainValueChangeEvent<?> that = (DomainValueChangeEvent<?>) o;
        return domainClass.equals(that.domainClass) &&
                change.equals(that.change);
    }

    @Override
    public int hashCode() {
        return Objects.hash(domainClass, change);
    }

    @Override
    public String toString() {
        return String.format("{%s:%s}", domainClass.getName(), change.toString());
    }
}


