package org.ccci.gto.globalreg;

/**
 * A {@code ClientErrorException} that indicates the request was unauthorized.
 * The response status code is 401.
 *
 * This can happen, for example, if this client's access token is invalid,
 * or if the request is made from an ip address that has not been authorized.
 *
 * @author Matt Drees
 */
public class UnauthorizedException extends ClientErrorException {
    private static final long serialVersionUID = -226761129706293401L;

    public UnauthorizedException(String responseContent) {
        super(401, responseContent);
    }
}
