package org.ccci.gto.globalreg;

public class NotFoundException extends ClientErrorException {

	public NotFoundException(String responseContent) {
		super(404, responseContent);
	}
}
