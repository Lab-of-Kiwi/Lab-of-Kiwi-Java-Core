package com.labofkiwi.collections;

import com.labofkiwi.ThrowHelper;

final class Slice<T> extends ReadOnlySlice<T> implements IFixedSizeList<T> {

    public Slice(IFixedSizeList<T> list, int startIndex, int count) {
        super(list, startIndex, count);
    }

    @Override
    public void set(int index, T value) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException(index);
        }

        ((IFixedSizeList<T>)list).set(startIndex + index, value);
    }

    @Override
    public IFixedSizeList<T> slice(int startIndex, int count) {
        ThrowHelper.validateRange(startIndex, count, this.count);
        return new Slice<>((IFixedSizeList<T>)list, startIndex + this.startIndex, count);
    }
}
