package org.ccci.gto.globalreg.serializer.json;

import org.ccci.gto.globalreg.Type;
import org.ccci.gto.globalreg.serializer.base.JsonIntermediateSerializer;
import org.ccci.gto.globalreg.serializer.base.UnparsableJsonException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

public class JsonSerializer extends JsonIntermediateSerializer<JSONObject, JSONArray> {
    private static final Logger LOG = LoggerFactory.getLogger(JsonSerializer.class);

    @Override
    protected IntJsonObj emptyJsonObj() {
        return new IntJsonObj(new JSONObject());
    }

    @Override
    protected IntJsonObj stringToJsonObj(final String raw) throws UnparsableJsonException {
        try {
            return new IntJsonObj(new JSONObject(raw));
        } catch (final JSONException e) {
            LOG.debug("JSON parsing error", e);
            throw new UnparsableJsonException(e);
        }
    }

    @Override
    protected String jsonObjToString(final JsonObj<JSONObject, JSONArray> json) {
        final JSONObject obj = json.getRawObject();
        return obj != null ? obj.toString() : "";
    }

    @Override
    protected <T> IntJsonObj entityToJsonObj(final Type<T> type, final T entity) {
        final Class<? extends T> clazz = type.getEntityClass();
        if (JSONObject.class.equals(clazz)) {
            return new IntJsonObj((JSONObject) entity);
        } else {
            throw new UnsupportedOperationException("Unsupported class for JsonSerializer: " + clazz.getName());
        }
    }

    @Override
    protected <T> T jsonObjToEntity(final Type<T> type, final JsonObj<JSONObject, JSONArray> json) {
        final Class<? extends T> clazz = type.getEntityClass();
        if (JSONObject.class.equals(clazz)) {
            return clazz.cast(json.getRawObject());
        } else {
            throw new UnsupportedOperationException("Unsupported class for JsonSerializer: " + clazz.getName());
        }
    }

    private static class IntJsonObj extends JsonObj<JSONObject, JSONArray> {
        private IntJsonObj(final JSONObject obj) {
            super(obj);
        }

        @Override
        protected IntJsonObj wrap(final String key) {
            return new IntJsonObj(new JSONObject(Collections.singletonMap(key, obj)));
        }

        @Override
        protected IntJsonObj getObject(final String key) {
            if (obj == null) {
                return this;
            }
            return new IntJsonObj(obj.optJSONObject(key));
        }

        @Override
        protected IntJsonArr getArray(final String key) {
            return new IntJsonArr(obj != null ? obj.optJSONArray(key) : null);
        }

        @Override
        protected Integer getInt(final String key, final Integer def) {
            try {
                return obj != null ? obj.getInt(key) : def;
            } catch (final JSONException e) {
                return def;
            }
        }

        @Override
        protected Long getLong(final String key, final Long def) {
            try {
                return obj != null ? obj.getLong(key) : def;
            } catch (final JSONException e) {
                return def;
            }
        }

        @Override
        protected Boolean getBoolean(final String key, final Boolean def) {
            try {
                return obj != null ? obj.getBoolean(key) : def;
            } catch (final JSONException e) {
                return def;
            }
        }

        @Override
        protected String getString(final String key, final String def) {
            return obj != null ? obj.optString(key, def) : def;
        }

        @Override
        protected IntJsonObj put(final String key, final Integer val) {
            if (obj != null) {
                obj.put(key, val);
            }

            return this;
        }

        @Override
        protected IntJsonObj put(final String key, final String val) {
            if (obj != null) {
                obj.put(key, val);
            }

            return this;
        }
    }

    private static class IntJsonArr extends JsonArr<JSONObject, JSONArray> {
        protected IntJsonArr(final JSONArray arr) {
            super(arr);
        }

        @Override
        protected int size() {
            return arr != null ? arr.length() : 0;
        }

        @Override
        protected IntJsonObj getObject(final int index) {
            return new IntJsonObj(arr == null ? null : arr.optJSONObject(index));
        }
    }
}
