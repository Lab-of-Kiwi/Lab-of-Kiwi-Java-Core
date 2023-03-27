package com.labofkiwi;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.function.Supplier;

import com.labofkiwi.collections.IFixedSizeList;
import com.labofkiwi.collections.IIterator;

public final class Array<T> implements IFixedSizeList<T> {
    @SuppressWarnings("rawtypes")
    private static final Array EMPTY = new Array(0);

    private final Object[] elements;

    public Array(int length) {
        elements = new Object[length];
    }

    public Array(int length, IntFunction<? extends T> supplier) {
        Objects.requireNonNull(supplier);
        elements = new Object[length];

        for (int i = 0; i < length; i++) {
            elements[i] = supplier.apply(i);
        }
    }

    public Array(int length, Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier);
        elements = new Object[length];

        for (int i = 0; i < length; i++) {
            elements[i] = supplier.get();
        }
    }

    public Array(int length, T value) {
        elements = new Object[length];

        for (int i = 0; i < length; i++) {
            elements[i] = value;
        }
    }

    @Override
    public int count() {
        return elements.length;
    }

    @Override
    public void fill(T value) {
        Arrays.fill(elements, value);
    }

    @Override
    public T get(int index) {
        @SuppressWarnings("unchecked")
        T value = (T)elements[index];
        return value;
    }

    @Override
    public IIterator<T> iterator() {
        return new ArrayIterator<>(this);
    }

    @Override
    public void set(int index, T value) {
        elements[index] = value;
    }

    public static <T> void copy(Array<T> source, int sourceIndex, Array<? super T> destination, int destinationIndex, int count) {
        System.arraycopy(source.elements, sourceIndex, destination.elements, destinationIndex, count);
    }

    public static <T> Array<T> empty() {
        @SuppressWarnings("unchecked")
        Array<T> result = (Array<T>)EMPTY;
        return result;
    }

    private static final class ArrayIterator<T> implements IIterator<T> {
        private final Array<T> array;
        private int index;

        public ArrayIterator(Array<T> array) {
            this.array = array;
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < array.count();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return array.get(index++);
        }

        @Override
        public void reset() {
            index = 0;
        }
    }
}
