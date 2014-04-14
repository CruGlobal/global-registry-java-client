package org.ccci.gto.globalreg.serializer.json;

import com.google.common.base.Throwables;
import org.ccci.gto.globalreg.EntityClass;
import org.ccci.gto.globalreg.serializer.AbstractSerializer;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;

public class JsonSerializer extends AbstractSerializer {
    @Override
    public <T> T toObject(final EntityClass<T> type, final String raw) {
        final Class<T> clazz = type.getEntityClass();
        try {
            if (JSONObject.class.equals(clazz)) {
                final JSONObject json = new JSONObject(raw);
                return clazz.cast(json.optJSONObject("entity").optJSONObject(type.getEntityType()));
            } else {
                throw new UnsupportedOperationException("Unsupported class for JsonSerializer: " + clazz.getName());
            }
        } catch (final JSONException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public <T> String fromObject(final EntityClass<T> type, final T object) {
        final Class<T> clazz = type.getEntityClass();
        if (object instanceof JSONObject) {
            return new JSONObject(Collections.singletonMap("entity", new JSONObject(Collections.singletonMap(type
                    .getEntityType(), object)))).toString();
        } else {
            throw new UnsupportedOperationException("Unsupported class for JsonSerializer: " + clazz.getName());
        }
    }
}
