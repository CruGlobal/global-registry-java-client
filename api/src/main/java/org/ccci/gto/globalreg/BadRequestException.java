package org.ccci.gto.globalreg;

public class BadRequestException extends ClientErrorException {

    public BadRequestException(String responseContent) {
        super(400, responseContent);
    }
}
