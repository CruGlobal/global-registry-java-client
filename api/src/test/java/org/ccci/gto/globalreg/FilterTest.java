package org.ccci.gto.globalreg;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FilterTest {
    @Test
    public void testPrependPath() throws Exception {
        final Filter expected = new Filter().path("a", "b", "c", "d");

        assertEquals(expected, new Filter().path("a", "b", "c", "d").prependPath());
        assertEquals(expected, new Filter().path("b", "c", "d").prependPath("a"));
        assertEquals(expected, new Filter().path("c", "d").prependPath("a", "b"));
        assertEquals(expected, new Filter().path("d").prependPath("a", "b", "c"));
        assertEquals(expected, new Filter().path().prependPath("a", "b", "c", "d"));
    }
}
