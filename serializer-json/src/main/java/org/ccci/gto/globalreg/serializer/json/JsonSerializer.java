package org.ccci.gto.globalreg.serializer.json;

import com.google.common.base.Throwables;
import org.ccci.gto.globalreg.EntityType;
import org.ccci.gto.globalreg.ResponseList;
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
    public <T> ResponseList<T> deserializeEntities(final Type<T> type, final String raw) {
        final ResponseList<T> list = new ResponseList<>();

        final Class<? extends T> clazz = type.getEntityClass();
        if (JSONObject.class.equals(clazz)) {
            try {
                final JSONObject json = new JSONObject(raw);
                final JSONArray entities = json.getJSONArray("entities");
                for (int i = 0; i < entities.length(); i++) {
                    list.add(clazz.cast(entities.getJSONObject(i).getJSONObject(type.getEntityType())));
                }

                // parse the meta-data
                populateResponseListMeta(list, json);

                // return the entities list
                return list;
            } catch (final JSONException e) {
                LOG.debug("JSON processing error", e);
                throw Throwables.propagate(e);
            }
        } else {
            throw new UnsupportedOperationException("Unsupported class for JsonSerializer: " + clazz.getName());
        }
    }

    @Override
    public String serializeEntityType(final EntityType type) {
        final JSONObject json = new JSONObject();
        json.put("name", type.getName());
        json.put("description", type.getDescription());
        final EntityType.FieldType fieldType = type.getFieldType();
        if (fieldType != null) {
            json.put("field_type", type.getFieldType().toString());
        }
        if (type.hasParent()) {
            json.put("parent_id", type.getParentId());
        }

        // wrap and return the json
        return this.wrap(json, "entity_type").toString();
    }

    @Override
    public EntityType deserializeEntityType(final String raw) {
        try {
            final JSONObject json = new JSONObject(raw);
            return this.parseEntityType(json.getJSONObject("entity_type"));
        } catch (final JSONException e) {
            LOG.debug("JSON processing error", e);
            throw Throwables.propagate(e);
        }
    }

    @Override
    public ResponseList<EntityType> deserializeEntityTypes(final String raw) {
        final ResponseList<EntityType> list = new ResponseList<>();

        try {
            final JSONObject json = new JSONObject(raw);

            // parse returned entity types
            final JSONArray types = json.getJSONArray("entity_types");
            for (int i = 0; i < types.length(); i++) {
                list.add(this.parseEntityType(types.getJSONObject(i)));
            }

            // parse the meta-data
            populateResponseListMeta(list, json);

            // return the entity types list
            return list;
        } catch (final JSONException e) {
            LOG.debug("JSON processing error", e);
            throw Throwables.propagate(e);
        }
    }

    @Override
    public <T> String serializeEntity(final Type<T> type, final T entity) {
        final Class<? extends T> clazz = type.getEntityClass();
        if (entity instanceof JSONObject) {
            return this.wrap(this.wrap((JSONObject) entity, type.getEntityType()), "entity").toString();
        } else {
            throw new UnsupportedOperationException("Unsupported class for JsonSerializer: " + clazz.getName());
        }
    }

    @Override
    protected IntJsonObj parseJsonObj(final String raw) throws UnparsableJsonException {
        try {
            return new IntJsonObj(new JSONObject(raw));
        } catch (final JSONException e) {
            LOG.debug("JSON parsing error", e);
            throw new UnparsableJsonException(e);
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

    protected JSONObject wrap(final JSONObject json, final String name) {
        return new JSONObject(Collections.singletonMap(name, json));
    }

    private EntityType parseEntityType(final JSONObject json) {
        return this.parseEntityType(json, null);
    }

    private EntityType parseEntityType(final JSONObject json, final EntityType parent) {
        final EntityType type = new EntityType();

        // set the parent
        final Integer parentId = json.has("parent_id") ? json.getInt("parent_id") : null;
        if (parent != null && parentId != null && !parentId.equals(parent.getId())) {
            throw new IllegalArgumentException("Specified parent object does not match the referenced parent object");
        } else if (parentId != null && parent == null) {
            type.setParentId(parentId);
        } else {
            type.setParent(parent);
        }

        if (json.has("id")) {
            type.setId(json.optInt("id"));
        }
        type.setName(json.optString("name", null));
        type.setDescription(json.optString("description", null));
        type.setFieldType(json.optString("field_type", null));

        // parse nested fields
        final JSONArray fields = json.optJSONArray("fields");
        if (fields != null) {
            for (int i = 0; i < fields.length(); i++) {
                type.addField(this.parseEntityType(fields.getJSONObject(i), type));
            }
        }

        // return the parsed entity_type
        return type;
    }

    private void populateResponseListMeta(final ResponseList<?> list, final JSONObject json) {
        // parse the meta-data
        final JSONObject metaJson = json.getJSONObject("meta");
        final ResponseList.Meta meta = list.getMeta();
        meta.setTotal(metaJson.getInt("total"));
        meta.setFrom(metaJson.getInt("from"));
        meta.setTo(metaJson.getInt("to"));
        meta.setPage(metaJson.getInt("page"));
        meta.setTotalPages(metaJson.getInt("total_pages"));
    }

    private static class IntJsonObj extends JsonObj<JSONObject, JSONArray> {
        private IntJsonObj(final JSONObject obj) {
            super(obj);
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
