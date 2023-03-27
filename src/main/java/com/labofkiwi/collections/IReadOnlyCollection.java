package com.labofkiwi.collections;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

import com.labofkiwi.TryResult;

public interface IReadOnlyCollection<T> extends IIterable<T> {
    default boolean contains(T item) {
        for (T element : this) {
            if (Objects.equals(item, element)) {
                return true;
            }
        }

        return false;
    }

    default void copyTo(IFixedSizeList<? super T> destination) {
        Objects.requireNonNull(destination);

        int count = count();

        if (destination.count() < count) {
            throw new IllegalArgumentException("Destination is not large enough.");
        }

        Iterator<T> it = iterator();

        for (int i = 0; i < count; i++) {
            destination.set(i, it.next());
        }
    }

    int count();

    default boolean exists(Predicate<? super T> match) {
        Objects.requireNonNull(match);

        for (T element : this) {
            if (match.test(element)) {
                return true;
            }
        }

        return false;
    }

    default boolean isReadOnly() {
        return true;
    }

    default boolean trueForAll(Predicate<? super T> match) {
        Objects.requireNonNull(match);

        for (T element : this) {
            if (!match.test(element)) {
                return false;
            }
        }

        return true;
    }

    default TryResult<T> tryFind(Predicate<? super T> match) {
        Objects.requireNonNull(match);

        for (T element : this) {
            if (match.test(element)) {
                return TryResult.success(element);
            }
        }

        return TryResult.failure();
    }

    default TryResult<T> tryFindLast(Predicate<? super T> match) {
        Objects.requireNonNull(match);

        Iterator<T> it = iterator();

        while (it.hasNext()) {
            T matchedElement = it.next();

            if (match.test(matchedElement)) {
                while (it.hasNext()) {
                    T element = it.next();

                    if (match.test(element)) {
                        matchedElement = element;
                    }
                }

                return TryResult.success(matchedElement);
            }
        }

        return TryResult.failure();
    }
}
