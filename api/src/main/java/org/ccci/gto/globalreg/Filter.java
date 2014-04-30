package org.ccci.gto.globalreg;

import org.ccci.gto.globalreg.util.ArrayUtil;

import java.io.Serializable;
import java.util.Arrays;

public final class Filter implements Serializable {
    private static final long serialVersionUID = 1900099970741635758L;

    private final String[] path;
    private final String value;

    public Filter() {
        this(null, null);
    }

    public Filter(final String[] path, final String value) {
        this.path = path != null ? path : new String[0];
        this.value = value != null ? value : "";
    }

    public final Filter path(final String... path) {
        return new Filter(path, this.value);
    }

    public final Filter prependPath(final String... path) {
        // short-circuit if there is nothing to do
        if (path == null || path.length == 0) {
            return this;
        }

        // return a filter with the merged path
        return this.path(ArrayUtil.merge(path, this.path));
    }

    public final Filter value(final String value) {
        return new Filter(this.path, value);
    }

    public final Filter value(final MeasurementType.Category category) {
        return this.value(category != null ? category.toString() : null);
    }

    public final boolean isValid() {
        return this.path.length > 0;
    }

    public final String[] getPath() {
        return this.path;
    }

    public final String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Filter filter = (Filter) o;
        return Arrays.equals(this.path, filter.path) && this.value.equals(filter.value);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(path);
        result = 31 * result + value.hashCode();
        return result;
    }
}
