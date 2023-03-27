package com.labofkiwi.collections;

import java.util.Objects;

import com.labofkiwi.ThrowHelper;
import com.labofkiwi.UInt;

public interface IList<T> extends IFixedSizeList<T>, ICollection<T> {
    void insert(int index, T item);

    default void insertRange(int index, Iterable<? extends T> collection) {
        Objects.requireNonNull(collection);

        if (collection instanceof IReadOnlyList) {
            IReadOnlyList<? extends T> list = (IReadOnlyList<? extends T>)collection;

            for (int i = 0; i < list.count(); i++) {
                insert(index++, list.get(i));
            }
        }
        else {
            for (T element : collection) {
                insert(index++, element);
            }
        }
    }

    default <U extends T> void insertRange(int index, U[] array) {
        Objects.requireNonNull(array);

        for (int i = 0; i < array.length; i++) {
            insert(index++, array[i]);
        }
    }

    @Override
    default boolean isFixedSize() {
        return false;
    }

    @Override
    default boolean isReadOnly() {
        return false;
    }

    T removeAt(int index);

    default void removeRange(int startIndex, int count) {
        ThrowHelper.validateRange(startIndex, count, count());

        for (int i = 0; i < count; i++) {
            removeAt(startIndex);
        }
    }

    default boolean setOrAdd(int index, T item) {
        int count = count();

        if (UInt.gt(index, count)) {
            throw new IndexOutOfBoundsException(index);
        }

        if (index == count) {
            add(item);
            return true;
        }

        set(index, item);
        return false;
    }
}
