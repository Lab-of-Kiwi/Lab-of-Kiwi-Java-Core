package com.labofkiwi.collections;

import java.util.NoSuchElementException;

import com.labofkiwi.ThrowHelper;

class ReadOnlySlice<T> implements IReadOnlyList<T> {
    protected final IReadOnlyList<T> list;
    protected final int startIndex;
    protected final int count;

    public ReadOnlySlice(IReadOnlyList<T> list, int startIndex, int count) {
        assert list != null;
        assert !(list instanceof ReadOnlySlice);
        assert (startIndex & 0xFFFFFFFFL) <= (list.count() & 0xFFFFFFFFL);
        assert count >= 0;
        assert ((startIndex + count) & 0xFFFFFFFFL) <= (list.count() & 0xFFFFFFFFL);

        this.list = list;
        this.startIndex = startIndex;
        this.count = count;
    }

    @Override
    public final int count() {
        return count;
    }

    @Override
    public final T get(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException(index);
        }

        return list.get(startIndex + index);
    }

    @Override
    public final IIterator<T> iterator() {
        return new SliceIterator<>(this);
    }

    @Override
    public IReadOnlyList<T> slice(int startIndex, int count) {
        ThrowHelper.validateRange(startIndex, count, this.count);
        return new ReadOnlySlice<>(list, startIndex + this.startIndex, count);
    }

    private static final class SliceIterator<T> implements IIterator<T> {
        private final ReadOnlySlice<T> slice;
        private int index;

        public SliceIterator(ReadOnlySlice<T> slice) {
            this.slice = slice;
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < slice.count();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return slice.get(index++);
        }

        @Override
        public void reset() {
            index = 0;
        }
    }
}
