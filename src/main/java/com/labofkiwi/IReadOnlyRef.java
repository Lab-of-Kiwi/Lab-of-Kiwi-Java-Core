package com.labofkiwi;

/**
 * Simulates the retrieval of a value "by reference".
 *
 * @param <T> The value's type.
 */
public interface IReadOnlyRef<T> {
    /**
     * Gets the value of this instance.
     *
     * @return The value of this instance.
     */
    T getValue();
}
