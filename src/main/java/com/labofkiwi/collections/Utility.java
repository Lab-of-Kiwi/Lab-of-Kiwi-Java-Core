package com.labofkiwi.collections;

final class Utility {
    private Utility() { }

    public static int getMedian(int low, int hi) {
        assert low <= hi;
        assert hi - low >= 0 : "Length overflow!";
        return low + ((hi - low) >> 1);
    }
}
