package com.labofkiwi.collections;

import java.util.Comparator;

import com.labofkiwi.ThrowHelper;
import com.labofkiwi.UInt;

public interface IFixedSizeList<T> extends IReadOnlyList<T> {
    default void fill(T value) {
        int count = count();

        for (int i = 0; i < count; i++) {
            set(i, value);
        }
    }

    @Override
    default boolean isReadOnly() {
        return false;
    }

    default void reverse() {
        int count = count();

        if (count < 2) {
            return;
        }

        int i = 0;
        int j = count - 1;

        while (i < j) {
            T temp = get(i);
            set(i, get(j));
            set(j, temp);
            i++;
            j--;
        }
    }

    void set(int index, T value);

    @Override
    default IFixedSizeList<T> slice(int startIndex) {
        return slice(startIndex, count() - startIndex);
    }

    @Override
    default IFixedSizeList<T> slice(int startIndex, int count) {
        ThrowHelper.validateRange(startIndex, count, count());
        return new Slice<>(this, startIndex, count);
    }

    default void sort() {
        sort(null);
    }

    default void sort(Comparator<? super T> comparator) {
        new Sorter<>(this, comparator).sort(0, count());
    }

    default boolean trySet(int index, T value) {
        if (UInt.gte(index, count())) {
            return false;
        }

        set(index, value);
        return true;
    }
}
