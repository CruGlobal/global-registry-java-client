package org.ccci.gto.globalreg.resteasy;

import org.ccci.gto.globalreg.BaseGlobalRegistryClient;
import org.ccci.gto.globalreg.UnauthorizedException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 * Created by ryancarlson on 6/30/14.
 */
public class ResteasyGlobalRegistryClient extends BaseGlobalRegistryClient
{

	@Override
	protected Response processRequest(Request request) throws UnauthorizedException
	{

		return null;
	}

	private WebTarget webTarget()
	{
		Client client = ClientBuilder.newBuilder().build();
		return client.target(apiUrl);
	}
}
