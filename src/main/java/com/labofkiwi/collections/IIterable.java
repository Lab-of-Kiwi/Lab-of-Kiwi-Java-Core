package com.labofkiwi.collections;

/**
 * Extension of the {@link Iterable} interface.
 *
 * @param <T> The type of elements returned by the iterator.
 */
public interface IIterable<T> extends Iterable<T> {
    // TODO Query

    @Override
    IIterator<T> iterator();
}
