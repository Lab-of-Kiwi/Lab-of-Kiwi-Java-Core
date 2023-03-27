package com.labofkiwi.collections;

import java.util.Comparator;

import com.labofkiwi.DefaultComparator;
import com.labofkiwi.numerics.BitOperations;

final class Sorter<T> {
    private static final int IntrosortSizeThreshold = 16;

    private final IFixedSizeList<T> keys;
    private final Comparator<? super T> comparator;

    public Sorter(IFixedSizeList<T> keys, Comparator<? super T> comparator) {
        this.keys = keys;
        this.comparator = comparator != null ? comparator : DefaultComparator.getInstance();
    }

    private void downHeap(int i, int n, int lo) {
        T d = keys.get(lo + i - 1);
        int child;

        while (i <= n / 2) {
            child = 2 * i;
            if (child < n && comparator.compare(keys.get(lo + child - 1), keys.get(lo + child)) < 0) {
                child++;
            }

            if (!(comparator.compare(d, keys.get(lo + child - 1)) < 0)) {
                break;
            }

            keys.set(lo + i - 1, keys.get(lo + child - 1));
            i = child;
        }

        keys.set(lo + i - 1, d);
    }

    private void heapSort(int lo, int hi) {
        int n = hi - lo + 1;

        for (int i = n / 2; i >= 1; i--) {
            downHeap(i, n, lo);
        }

        for (int i = n; i > 1; i--) {
            swap(lo, lo + i - 1);
            downHeap(1, i - 1, lo);
        }
    }

    private void insertionSort(int lo, int hi) {
        int i, j;
        T t;

        for (i = lo; i < hi; i++) {
            j = i;
            t = keys.get(i + 1);

            while (j >= lo && comparator.compare(t, keys.get(j)) < 0) {
                keys.set(j + 1, keys.get(j));
                j--;
            }

            keys.set(j + 1, t);
        }
    }

    private void introSort(int lo, int hi, int depthLimit) {
        assert hi >= lo;
        assert depthLimit >= 0;

        while (hi > lo) {
            int partitionSize = hi - lo + 1;

            if (partitionSize <= IntrosortSizeThreshold) {
                assert partitionSize >= 2;

                if (partitionSize == 2) {
                    swapIfGreater(lo, hi);
                    return;
                }

                if (partitionSize == 3) {
                    swapIfGreater(lo, hi - 1);
                    swapIfGreater(lo, hi);
                    swapIfGreater(hi - 1, hi);
                    return;
                }

                insertionSort(lo, hi);
                return;
            }

            if (depthLimit == 0) {
                heapSort(lo, hi);
                return;
            }

            depthLimit--;

            int p = pickPivotAndPartition(lo, hi);
            introSort(p + 1, hi, depthLimit);
            hi = p - 1;
        }
    }

    private void introspectiveSort(int left, int length) {
        if (length < 2) {
            return;
        }

        try {
            introSort(left, length + left - 1, 2 * (BitOperations.log2(length) + 1));
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(String.format("Unable to sort because the Comparator.compare() method returns inconsistent results. Either a value does not compare equal to itself, or one value repeatedly compared to another value yields different results. Comparator: '%s'.", comparator));
        } catch (Exception e) {
            throw new IllegalStateException("Failed to compare two elements.", e);
        }
    }

    private int pickPivotAndPartition(int lo, int hi) {
        assert hi - lo >= IntrosortSizeThreshold;

        // Compute median-of-three.  But also partition them, since we've done the comparison.
        int mid = lo + (hi - lo) / 2;

        // Sort lo, mid and hi appropriately, then pick mid as the pivot.
        swapIfGreater(lo, mid);
        swapIfGreater(lo, hi);
        swapIfGreater(mid, hi);

        T pivot = keys.get(mid);
        swap(mid, hi - 1);
        int left = lo, right = hi - 1;  // We already partitioned lo and hi and put the pivot in hi - 1.  And we pre-increment & decrement below.

        while (left < right) {
            while (comparator.compare(keys.get(++left), pivot) < 0);
            while (comparator.compare(pivot, keys.get(--right)) < 0);

            if (left >= right) {
                break;
            }

            swap(left, right);
        }

        // Put pivot in the right location.
        if (left != hi - 1) {
            swap(left, hi - 1);
        }

        return left;
    }

    public void sort(int left, int length) {
        introspectiveSort(left, length);
    }

    private void swap(int i, int j) {
        T t = keys.get(i);
        keys.set(i, keys.get(j));
        keys.set(j, t);
    }

    private void swapIfGreater(int a, int b) {
        if (a != b) {
            if (comparator.compare(keys.get(a), keys.get(b)) > 0) {
                T temp = keys.get(a);
                keys.set(a, keys.get(b));
                keys.set(b, temp);
            }
        }
    }
}
