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
		WebTarget webTarget = client.target(apiUrl);
		webTarget = addPath(request, webTarget);
		webTarget = addQueryParameters(request, webTarget);

		Invocation.Builder invocation = webTarget.request()
				.header("Authorization", "Bearer " + accessToken);

		javax.ws.rs.core.Response response = request.content == null ?
				invocation.method(request.method) :
				invocation.method(request.method, Entity.json(request.content));

		return buildResponse(response);
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
	 * Takes the status code and returned entity from the jax-rs response and converts it into a
	 * Global Registry client response.
	 */
	@Nonnull
	private Response buildResponse(javax.ws.rs.core.Response response) throws UnauthorizedException {
        return new Response(response.getStatus(), response.readEntity(String.class));
    }
}
