package org.ccci.gto.globalreg.serializer.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Throwables;
import org.ccci.gto.globalreg.EntityType;
import org.ccci.gto.globalreg.ResponseList;
import org.ccci.gto.globalreg.Type;
import org.ccci.gto.globalreg.serializer.base.JsonIntermediateSerializer;
import org.ccci.gto.globalreg.serializer.base.UnparsableJsonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JacksonSerializer extends JsonIntermediateSerializer<JsonNode, JsonNode> {
    private static final Logger LOG = LoggerFactory.getLogger(JacksonSerializer.class);

    private final ObjectMapper mapper;

    public JacksonSerializer() {
        this.mapper = new ObjectMapper();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public JacksonSerializer(final ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public <T> T deserializeEntity(final Type<T> type, final String raw) {
        try {
            final JsonNode json = this.mapper.readTree(raw);
            return this.mapper.treeToValue(json.path("entity").path(type.getEntityType()), type.getEntityClass());
        } catch (final IOException e) {
            LOG.error("Unexpected IOException", e);
            throw Throwables.propagate(e);
        }
    }

    @Override
    public <T> ResponseList<T> deserializeEntities(final Type<T> type, final String raw) {
        try {
            final JsonNode root = this.mapper.readTree(raw);
            final ResponseList<T> list = new ResponseList<>();

            // parse all returned entities
            final JsonNode entities = root.path("entities");
            if(entities.isArray()) {
                for(final JsonNode entity : entities) {
                    list.add(this.mapper.treeToValue(entity.path(type.getEntityType()), type.getEntityClass()));
                }
            }

            // parse the meta-data
            populateResponseListMeta(list, root);

            // return the parsed list
            return list;
        } catch (final IOException e) {
            LOG.error("Unexpected IOException", e);
            throw Throwables.propagate(e);
        }
    }

    @Override
    public String serializeEntityType(final EntityType type) {
        final ObjectNode json = this.mapper.createObjectNode();
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
            final JsonNode root = this.mapper.readTree(raw);
            return this.parseEntityType(root.path("entity_type"));
        } catch (final IOException e) {
            LOG.error("Unexpected IOException", e);
            throw Throwables.propagate(e);
        }
    }

    @Override
    public ResponseList<EntityType> deserializeEntityTypes(final String raw) {
        try {
            final JsonNode root = this.mapper.readTree(raw);
            final ResponseList<EntityType> list = new ResponseList<>();

            // parse all returned entity types
            final JsonNode types = root.path("entity_types");
            if (types.isArray()) {
                for (final JsonNode type : types) {
                    list.add(this.parseEntityType(type));
                }
            }

            // parse the meta-data
            populateResponseListMeta(list, root);

            return list;
        } catch (final IOException e) {
            LOG.error("Unexpected IOException", e);
            throw Throwables.propagate(e);
        }
    }

    @Override
    public <T> String serializeEntity(final Type<T> type, final T entity) {
        return this.wrap(this.wrap(this.mapper.valueToTree(entity), type.getEntityType()), "entity").toString();
    }

    @Override
    protected IntJsonObj parseJsonObj(final String raw) throws UnparsableJsonException {
        try {
            return new IntJsonObj(this.mapper.readTree(raw));
        } catch (final JsonProcessingException e) {
            LOG.debug("JSON parsing error", e);
            throw new UnparsableJsonException(e);
        } catch (final IOException e) {
            LOG.debug("Unexpected IOException", e);
            throw Throwables.propagate(e);
        }
    }

    protected JsonNode wrap(final JsonNode json, final String name) {
        final ObjectNode wrapper = this.mapper.createObjectNode();
        wrapper.put(name, json);
        return wrapper;
    }

    private EntityType parseEntityType(final JsonNode json) {
        return this.parseEntityType(json, null);
    }

    private EntityType parseEntityType(final JsonNode json, final EntityType parent) {
        final EntityType type = new EntityType();

        // set the parent
        final JsonNode parentId = json.get("parent_id");
        if (parent != null && parentId != null && parentId.asInt() != parent.getId()) {
            throw new IllegalArgumentException("Specified parent object does not match the referenced parent object");
        } else if (parentId != null && parent == null) {
            type.setParentId(parentId.asInt());
        } else {
            type.setParent(parent);
        }

        final JsonNode id = json.get("id");
        type.setId(id != null ? id.asInt() : null);
        final JsonNode name = json.get("name");
        type.setName(name != null ? name.asText() : null);
        final JsonNode description = json.get("description");
        type.setDescription(description != null ? description.asText() : null);
        final JsonNode fieldType = json.get("field_type");
        type.setFieldType(fieldType != null ? fieldType.asText() : null);

        // parse nested fields
        final JsonNode fields = json.path("fields");
        if (fields.isArray()) {
            for (final JsonNode field : fields) {
                type.addField(this.parseEntityType(field, type));
            }
        }

        // return the parsed entity_type
        return type;
    }

    private void populateResponseListMeta(final ResponseList<?> list, final JsonNode json) {
        // parse the meta-data
        final JsonNode metaJson = json.path("meta");
        final ResponseList.Meta meta = list.getMeta();
        meta.setTotal(metaJson.path("total").asInt(0));
        meta.setFrom(metaJson.path("from").asInt(0));
        meta.setTo(metaJson.path("to").asInt(0));
        meta.setPage(metaJson.path("page").asInt(0));
        meta.setTotalPages(metaJson.path("total_pages").asInt(0));
    }

    private static final class IntJsonObj extends JsonObj<JsonNode, JsonNode> {
        protected IntJsonObj(final JsonNode obj) {
            super(obj);
        }

        @Override
        protected IntJsonObj getObject(final String key) {
            return new IntJsonObj(obj.path(key));
        }

        @Override
        protected JsonArr<JsonNode, JsonNode> getArray(final String key) {
            return new IntJsonArr(obj.path(key));
        }

        @Override
        protected Integer getInt(final String key, final Integer def) {
            final JsonNode val = obj.get(key);

            // simplify processing for a couple simple cases
            if (val == null) {
                return def;
            } else if (def != null) {
                return val.asInt(def);
            }

            // otherwise test 2 defaults to see if we have a valid int value
            final int val1 = val.asInt(1);
            final int val2 = val.asInt(2);
            return val1 == val2 ? val1 : def;
        }

        @Override
        protected Long getLong(final String key, final Long def) {
            final JsonNode val = obj.get(key);

            // simplify processing for a couple simple cases
            if (val == null) {
                return def;
            } else if (def != null) {
                return val.asLong(def);
            }

            // otherwise test 2 defaults to see if we have a valid long value
            final long val1 = val.asLong(1);
            final long val2 = val.asLong(2);
            return val1 == val2 ? val1 : def;
        }

        @Override
        protected Boolean getBoolean(final String key, final Boolean def) {
            final JsonNode val = obj.get(key);

            // simplify processing for a couple simple cases
            if (val == null) {
                return def;
            } else if (def != null) {
                return val.asBoolean(def);
            }

            // otherwise test 2 defaults to see if we have a valid boolean value
            final boolean val1 = val.asBoolean(true);
            final boolean val2 = val.asBoolean(false);
            return val1 == val2 ? val1 : def;
        }

        @Override
        protected String getString(final String key, final String def) {
            final JsonNode val = obj.get(key);
            return val != null ? val.asText() : def;
        }
    }

    private static final class IntJsonArr extends JsonArr<JsonNode, JsonNode> {
        protected IntJsonArr(final JsonNode arr) {
            super(arr);
        }

        @Override
        protected int size() {
            return arr.size();
        }

        @Override
        protected IntJsonObj getObject(final int index) {
            return new IntJsonObj(arr.path(index));
        }
    }
}
