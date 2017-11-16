package org.ccci.gto.globalreg;

/**
 * A {@code HttpErrorException} that indicates a server-side global registry error.
 * The response status code is in the 5xx range.
 *
 * @author Ryan Carlson
 * @author Matt Drees
 */
public class ServerErrorException extends HttpErrorException {

    public ServerErrorException(int statusCode, String responseContent) {
        super(statusCode, responseContent);
    }
}
