package org.ccci.gto.globalreg.serializer.json;

import com.google.common.base.Throwables;
import org.ccci.gto.globalreg.serializer.Serializer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonSerializer implements Serializer {
    @Override
    public <T> T toObject(final Class<T> clazz, final String raw) {
        try {
            if (JSONObject.class.equals(clazz)) {
                return clazz.cast(new JSONObject(raw));
            } else if (JSONArray.class.equals(clazz)) {
                return clazz.cast(new JSONArray(raw));
            } else {
                throw new UnsupportedOperationException("Unsupported class for JsonSerializer: " + clazz.getName());
            }
        } catch (final JSONException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public <T> String fromObject(final Class<T> clazz, final T object) {
        if (object instanceof JSONArray || object instanceof JSONObject) {
            return object.toString();
        } else {
            throw new UnsupportedOperationException("Unsupported class for JsonSerializer: " + clazz.getName());
        }
    }
}
