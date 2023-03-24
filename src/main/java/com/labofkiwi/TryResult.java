package com.labofkiwi;

/**
 * Represents a result for the Try-Get pattern.
 *
 * @param <T> The value's type.
 */
public final class TryResult<T> implements IReadOnlyRef<T> {
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static final TryResult FAILURE = new TryResult(false, null);

    private final boolean isSuccess;
    private final T       value;

    // Internal constructor.
    private TryResult(boolean isSuccess, T value) {
        this.isSuccess = isSuccess;
        this.value     = value;
    }

    /**
     * Gets the resulting value of the process that produced this instance.
     *
     * @return The value if successful; otherwise, {@code null}.
     */
    @Override
    public T getValue() {
        return value;
    }

    /**
     * Determines if the process resulting in this instance was successful.
     *
     * @return {@code true} is successful; otherwise, {@code false}.
     */
    public boolean isSuccess() {
        return isSuccess;
    }

    /**
     * Returns the failure instance, where the value is {@code null} and is not successful.
     *
     * @param <T> The value's type.
     * @return The failure instance.
     */
    public static <T> TryResult<T> failure() {
        @SuppressWarnings("unchecked")
        TryResult<T> result = (TryResult<T>)FAILURE;
        return result;
    }

    /**
     * Returns a successful instance.
     *
     * @param <T> The value's type.
     * @param value The resulting value.
     * @return A successful instance.
     */
    public static <T> TryResult<T> success(T value) {
        return new TryResult<>(true, value);
    }
}
