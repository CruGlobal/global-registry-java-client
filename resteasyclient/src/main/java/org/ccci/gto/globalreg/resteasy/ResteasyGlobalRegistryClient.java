package org.ccci.gto.globalreg.resteasy;

import org.ccci.gto.globalreg.BaseGlobalRegistryClient;
import org.ccci.gto.globalreg.UnauthorizedException;

import javax.annotation.Nonnull;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import java.util.Collection;

/**
 * This class uses JAXRS implementation RestEasy to interact with the Global Registry.
 *
 * For more information on RestEasy, @see <a href="http://resteasy.jboss.org/">http://resteasy.jboss.org/</a>
 *
 * Created by ryancarlson on 6/30/14.
 */
public class ResteasyGlobalRegistryClient extends BaseGlobalRegistryClient
{

	@Nonnull
	@Override
	protected Response processRequest(Request request) throws UnauthorizedException
	{
		WebTarget webTarget = webTarget()
				.path(buildPath(request));

		webTarget = addQueryParameters(request, webTarget);

		Invocation.Builder requestBuilder = webTarget
				.request()
				.header("Authorization", "Bearer " + accessToken);

		javax.ws.rs.core.Response resteasyResponse = execute(requestBuilder, request);

		return buildResponse(resteasyResponse);
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
		Client client = ClientBuilder.newBuilder().build();
		return client.target(apiUrl);
	}

	/**
	 * Build a String representation of the resource path based on the path elements provided in the Request object.
	 *
	 * @param request
	 * @return
	 */
	private String buildPath(Request request)
	{
		StringBuilder pathBuilder = new StringBuilder();

		boolean first = true;
		for(String pathElement : request.path)
		{
			if(!first) pathBuilder.append("/");
			pathBuilder.append(pathElement);
			first = false;
		}

		return pathBuilder.toString();
	}

	/**
	 * Add query parameters on to the WebTarget based on the query parameters provided in the Request object.
	 *
	 * Note that WebTarget is immutable, so in order to preserve data a new copy of the object must be returned,
	 * pass by reference is not suitable here.
	 *
	 * @param request
	 * @param webTarget
	 * @return
	 */
	private WebTarget addQueryParameters(Request request, WebTarget webTarget)
	{
		// add query parameters
		for(String paramName : request.queryParams.keySet())
		{
			Collection<String> values = request.queryParams.get(paramName);

			webTarget = webTarget.queryParam(paramName, values.toArray(new String[values.size()]));
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
	 * Take the status code and returned entity from the RestEasy response and convert it into a
	 * Global Registry client response.  The response entity is converted into a String for
	 * convenience.
	 *
	 * @param resteasyResponse
	 * @return
	 */
	@Nonnull
	private Response buildResponse(javax.ws.rs.core.Response resteasyResponse) throws UnauthorizedException {
        return new Response(resteasyResponse.getStatus(), resteasyResponse.readEntity(String.class));
    }
}
