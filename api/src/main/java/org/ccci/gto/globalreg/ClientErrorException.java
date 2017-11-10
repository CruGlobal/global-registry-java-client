package org.ccci.gto.globalreg;

import com.google.common.base.Ascii;

/**
 * Created by ryancarlson on 8/29/14.
 */
public class ClientErrorException extends HttpErrorException {

	public ClientErrorException(int statusCode, String responseContent) {
		super(statusCode, responseContent);
	}

}
