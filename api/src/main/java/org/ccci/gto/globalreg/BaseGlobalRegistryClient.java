package org.ccci.gto.globalreg;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;
import org.ccci.gto.globalreg.serializer.Serializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseGlobalRegistryClient extends AbstractGlobalRegistryClient {
    private static final MediaType APPLICATION_JSON = MediaType.create("application", "json");

    protected String apiUrl;
    protected String accessToken;
    protected Serializer serializer;

    public void setApiUrl(final String apiUrl) {
        this.apiUrl = apiUrl != null && !apiUrl.endsWith("/") ? apiUrl + "/" : apiUrl;
    }

    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    public void setSerializer(final Serializer serializer) {
        this.serializer = serializer;
    }

    private void attachFilters(final Request request, final Filter... filters) {
        for (final Filter filter : filters) {
            if (filter != null && filter.isValid()) {
                // generate the name for this filter
                final StringBuilder name = new StringBuilder(PARAM_FILTER);
                for (final String field : filter.getPath()) {
                    name.append("[").append(field).append("]");
                }
                if (filter.getValues().length > 1) {
                    name.append("[]");
                }

                // append all values for this filter
                request.queryParams.putAll(name.toString(), Lists.newArrayList(filter.getValues()));
            }
        }
    }

    protected abstract Response processRequest(Request request) throws UnauthorizedException;

    @Override
    public <T> T getEntity(final Type<T> type, final String id, final Filter... filters) throws GlobalRegistryException
	{
        // build the request
        final Request request = new Request();
        request.path = new String[]{PATH_ENTITIES, id};
        this.attachFilters(request, filters);

        // process request
        final Response response = this.processRequest(request);

        checkResponseForError(response);

        return this.serializer.deserializeEntity(type, response.content);
    }

    @Override
    public <T> ResponseList<T> getEntities(final Type<T> type, final int page, final int perPage,
                                           final Filter... filters) throws GlobalRegistryException
    {
        // build request
        final Request request = new Request();
        request.path = new String[]{PATH_ENTITIES};
        request.queryParams.put(PARAM_ENTITY_TYPE, type.getEntityType());
        request.queryParams.put(PARAM_PAGE, Integer.toString(page));
        request.queryParams.put(PARAM_PER_PAGE, Integer.toString(perPage));
        this.attachFilters(request, filters);

        // execute request
        final Response response = this.processRequest(request);

        checkResponseForError(response);

        return this.serializer.deserializeEntities(type, response.content);
    }

    @Override
    public <T> T addEntity(final Type<T> type, final T entity) throws GlobalRegistryException
    {
        // build request
        final Request request = new Request();
        request.method = "POST";
        request.path = new String[]{PATH_ENTITIES};
        request.contentType = APPLICATION_JSON.toString();
        request.content = this.serializer.serializeEntity(type, entity);

        // execute request
        final Response response = this.processRequest(request);

        checkResponseForError(response);

        return this.serializer.deserializeEntity(type, response.content);
    }

    @Override
    public <T> T updateEntity(final Type<T> type, final String id, final T entity) throws GlobalRegistryException
    {
        // build the request
        final Request request = new Request();
        request.method = "PUT";
        request.path = new String[]{PATH_ENTITIES, id};
        request.contentType = APPLICATION_JSON.toString();
        request.content = this.serializer.serializeEntity(type, entity);

        // execute request
        final Response response = this.processRequest(request);

        checkResponseForError(response);

        return this.serializer.deserializeEntity(type, response.content);
    }

    @Override
    public void deleteEntity(final String id) throws GlobalRegistryException
    {
        // build the request
        final Request request = new Request();
        request.method = "DELETE";
        request.path = new String[]{PATH_ENTITIES, id};

        // execute request
        Response response = this.processRequest(request);

        checkResponseForError(response);
    }

    @Override
    public ResponseList<EntityType> getEntityTypes(final int page, final Filter... filters) throws GlobalRegistryException
    {
        // build request
        final Request request = new Request();
        request.path = new String[]{PATH_ENTITY_TYPES};
        request.queryParams.put(PARAM_PAGE, Integer.toString(page));
        this.attachFilters(request, filters);

        // execute request
        final Response response = this.processRequest(request);

        checkResponseForError(response);

        return this.serializer.deserializeEntityTypes(response.content);
    }

    // XXX: this is currently untested
    @Override
    public final EntityType addEntityType(final EntityType type) throws GlobalRegistryException
    {
        // build request
        final Request request = new Request();
        request.method = "POST";
        request.path = new String[]{PATH_ENTITY_TYPES};
        request.contentType = APPLICATION_JSON.toString();
        request.content = this.serializer.serializeEntityType(type);

        // execute request
        final Response response = this.processRequest(request);

        checkResponseForError(response);

        return this.serializer.deserializeEntityType(response.content);
    }

    @Override
    public RegisteredSystem getSystem(final String id) throws GlobalRegistryException
    {
        // build request
        final Request request = new Request();
        request.path = new String[]{PATH_SYSTEMS, id};

        // execute request
        final Response response = this.processRequest(request);

        checkResponseForError(response);

        // process response
        return this.serializer.deserializeSystem(response.content);
    }

    @Override
    public List<RegisteredSystem> getSystems() throws GlobalRegistryException
    {
        // build request
        final Request request = new Request();
        request.path = new String[]{PATH_SYSTEMS};

        // execute request
        final Response response = this.processRequest(request);

        checkResponseForError(response);

        return this.serializer.deserializeSystems(response.content);
    }

    @Override
    public ResponseList<MeasurementType> getMeasurementTypes(final int page, final Filter... filters) throws GlobalRegistryException
    {
        // build request
        final Request request = new Request();
        request.path = new String[]{PATH_MEASUREMENT_TYPES};
        request.queryParams.put(PARAM_PAGE, Integer.toString(page));
        this.attachFilters(request, filters);

        // execute request
        final Response response = this.processRequest(request);

        checkResponseForError(response);

        return this.serializer.deserializeMeasurementTypes(response.content);
    }

    @Override
    public MeasurementType getMeasurementType(final String id, final Filter... filters) throws GlobalRegistryException
    {
        // build request
        final Request request = new Request();
        request.path = new String[]{PATH_MEASUREMENT_TYPES, id};
        this.attachFilters(request, filters);

        // execute request
        final Response response = this.processRequest(request);

        checkResponseForError(response);

        // process response
        return this.serializer.deserializeMeasurementType(response.content);
    }

    private void checkResponseForError(Response response) throws ClientErrorException, ServerErrorException
    {
        if (response.code / 100 == 4) {
            throw new ClientErrorException(response.code, response.content);
        } else if (response.code / 100 == 5) {
            throw new ServerErrorException(response.code, response.content);
        }
    }

    public final static class Request {
        public String method = "GET";
        public String[] path = new String[0];
        public final Map<String, String> headers = new HashMap<>();
        public final Multimap<String, String> queryParams = HashMultimap.create();
        public String contentType = null;
        public String content = null;

        Request() {
            this.headers.put(HttpHeaders.ACCEPT, APPLICATION_JSON.toString());
        }
    }

    public final static class Response {
        public final int code;
        public final String content;

        public Response(final int code, final String content) {
            this.code = code;
            this.content = content;
        }
    }
}
