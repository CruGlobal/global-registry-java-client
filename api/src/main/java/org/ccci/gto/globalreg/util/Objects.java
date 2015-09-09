package org.ccci.gto.globalreg.util;

import java.util.Arrays;

/**
 * Implements Java 7's Object class
 *
 * @author Bill Randall
 */
public class Objects {
    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

    public static int hash(Object... values)
    {
        return Arrays.hashCode(values);
    }
}
