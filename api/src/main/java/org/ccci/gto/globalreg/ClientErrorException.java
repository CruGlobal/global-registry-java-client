package org.ccci.gto.globalreg;

import com.google.common.base.Ascii;
import com.google.common.base.Preconditions;

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
        Preconditions.checkArgument(statusCode / 100 == 4);
        return statusCode;
    }

}
