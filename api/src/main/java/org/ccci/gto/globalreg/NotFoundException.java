package org.ccci.gto.globalreg;

/**
 * A {@code ClientErrorException} that indicates the requested resource could not be found.
 * The response status code is 404.
 *
 * This can happen, for example, if the resource has been deleted,
 * or if this client's access token does not grant access to the requested resource.
 *
 *
 *
 * @author Matt Drees
 */
public class NotFoundException extends ClientErrorException {

    public NotFoundException(String responseContent) {
        super(404, responseContent);
    }
}
