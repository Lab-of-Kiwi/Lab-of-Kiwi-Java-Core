package com.labofkiwi;

/**
 * Simulates the retrieval and setting of a value "by reference".
 *
 * @param <T> The value's type.
 */
public interface Ref<T> extends ReadOnlyRef<T> {
    /**
     * Sets the value of this instance.
     *
     * @param value The new value for this instance.
     */
    void setValue(T value);
}
