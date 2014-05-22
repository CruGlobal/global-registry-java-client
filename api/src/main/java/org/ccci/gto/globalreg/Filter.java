package org.ccci.gto.globalreg;

import org.ccci.gto.globalreg.util.ArrayUtil;

import java.io.Serializable;
import java.util.Arrays;

public final class Filter implements Serializable {
    private static final long serialVersionUID = 7723108591216268801L;

    // several common filters
    public static final Filter OWNED_BY = new Filter().path("owned_by");
    public static final Filter PERIOD_FROM = new Filter().path("period_from");
    public static final Filter PERIOD_TO = new Filter().path("period_to");

    private final String[] path;
    private final String[] values;

    public Filter() {
        this(null, null);
    }

    public Filter(final String[] path, final String[] values) {
        this.path = path != null ? path : new String[0];
        this.values = values != null ? values : new String[0];
    }

    public final Filter path(final String... path) {
        return new Filter(path, this.values);
    }

    public final Filter prependPath(final String... path) {
        // short-circuit if there is nothing to do
        if (path == null || path.length == 0) {
            return this;
        }

        // return a filter with the merged path
        return this.path(ArrayUtil.merge(path, this.path));
    }

    @Deprecated
    public final Filter value(final String value) {
        return new Filter(this.path, value != null ? new String[]{value} : new String[0]);
    }

    public final Filter value(final MeasurementType.Category category) {
        return this.value(category != null ? category.toString() : null);
    }

    public final Filter values(final String... values) {
        return new Filter(this.path, values);
    }

    public final Filter appendValues(final String... values) {
        // short-circuit if there is nothing to do
        if (values == null || values.length == 0) {
            return this;
        }

        // return a filter with the appended values
        return new Filter(this.path, ArrayUtil.merge(this.values, values));
    }

    public final boolean isValid() {
        return this.path.length > 0 && this.values.length > 0;
    }

    public final String[] getPath() {
        return this.path;
    }

    public final String[] getValues() {return this.values;}

    @Deprecated
    public final String getValue() {
        return this.values.length > 1 ? this.values[0] : "";
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Filter filter = (Filter) o;
        return Arrays.equals(this.path, filter.path) && Arrays.equals(this.values, filter.values);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(this.path);
        result = 31 * result + Arrays.hashCode(this.values);
        return result;
    }
}
