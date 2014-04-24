package org.ccci.gto.globalreg.serializer.jackson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import org.ccci.gto.globalreg.ResponseList;
import org.ccci.gto.globalreg.Type;
import org.ccci.gto.globalreg.serializer.AbstractSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JacksonSerializer extends AbstractSerializer {
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
            final JsonNode metaJson = root.path("meta");
            final ResponseList.Meta meta = list.getMeta();
            meta.setTotal(metaJson.path("total").asInt(0));
            meta.setFrom(metaJson.path("from").asInt(0));
            meta.setTo(metaJson.path("to").asInt(0));
            meta.setPage(metaJson.path("page").asInt(0));
            meta.setTotalPages(metaJson.path("total_pages").asInt(0));

            // return the parsed list
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

    private JsonNode wrap(final JsonNode json, final String name) {
        return this.mapper.createObjectNode().put(name, json);
    }
}
