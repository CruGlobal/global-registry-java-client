package org.ccci.gto.globalreg;

import com.google.common.base.Ascii;

public class HttpErrorException extends GlobalRegistryException {
    final int statusCode;
    final String responseContent;

    public HttpErrorException(int statusCode, String responseContent) {
        super(String.format("status code %s: %s", statusCode, Ascii.truncate(responseContent, 200, "...")));
        this.statusCode = statusCode;
        this.responseContent = responseContent;
    }

    public HttpErrorException(Throwable cause, int statusCode, String responseContent) {
        super(cause);
        this.statusCode = statusCode;
        this.responseContent = responseContent;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseContent() {
        return responseContent;
    }
}
