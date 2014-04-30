package org.ccci.gto.globalreg.serializer.base;

import com.google.common.primitives.Ints;
import org.ccci.gto.globalreg.EntityType;
import org.ccci.gto.globalreg.MeasurementType;
import org.ccci.gto.globalreg.RegisteredSystem;
import org.ccci.gto.globalreg.ResponseList;
import org.ccci.gto.globalreg.Type;
import org.ccci.gto.globalreg.serializer.AbstractSerializer;
import org.ccci.gto.globalreg.serializer.SerializerException;

import java.util.ArrayList;
import java.util.List;

public abstract class JsonIntermediateSerializer<O, A> extends AbstractSerializer {
    @Override
    public <T> String serializeEntity(final Type<T> type, final T entity) {
        return this.jsonObjToString(this.entityToJsonObj(type, entity).wrap(type.getEntityType()).wrap("entity"));
    }

    @Override
    public <T> T deserializeEntity(final Type<T> type, final String raw) throws SerializerException {
        final JsonObj<O, A> json = this.stringToJsonObj(raw).getObject("entity").getObject(type.getEntityType());
        return jsonObjToEntity(type, json);
    }

    @Override
    public <T> ResponseList<T> deserializeEntities(final Type<T> type, final String raw) throws SerializerException {
        final ResponseList<T> list = new ResponseList<>();

        final JsonObj<O, A> json = this.stringToJsonObj(raw);
        final JsonArr<O, A> entities = json.getArray("entities");
        for (int i = 0; i < entities.size(); i++) {
            list.add(jsonObjToEntity(type, entities.getObject(i).getObject(type.getEntityType())));
        }

        // parse the meta-data
        populateResponseListMeta(list, json);

        // return the entities list
        return list;
    }

    @Override
    public String serializeEntityType(final EntityType type) {
        final JsonObj<O, A> json = this.emptyJsonObj();
        json.put("name", type.getName()).put("description", type.getDescription());

        final EntityType.FieldType fieldType = type.getFieldType();
        if (fieldType != EntityType.FieldType.UNKNOWN && fieldType != EntityType.FieldType.NONE) {
            json.put("field_type", type.getFieldType().toString());
        }
        if (type.hasParent()) {
            json.put("parent_id", type.getParentId());
        }

        // wrap and return the json
        return this.jsonObjToString(json.wrap("entity_type"));
    }

    @Override
    public EntityType deserializeEntityType(final String raw) throws UnparsableJsonException {
        return this.parseEntityType(this.stringToJsonObj(raw).getObject("entity_type"));
    }

    @Override
    public ResponseList<EntityType> deserializeEntityTypes(final String raw) throws UnparsableJsonException {
        final ResponseList<EntityType> list = new ResponseList<>();

        final JsonObj<O, A> json = this.stringToJsonObj(raw);
        final JsonArr<O, A> types = json.getArray("entity_types");
        for (int i = 0; i < types.size(); i++) {
            list.add(this.parseEntityType(types.getObject(i)));
        }

        // parse the meta-data
        populateResponseListMeta(list, json);

        return list;
    }

    @Override
    public RegisteredSystem deserializeSystem(final String raw) throws UnparsableJsonException {
        return this.parseSystem(this.stringToJsonObj(raw).getObject("system"));
    }

    @Override
    public List<RegisteredSystem> deserializeSystems(final String raw) throws UnparsableJsonException {
        final JsonArr<O, A> json = this.stringToJsonObj(raw).getArray("systems");

        // process into an array & return
        final List<RegisteredSystem> systems = new ArrayList<>(json.size());
        for (int i = 0; i < json.size(); i++) {
            systems.add(this.parseSystem(json.getObject(i)));
        }
        return systems;
    }

    @Override
    public MeasurementType deserializeMeasurementType(final String raw) throws UnparsableJsonException {
        return this.parseMeasurementType(this.stringToJsonObj(raw).getObject("measurement_type"));
    }

