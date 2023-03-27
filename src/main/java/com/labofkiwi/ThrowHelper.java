package com.labofkiwi;

public final class ThrowHelper {
    private static final String ARGUMENT_RANGE      = "Invalid value for '%s': %d. Valid values are between %d and %d, inclusive.";
    private static final String LIST_RANGE          = "Invalid range for list '%s': [%d, $d]. Valid range: [0, %d].";
    private static final String LIST_RANGE_INSTANCE = "Invalid range for list: [%d, $d]. Valid range: [0, %d].";

    private ThrowHelper() { }

    public static void validateArgumentRange(String argumentName, int value, int maxValue) {
        if (UInt.gt(value, maxValue)) {
            throw new IllegalArgumentException(String.format(ARGUMENT_RANGE, argumentName, value, 0, maxValue));
        }
    }

    public static void validateArgumentRange(String argumentName, int value, int minValue, int maxValue) {
        if (value < minValue || value > maxValue) {
            throw new IllegalArgumentException(String.format(ARGUMENT_RANGE, argumentName, value, minValue, maxValue));
        }
    }

    public static void validateIndex(int index, int maxIndex) {
        validateIndex("index", index, maxIndex);
    }

    public static void validateIndex(String indexName, int index, int maxIndex) {
        if (UInt.gt(index, maxIndex)) {
            throw new IndexOutOfBoundsException(String.format(ARGUMENT_RANGE, indexName, index, 0, maxIndex));
        }
    }

    public static void validateRange(int startIndex, int count, int listSize) {
        validateRange("startIndex", "count", startIndex, count, listSize);
    }

    public static void validateRange(String indexName, String countName, int startIndex, int count, int listSize) {
        validateIndex(indexName, startIndex, listSize);
        validateArgumentRange(countName, count, listSize);

        if (UInt.gt(startIndex + count, listSize)) {
            throw new IllegalArgumentException(String.format(LIST_RANGE_INSTANCE, startIndex, count, listSize));
        }
    }

    public static void validateRange(String indexName, String countName, String listName, int startIndex, int count, int listSize) {
        validateIndex(indexName, startIndex, listSize);
        validateArgumentRange(countName, count, listSize);

        if (UInt.gt(startIndex + count, listSize)) {
            throw new IllegalArgumentException(String.format(LIST_RANGE, listName, startIndex, count, listSize));
        }
    }
}
