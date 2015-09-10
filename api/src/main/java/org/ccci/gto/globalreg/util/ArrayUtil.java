package org.ccci.gto.globalreg.util;

import java.util.Arrays;

public final class ArrayUtil {
    private ArrayUtil() {}

    public static <T> T[] merge(final T[] first, final T... second) {
        final T[] output = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, output, first.length, second.length);
        return output;
    }
}
