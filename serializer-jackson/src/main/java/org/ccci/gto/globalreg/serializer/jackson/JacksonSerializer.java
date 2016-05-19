package org.ccci.gto.globalreg.serializer.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Throwables;
import org.ccci.gto.globalreg.Type;
import org.ccci.gto.globalreg.serializer.JsonIntermediateSerializer;
import org.ccci.gto.globalreg.serializer.SerializerException;
import org.ccci.gto.globalreg.serializer.UnparsableJsonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
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

    @Nonnull
    @Override
    protected JsonObj<JsonNode, JsonNode> emptyJsonObj() {
        return new IntJsonObj(this.mapper.createObjectNode());
    }

    @Nonnull
    @Override
    protected IntJsonObj stringToJsonObj(final String raw) throws UnparsableJsonException {
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

    @Nonnull
    @Override
    protected String jsonObjToString(final JsonObj<JsonNode, JsonNode> json) {
		try
		{
			return mapper.writeValueAsString(json.getRawObject());
		}
		catch(IOException exception)
		{
			LOG.error("Error writing JsonNode to String", exception);
			throw Throwables.propagate(exception);
		}
    }

    @Override
    protected <T> IntJsonObj entityToJsonObj(final Type<T> type, final T entity) {
        return new IntJsonObj(this.mapper.valueToTree(entity));
    }

    @Override
    protected <T> T jsonObjToEntity(final Type<T> type, final JsonObj<JsonNode,
            JsonNode> json) throws SerializerException {
        try {
            return this.mapper.treeToValue(json.getRawObject(), type.getEntityClass());
        } catch (final JsonProcessingException e) {
            throw new SerializerException(e);
        }
    }

    private final class IntJsonObj extends JsonObj<JsonNode, JsonNode> {
        protected IntJsonObj(final JsonNode obj) {
            super(obj);
        }

        @Nonnull
        @Override
        protected IntJsonObj wrap(final String key) {
            final ObjectNode wrapper = mapper.createObjectNode();
            wrapper.put(key, obj);
            return new IntJsonObj(wrapper);
        }

        @Nonnull
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
            return val1 == val2 ? val1 : null;
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
            return val1 == val2 ? val1 : null;
        }

        @Override
        protected Double getDouble(final String key, final Double def) {
            final JsonNode val = obj.get(key);

            // simplify processing for a couple simple cases
            if (val == null) {
                return def;
            } else if (def != null) {
                return val.asDouble(def);
            }

            // otherwise test 2 defaults to see if we have a valid long value
            final double val1 = val.asDouble(1);
            final double val2 = val.asDouble(2);
            final double diff = val1 - val2;
            return (diff < 0.1 && diff > -0.1) ? val1 : null;
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
            return val1 == val2 ? val1 : null;
        }

        @Override
        protected String getString(final String key, final String def) {
            final JsonNode val = obj.get(key);
            return val != null ? val.asText() : def;
        }

        @Override
        protected IntJsonObj put(final String key, final Integer val) {
            if (obj instanceof ObjectNode) {
                ((ObjectNode) obj).put(key, val);
            } else {
                //TODO: throw an exception
            }

            return this;
        }

        @Override
        protected IntJsonObj put(final String key, final Long val) {
            if (obj instanceof ObjectNode) {
                ((ObjectNode) obj).put(key, val);
            } else {
                //TODO: throw an exception
            }

            return this;
        }

        @Override
        protected IntJsonObj put(final String key, final String val) {
            if (obj instanceof ObjectNode) {
                ((ObjectNode) obj).put(key, val);
            } else {
                //TODO: throw an exception
            }

            return this;
        }
    }

    private final class IntJsonArr extends JsonArr<JsonNode, JsonNode> {
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

        @Override
        protected String getString(final int index, final String def) {
            final JsonNode val = arr.get(index);
            return val != null ? val.asText() : def;
        }
    }
}
