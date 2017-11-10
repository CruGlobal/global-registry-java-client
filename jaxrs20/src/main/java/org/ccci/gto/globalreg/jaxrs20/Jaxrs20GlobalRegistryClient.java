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
 * The caller may choose the jax-rs implementation by using the {@code Client} or {@code ClientBuilder} constructors.
 *
 */
public class Jaxrs20GlobalRegistryClient extends BaseGlobalRegistryClient
{

	private final Client client;
	private final boolean responsibleForClosingClient;

	/**
	 * Constructs a GR client using {@link ClientBuilder#newBuilder()}.
	 */
	public Jaxrs20GlobalRegistryClient() {
		this(ClientBuilder.newBuilder());
	}

	/**
	 * Constructs a GR client that uses the given jax-rs client builder.
	 * The jax-rs client will be closed when {@link #close()} is invoked.
	 */
	public Jaxrs20GlobalRegistryClient(final ClientBuilder clientBuilder) {
		this(clientBuilder.build(), true);
	}

	/**
	 * Constructs a GR client that uses the given jax-rs client.
	 * The given client will not be closed when {@link #close()} is invoked;
	 * it is the caller's responsibility.
	 */
	public Jaxrs20GlobalRegistryClient(final Client client) {
		this(client, false);
	}

	private Jaxrs20GlobalRegistryClient(final Client client, final boolean responsibleForClosingClient) {
		this.client = client;
		this.responsibleForClosingClient = false;
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

	@Override
	public void close()  {
		if (responsibleForClosingClient) {
			client.close();
		}
	}
}
