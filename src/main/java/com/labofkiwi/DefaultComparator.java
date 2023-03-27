package com.labofkiwi;

import java.util.Comparator;

public final class DefaultComparator implements Comparator<Object> {
    private static final DefaultComparator INSTANCE = new DefaultComparator();

    private DefaultComparator() { }

    @Override
    public int compare(Object o1, Object o2) {
        @SuppressWarnings("rawtypes")
        Comparable c1 = (Comparable)o1;

        @SuppressWarnings("rawtypes")
        Comparable c2 = (Comparable)o2;

        @SuppressWarnings("unchecked")
        int result = c1.compareTo(c2);
        return result;
    }

    public static <T> Comparator<T> getInstance() {
        @SuppressWarnings("unchecked")
        Comparator<T> result = (Comparator<T>)INSTANCE;
        return result;
    }
}
