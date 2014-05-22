package org.ccci.gto.globalreg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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

    @Test
    public void testValue() throws Exception {
        { // value(String) tests
            // test setting a null value
            assertEquals(new Filter(), new Filter().value((String) null));
            assertEquals(new Filter(), new Filter().value("a").value((String) null));
            assertEquals(new Filter(), new Filter().values("a", "b").value((String) null));

            // null is different from ""
            assertNotEquals("value(null) and value(\"\") should produce different filters", new Filter().value(""),
                    new Filter().value((String) null));
        }
    }
}
