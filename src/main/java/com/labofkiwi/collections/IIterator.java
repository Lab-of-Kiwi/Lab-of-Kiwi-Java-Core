package com.labofkiwi.collections;

import java.util.Iterator;

public interface IIterator<T> extends Iterator<T> {
    void reset();
}
