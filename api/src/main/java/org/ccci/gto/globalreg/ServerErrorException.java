package org.ccci.gto.globalreg;

import com.google.common.base.Preconditions;

/**
 * A {@code HttpErrorException} that indicates a server-side global registry error.
 * The response status code is in the 5xx range.
 *
 * @author Ryan Carlson
 * @author Matt Drees
 */
public class ServerErrorException extends HttpErrorException {

    public ServerErrorException(int statusCode, String responseContent) {
        super(checkStatus(statusCode), responseContent);
    }

    private static int checkStatus(final int statusCode) {
        Preconditions.checkArgument(statusCode / 100 == 5);
        return statusCode;
    }

}
