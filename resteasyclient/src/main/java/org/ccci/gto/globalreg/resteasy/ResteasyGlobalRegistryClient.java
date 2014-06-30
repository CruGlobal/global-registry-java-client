package org.ccci.gto.globalreg.resteasy;

import org.ccci.gto.globalreg.BaseGlobalRegistryClient;
import org.ccci.gto.globalreg.UnauthorizedException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

/**
 * Created by ryancarlson on 6/30/14.
 */
public class ResteasyGlobalRegistryClient extends BaseGlobalRegistryClient
{

	@Override
	protected Response processRequest(Request request) throws UnauthorizedException
	{

		Invocation.Builder requestBuilder = webTarget()
				.path(buildPath(request))
				.queryParam("access_token", accessToken)
				.request();

		javax.ws.rs.core.Response resteasyResponse = execute(requestBuilder, request);

		return buildResponse(resteasyResponse);
	}

	private Response buildResponse(javax.ws.rs.core.Response resteasyResponse)
	{
			return new Response(resteasyResponse.getStatus(), resteasyResponse.readEntity(String.class));
	}

	private WebTarget webTarget()
	{
		Client client = ClientBuilder.newBuilder().build();
		return client.target(apiUrl);
	}

	private String buildPath(Request request)
	{
		StringBuilder pathBuilder = new StringBuilder();

		for(String pathElement : request.path)
		{
			pathBuilder.append(pathElement);

		}

		return pathBuilder.toString();
	}

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
}
