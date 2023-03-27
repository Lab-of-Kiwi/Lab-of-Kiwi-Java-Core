package com.labofkiwi.collections;

import java.util.Objects;
import java.util.function.Predicate;

public interface ICollection<T> extends IReadOnlyCollection<T> {
    void add(T item);

    default void addRange(Iterable<? extends T> collection) {
        Objects.requireNonNull(collection);

        if (collection instanceof IReadOnlyList) {
            IReadOnlyList<? extends T> list = (IReadOnlyList<? extends T>)collection;

            for (int i = 0; i < list.count(); i++) {
                add(list.get(i));
            }
        }
        else {
            for (T element : collection) {
                add(element);
            }
        }
    }

    default <U extends T> void addRange(U[] array) {
        Objects.requireNonNull(array);

        for (int i = 0; i < array.length; i++) {
            add(array[i]);
        }
    }

    void clear();

    @Override
    default boolean isReadOnly() {
        return false;
    }

    boolean remove(T item);

    int removeAll(Predicate<? super T> match);
}
