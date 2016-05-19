package org.ccci.gto.globalreg.serializer.jsonpath;

import com.jayway.jsonpath.JsonModel;
import com.jayway.jsonpath.internal.Utils;
import org.ccci.gto.globalreg.Type;
import org.ccci.gto.globalreg.serializer.JsonIntermediateSerializer;
import org.ccci.gto.globalreg.serializer.SerializerException;
import org.ccci.gto.globalreg.serializer.UnparsableJsonException;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class JsonPathSerializer extends JsonIntermediateSerializer<JsonModel, JsonModel> {
    @Nonnull
    @Override
    protected IntJsonObj stringToJsonObj(@Nonnull final String raw) throws UnparsableJsonException {
        return new IntJsonObj(JsonModel.model(raw));
    }

    @Override
    protected String jsonObjToString(final JsonObj<JsonModel, JsonModel> json) {
        return json.getRawObject().toJson();
    }

    @Override
    protected <T> JsonObj<JsonModel, JsonModel> entityToJsonObj(final Type<T> type, T entity) {
        final Class<? extends T> clazz = type.getEntityClass();
        if (JsonModel.class.equals(clazz)) {
            return new IntJsonObj((JsonModel) entity);
        } else {
            throw new UnsupportedOperationException("Unsupported class for JsonPathSerializer: " + clazz.getName());
        }
    }

    @Override
    protected <T> T jsonObjToEntity(@Nonnull final Type<T> type, @Nonnull final JsonObj<JsonModel, JsonModel> json)
            throws SerializerException {
        final Class<? extends T> clazz = type.getEntityClass();
        if (JsonModel.class.equals(clazz)) {
            return clazz.cast(json.getRawObject());
        } else {
            throw new UnsupportedOperationException("Unsupported class for JsonPathSerializer: " + clazz.getName());
        }
    }

    @Nonnull
    @Override
    protected IntJsonObj emptyJsonObj() {
        return new IntJsonObj(JsonModel.create("{}"));
    }

    private static class IntJsonObj extends JsonObj<JsonModel, JsonModel> {
        private final JsonModel.ObjectOps objOps;

        private IntJsonObj(final JsonModel obj) {
            super(obj);
            this.objOps = obj != null ? obj.opsForObject() : null;
        }

        @Override
        protected IntJsonObj wrap(final String key) {
            final IntJsonObj newObj = new IntJsonObj(JsonModel.model("{}"));
            newObj.objOps.put(key, this.obj != null ? this.obj.getJsonObject() : null);
            return newObj;
        }

        @Nonnull
        @Override
        protected IntJsonObj getObject(final String key) {
            return new IntJsonObj(obj.getSubModel(key));
        }

        @Override
        protected IntJsonArr getArray(final String key) {
            return new IntJsonArr(this.objOps != null && this.objOps.containsKey(key) ? obj.getSubModel(key) : null);
        }

        @Override
        protected Long getLong(final String key, final Long def) {
            try {
                return this.objOps != null && this.objOps.containsKey(key) ? this.objOps.getLong(key) : def;
            } catch (final Exception e) {
                return def;
            }
        }

        @Override
        protected Double getDouble(final String key, final Double def) {
            try {
                return this.objOps != null && this.objOps.containsKey(key) ? this.objOps.getDouble(key) : def;
            } catch (final Exception e) {
                return def;
            }
        }

        @Override
        protected Boolean getBoolean(final String key, final Boolean def) {
            try {
                final String val = this.objOps.getString(key);
                return "true".equalsIgnoreCase(val) ? Boolean.TRUE : "false".equalsIgnoreCase(val) ? Boolean.FALSE :
                        def;
            } catch (final Exception e) {
                return def;
            }
        }

        @Override
        protected String getString(final String key, final String def) {
            try {
                return this.objOps != null && this.objOps.containsKey(key) ? this.objOps.getString(key) : def;
            } catch (final Exception e) {
                return def;
            }
        }

        @Override
        protected IntJsonObj put(final String key, final Integer val) {
            this.objOps.put(key, val);
            return this;
        }

        @Override
        protected IntJsonObj put(final String key, final Long val) {
            this.objOps.put(key, val);
            return this;
        }

        @Override
        protected JsonObj<JsonModel, JsonModel> put(final String key, final String val) {
            this.objOps.put(key, val);
            return this;
        }
    }

    private static class IntJsonArr extends JsonArr<JsonModel, JsonModel> {
        private final JsonModel.ArrayOps arrOps;
        private final List<Object> list;

        private IntJsonArr(final JsonModel arr) {
            super(arr);
            this.arrOps = arr != null && arr.isList() ? arr.opsForArray() : null;
            this.list = this.arrOps != null ? this.arrOps.getTarget() : Collections.emptyList();
        }

        @Override
        protected int size() {
            return this.list.size();
        }

        @Override
        protected IntJsonObj getObject(final int index) {
            return new IntJsonObj(arr.getSubModel("[" + Integer.toString(index) + "]"));
        }

        @Override
        protected String getString(final int index, final String def) {
            try {
                return Utils.toString(this.list.get(index));
            } catch (final Exception e) {
                return def;
            }
        }
    }
}
