package org.ccci.gto.globalreg.jaxrs20;

import org.ccci.gto.globalreg.BaseGlobalRegistryClient;
import org.ccci.gto.globalreg.UnauthorizedException;

import javax.annotation.Nonnull;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import java.util.Map;

/**
 * This class uses the JAX-RS 2 API to interact with the Global Registry.
 *
 * The caller may choose the jax-rs implementation by using the {@code Client} constructor.
 *
 */
public class Jaxrs20GlobalRegistryClient extends BaseGlobalRegistryClient
{

	private final Client client;

	public Jaxrs20GlobalRegistryClient() {
		this(ClientBuilder.newBuilder().build());
	}

	public Jaxrs20GlobalRegistryClient(Client client) {
		this.client = client;
	}

	@Nonnull
	@Override
	protected Response processRequest(Request request) throws UnauthorizedException
	{
		WebTarget webTarget = webTarget();
		webTarget = addPath(request, webTarget);
		webTarget = addQueryParameters(request, webTarget);

		Invocation.Builder requestBuilder = webTarget
				.request()
				.header("Authorization", "Bearer " + accessToken);

		return buildResponse(execute(requestBuilder, request));
	}

	/**
	 * Create a WebTarget based on the api URL.
	 *
	 * This WebTarget represents a resource target identified by the resource URI.
	 *
	 * @see javax.ws.rs.client.WebTarget
	 *
	 * @return
	 */
	private WebTarget webTarget()
	{
		return client.target(apiUrl);
	}

	private WebTarget addPath(final Request request, WebTarget webTarget)
	{
		for (String pathSegment : request.path) {
			webTarget = webTarget.path(pathSegment);
		}
		return webTarget;
	}

	private WebTarget addQueryParameters(Request request, WebTarget webTarget)
	{
		for(Map.Entry<String, String> entry : request.queryParams.entries())
		{
			webTarget = webTarget.queryParam(entry.getKey(), entry.getValue());
		}
		return webTarget;
	}

	/**
	 * Call the appropriate method on the target resource.  The method passed in the Request object is
	 * mapped to one of "GET", "POST", "PUT", "DELETE", or "OPTIONS".  Any other method results in an
	 * UnsupportedOperationException.
	 *
	 * @param requestBuilder
	 * @param request
	 * @return
	 */
	private javax.ws.rs.core.Response execute(Invocation.Builder requestBuilder, Request request)
	{
		if("GET".equalsIgnoreCase(request.method))
		{
			return requestBuilder.get();
		}
		if("POST".equalsIgnoreCase(request.method))
		{
			return requestBuilder.post(Entity.json(request.content));
		}
		if("PUT".equalsIgnoreCase(request.method))
		{
			return requestBuilder.put(Entity.json(request.content));
		}
		if("DELETE".equalsIgnoreCase(request.method))
		{
			return requestBuilder.delete();
		}
		if("OPTIONS".equalsIgnoreCase(request.method))
		{
			return requestBuilder.options();
		}

		throw new UnsupportedOperationException();
	}

	/**
	 * Takes the status code and returned entity from the jax-rs response and converts it into a
	 * Global Registry client response.
	 */
	@Nonnull
	private Response buildResponse(javax.ws.rs.core.Response response) throws UnauthorizedException {
        return new Response(response.getStatus(), response.readEntity(String.class));
    }
}
