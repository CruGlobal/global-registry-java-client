package org.ccci.gto.globalreg;

/**
 * A {@code ClientErrorException} that indicates the request was invalid.
 * The response status code is 400.
 *
 * This can happen, for example, if invalid fields are sent in a new entity.
 *
 * @author Matt Drees
 */
public class BadRequestException extends ClientErrorException {

    public BadRequestException(String responseContent) {
        super(400, responseContent);
    }
}
