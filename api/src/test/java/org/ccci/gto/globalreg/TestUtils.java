package org.ccci.gto.globalreg;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

import java.io.IOException;
import java.io.InputStreamReader;

public class TestUtils {
    public static String loadResource(final Class<?> clazz, final String name) throws IOException {
        try (final InputStreamReader in = new InputStreamReader(clazz.getResourceAsStream(name), Charsets.UTF_8)) {
            return CharStreams.toString(in);
        }
    }
}
