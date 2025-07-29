package org.ccci.gto.globalreg;

/**
 * A {@code HttpErrorException} that indicates a client-side global registry error.
 * The response status code is in the 4xx range.
 *
 * @author Ryan Carlson
 * @author Matt Drees
 */
public class ClientErrorException extends HttpErrorException {

    public ClientErrorException(int statusCode, String responseContent) {
        super(checkStatus(statusCode), responseContent);
    }

    private static int checkStatus(final int statusCode) {
        if (statusCode / 100 != 4) {
            throw new IllegalArgumentException("Status code must be in 4xx range, got: " + statusCode);
        }
        return statusCode;
    }

}
