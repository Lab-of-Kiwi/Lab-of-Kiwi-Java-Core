package com.labofkiwi.collections;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Predicate;

import com.labofkiwi.DefaultComparator;
import com.labofkiwi.ThrowHelper;
import com.labofkiwi.TryResult;
import com.labofkiwi.UInt;

public interface IReadOnlyList<T> extends IReadOnlyCollection<T> {
    default int binarySearch(T item) {
        return binarySearch(item, null);
    }

    default int binarySearch(T item, Comparator<? super T> comparator) {
        if (comparator == null) {
            comparator = DefaultComparator.getInstance();
        }

        int lo = 0;
        int hi = count() - 1;

        while (lo <= hi) {
            int i = Utility.getMedian(lo, hi);

            int c;

            try {
                c = comparator.compare(get(i), item);
            } catch (Exception e) {
                throw new IllegalStateException("Comparator failed.");
            }

            if (c == 0) {
                return i;
            }

            if (c < 0) {
                lo = i + 1;
            }
            else {
                hi = i - 1;
            }
        }

        return ~lo;
    }

    @Override
    default boolean contains(T item) {
        return indexOf(item) != -1;
    }

    @Override
    default void copyTo(IFixedSizeList<? super T> destination) {
        Objects.requireNonNull(destination);

        int count = count();

        if (destination.count() < count) {
            throw new IllegalArgumentException("Destination is not large enough.");
        }

        for (int i = 0; i < count; i++) {
            destination.set(i, get(i));
        }
    }

    @Override
    default boolean exists(Predicate<? super T> match) {
        return findIndex(match) != -1;
    }

    default int findIndex(Predicate<? super T> match) {
        Objects.requireNonNull(match);

        int count = count();

        for (int i = 0; i < count; i++) {
            if (match.test(get(i))) {
                return i;
            }
        }

        return -1;
    }

    default int findLastIndex(Predicate<? super T> match) {
        Objects.requireNonNull(match);

        for (int i = count() - 1; i >= 0; i--) {
            if (match.test(get(i))) {
                return i;
            }
        }

        return -1;
    }

    T get(int index);

    default int indexOf(T item) {
        int count = count();

        if (item == null) {
            for (int i = 0; i < count; i++) {
                if (get(i) == null) {
                    return i;
                }
            }
        }
        else {
            for (int i = 0; i < count; i++) {
                if (item.equals(get(i))) {
                    return i;
                }
            }
        }

        return -1;
    }

    default boolean isFixedSize() {
        return true;
    }

    default int lastIndexOf(T item) {
        if (item == null) {
            for (int i = count() - 1; i >= 0; i--) {
                if (get(i) == null) {
                    return i;
                }
            }
        }
        else {
            for (int i = count() - 1; i >= 0; i--) {
                if (item.equals(get(i))) {
                    return i;
                }
            }
        }

        return -1;
    }

    default IReadOnlyList<T> slice(int startIndex) {
        return slice(startIndex, count() - startIndex);
    }

    default IReadOnlyList<T> slice(int startIndex, int count) {
        ThrowHelper.validateRange(startIndex, count, count());
        return new ReadOnlySlice<>(this, startIndex, count);
    }

    @Override
    default boolean trueForAll(Predicate<? super T> match) {
        Objects.requireNonNull(match);

        int count = count();

        for (int i = 0; i < count; i++) {
            if (!match.test(get(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    default TryResult<T> tryFind(Predicate<? super T> match) {
        int count = count();

        for (int i = 0; i < count; i++) {
            T element = get(i);

            if (match.test(get(i))) {
                return TryResult.success(element);
            }
        }

        return TryResult.failure();
    }

    @Override
    default TryResult<T> tryFindLast(Predicate<? super T> match) {
        for (int i = count() - 1; i >= 0; i--) {
            T element = get(i);

            if (match.test(get(i))) {
                return TryResult.success(element);
            }
        }

        return TryResult.failure();
    }

    default TryResult<T> tryGet(int index) {
        if (UInt.gte(index, count())) {
            return TryResult.failure();
        }

        return TryResult.success(get(index));
    }
}
