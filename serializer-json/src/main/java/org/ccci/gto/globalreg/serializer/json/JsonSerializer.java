package org.ccci.gto.globalreg.serializer.json;

import com.google.common.base.Throwables;
import org.ccci.gto.globalreg.ResponseList;
import org.ccci.gto.globalreg.Type;
import org.ccci.gto.globalreg.serializer.AbstractSerializer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;

public class JsonSerializer extends AbstractSerializer {
    @Override
    public <T> T parseEntity(final Type<T> type, final String raw) {
        final Class<? extends T> clazz = type.getEntityClass();
        try {
            if (JSONObject.class.equals(clazz)) {
                final JSONObject json = new JSONObject(raw);
                return clazz.cast(json.getJSONObject("entity").getJSONObject(type.getEntityType()));
            } else {
                throw new UnsupportedOperationException("Unsupported class for JsonSerializer: " + clazz.getName());
            }
        } catch (final JSONException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public <T> ResponseList<T> parseEntitiesList(final Type<T> type, final String raw) {
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
                final JSONObject metaJson = json.getJSONObject("meta");
                final ResponseList.Meta meta = list.getMeta();
                meta.setTotal(metaJson.getInt("total"));
                meta.setFrom(metaJson.getInt("from"));
                meta.setTo(metaJson.getInt("to"));
                meta.setPage(metaJson.getInt("page"));
                meta.setTotalPages(metaJson.getInt("total_pages"));

                // return the entities list
                return list;
            } catch (final JSONException e) {
                throw Throwables.propagate(e);
            }
        } else {
            throw new UnsupportedOperationException("Unsupported class for JsonSerializer: " + clazz.getName());
        }
    }

    @Override
    public <T> String fromObject(final Type<T> type, final T object) {
        final Class<? extends T> clazz = type.getEntityClass();
        if (object instanceof JSONObject) {
            return this.wrap(this.wrap((JSONObject) object, type.getEntityType()), "entity").toString();
        } else {
            throw new UnsupportedOperationException("Unsupported class for JsonSerializer: " + clazz.getName());
        }
    }

    private JSONObject wrap(final JSONObject json, final String name) {
        return new JSONObject(Collections.singletonMap(name, json));
    }
}
