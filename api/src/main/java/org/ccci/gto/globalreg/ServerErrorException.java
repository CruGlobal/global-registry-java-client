package org.ccci.gto.globalreg;

/**
 * Created by ryancarlson on 8/29/14.
 */
public class ServerErrorException extends HttpErrorException {

	public ServerErrorException(int statusCode, String responseContent) {
		super(statusCode, responseContent);
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getResponseContent() {
		return responseContent;
	}
}
