package com.labofkiwi;

public final class UInt {
    private static final long UINT64 = 0xFFFFFFFFL;

    private UInt() { }

    public static boolean gt(int x, int y) {
        return (x & UINT64) > (y & UINT64);
    }

    public static boolean gte(int x, int y) {
        return (x & UINT64) >= (y & UINT64);
    }

    public static boolean lt(int x, int y) {
        return (x & UINT64) < (y & UINT64);
    }

    public static boolean lte(int x, int y) {
        return (x & UINT64) <= (y & UINT64);
    }
}
