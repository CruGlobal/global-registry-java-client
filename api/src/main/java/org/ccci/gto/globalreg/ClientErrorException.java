package org.ccci.gto.globalreg;

import com.google.common.base.Ascii;

/**
 * A {@code HttpErrorException} that indicates a client-side global registry error.
 * The response status code is in the 4xx range.
 *
 * @author Ryan Carlson
 * @author Matt Drees
 */
public class ClientErrorException extends HttpErrorException {

    public ClientErrorException(int statusCode, String responseContent) {
        super(statusCode, responseContent);
    }

}
