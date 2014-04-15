package org.ccci.gto.globalreg;

import com.google.common.io.CharStreams;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class TestUtils {
    public static String loadResource(final Class<?> clazz, final String name) throws IOException {
        try (final InputStreamReader in = new InputStreamReader(clazz.getResourceAsStream(name),
                StandardCharsets.UTF_8)) {
            return CharStreams.toString(in);
        }
    }
}
