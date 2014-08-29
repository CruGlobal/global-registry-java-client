package org.ccci.gto.globalreg;

/**
 * Created by ryancarlson on 8/29/14.
 */
public class ServerErrorException extends GlobalRegistryException {
	final int statusCode;
	final String responseContent;

	public ServerErrorException(int statusCode, String responseContent) {
		this.statusCode = statusCode;
		this.responseContent = responseContent;
	}

	public ServerErrorException(Throwable cause, int statusCode, String responseContent) {
		super(cause);
		this.statusCode = statusCode;
		this.responseContent = responseContent;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getResponseContent() {
		return responseContent;
	}
}
