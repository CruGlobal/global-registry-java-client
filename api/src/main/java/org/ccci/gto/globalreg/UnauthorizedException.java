package org.ccci.gto.globalreg;

public class UnauthorizedException extends ClientErrorException {
    private static final long serialVersionUID = -226761129706293401L;

    public UnauthorizedException(String responseContent) {
        super(401, responseContent);
    }
}
