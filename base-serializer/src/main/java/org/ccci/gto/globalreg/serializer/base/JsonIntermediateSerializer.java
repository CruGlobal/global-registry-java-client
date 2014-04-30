package org.ccci.gto.globalreg.serializer.base;

import com.google.common.primitives.Ints;
import org.ccci.gto.globalreg.System;
import org.ccci.gto.globalreg.Type;
import org.ccci.gto.globalreg.serializer.AbstractSerializer;
import org.ccci.gto.globalreg.serializer.SerializerException;

import java.util.ArrayList;
import java.util.List;

public abstract class JsonIntermediateSerializer<O, A> extends AbstractSerializer {
    @Override
    public <T> T deserializeEntity(final Type<T> type, final String raw) throws SerializerException {
        final JsonObj<O, A> json = this.parseJsonObj(raw).getObject("entity").getObject(type.getEntityType());
        return jsonObjToEntity(type, json);
    }

    @Override
    public System deserializeSystem(final String raw) throws UnparsableJsonException {
        return this.parseSystem(this.parseJsonObj(raw).getObject("system"));
    }

    @Override
    public List<System> deserializeSystems(final String raw) throws UnparsableJsonException {
        final JsonArr<O, A> json = this.parseJsonObj(raw).getArray("systems");

        // process into an array & return
        final List<System> systems = new ArrayList<>(json.size());
        for (int i = 0; i < json.size(); i++) {
            systems.add(this.parseSystem(json.getObject(i)));
        }
        return systems;
    }

    private System parseSystem(final JsonObj<O, A> json) {
        // build & return System object
        final System system = new System();
        system.setId(json.getLong("id"));
        system.setName(json.getString("name"));
        system.setRoot(json.getBoolean("root"));
        system.setTrusted(json.getBoolean("is_trusted"));
        system.setAccessToken(json.getString("access_token"));
        return system;
    }

    protected abstract JsonObj<O, A> parseJsonObj(String raw) throws UnparsableJsonException;

    protected abstract <T> T jsonObjToEntity(Type<T> type, JsonObj<O, A> json) throws SerializerException;

    protected abstract static class JsonObj<O, A> {
        protected final O obj;

        protected JsonObj(final O obj) {
            this.obj = obj;
        }

        public O getRawObject() {
            return obj;
        }

        protected abstract JsonObj<O, A> getObject(String key);

        protected abstract JsonArr<O, A> getArray(String key);

        protected Integer getInt(final String key) {
            return this.getInt(key, null);
        }

        protected Integer getInt(final String key, final Integer def) {
            final Long val = this.getLong(key, null);
            return val != null ? Ints.checkedCast(val) : def;
        }

        protected Long getLong(final String key) {
            return this.getLong(key, null);
        }

        protected Long getLong(final String key, final Long def) {
            final Integer val = this.getInt(key, null);
            return val != null ? Long.valueOf(val.longValue()) : def;
        }

        protected Boolean getBoolean(final String key) {
            return this.getBoolean(key, null);
        }

        protected abstract Boolean getBoolean(String key, Boolean def);

        protected String getString(final String key) {
            return this.getString(key, null);
        }

        protected abstract String getString(String key, String def);
    }

    protected abstract static class JsonArr<O, A> {
        protected final A arr;

        protected JsonArr(final A arr) {
            this.arr = arr;
        }

        protected abstract int size();

        protected abstract JsonObj<O, A> getObject(int index);


    }
}