    @Override
    public ResponseList<MeasurementType> deserializeMeasurementTypes(final String raw) throws SerializerException {
        final ResponseList<MeasurementType> list = new ResponseList<>();

        final JsonObj<O, A> json = this.stringToJsonObj(raw);
        final JsonArr<O, A> types = json.getArray("measurement_types");
        for (int i = 0; i < types.size(); i++) {
            list.add(this.parseMeasurementType(types.getObject(i)));
        }

        // parse the meta-data
        populateResponseListMeta(list, json);

        return list;
    }

    private EntityType parseEntityType(final JsonObj<O, A> json) {
        return this.parseEntityType(json, null);
    }

    private EntityType parseEntityType(final JsonObj<O, A> json, final EntityType parent) {
        final EntityType type = new EntityType();

        // set the parent
        final Long parentId = json.getLong("parent_id", null);
        if (parent != null && parentId != null && !parentId.equals(parent.getId())) {
            throw new IllegalArgumentException("Specified parent object does not match the referenced parent object");
        } else if (parentId != null && parent == null) {
            type.setParentId(parentId);
        } else {
            type.setParent(parent);
        }

        type.setId(json.getLong("id", null));
        type.setName(json.getString("name", null));
        type.setDescription(json.getString("description", null));
        type.setFieldType(json.getString("field_type", "entity"));

        // parse nested fields
        final JsonArr<O, A> fields = json.getArray("fields");
        for (int i = 0; i < fields.size(); i++) {
            type.addField(this.parseEntityType(fields.getObject(i), type));
        }

        // return the parsed entity_type
        return type;
    }

    private RegisteredSystem parseSystem(final JsonObj<O, A> json) {
        // build & return System object
        final RegisteredSystem system = new RegisteredSystem();
        system.setId(json.getLong("id"));
        system.setName(json.getString("name"));
        system.setRoot(json.getBoolean("root"));
        system.setTrusted(json.getBoolean("is_trusted"));
        system.setAccessToken(json.getString("access_token"));
        return system;
    }

    private void populateResponseListMeta(final ResponseList<?> list, final JsonObj<O, A> json) {
        // parse the meta-data
        final JsonObj<O, A> metaJson = json.getObject("meta");
        final ResponseList.Meta meta = list.getMeta();
        meta.setTotal(metaJson.getInt("total", 0));
        meta.setFrom(metaJson.getInt("from", 0));
        meta.setTo(metaJson.getInt("to", 0));
        meta.setPage(metaJson.getInt("page", 0));
        meta.setTotalPages(metaJson.getInt("total_pages", 0));
    }

    private MeasurementType parseMeasurementType(final JsonObj<O, A> json) {
        // build & return MeasurementType
        final MeasurementType type = new MeasurementType();
        type.setId(json.getLong("id"));
        type.setName(json.getString("name"));
        type.setDescription(json.getString("description"));
        type.setCategory(json.getString("category"));
        type.setFrequency(json.getString("frequency"));
        type.setUnit(json.getString("unit"));
        type.setRelatedEntityType(json.getLong("related_entity_type_id"));
        return type;
    }

    protected abstract JsonObj<O, A> stringToJsonObj(String raw) throws UnparsableJsonException;

    protected abstract String jsonObjToString(JsonObj<O, A> json);

    protected abstract <T> JsonObj<O, A> entityToJsonObj(Type<T> type, T entity);

    protected abstract <T> T jsonObjToEntity(Type<T> type, JsonObj<O, A> json) throws SerializerException;

    protected abstract JsonObj<O, A> emptyJsonObj();

    protected abstract static class JsonObj<O, A> {
        protected final O obj;

        protected JsonObj(final O obj) {
            this.obj = obj;
        }

        public O getRawObject() {
            return obj;
        }

        protected abstract JsonObj<O, A> wrap(String key);

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

        protected abstract JsonObj<O, A> put(String key, Integer val);

        protected abstract JsonObj<O, A> put(String key, Long val);

        protected abstract JsonObj<O, A> put(String key, String val);
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
