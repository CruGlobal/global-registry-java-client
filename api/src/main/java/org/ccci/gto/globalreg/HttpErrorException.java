package org.ccci.gto.globalreg;

import com.google.common.base.Ascii;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpErrorException extends GlobalRegistryException {

    private static final Pattern JSON_ERROR_PATTERN;
    static {
        String whitespace = "\\s*";
        JSON_ERROR_PATTERN = Pattern.compile(
                "\\{" +
                whitespace +
                "\"error\"" +
                whitespace +
                ":" +
                whitespace +
                "\"(?<errorMessage>.*)\"" +
                whitespace +
                "}"
        );
    }

    final int statusCode;
    final String responseContent;

    public HttpErrorException(int statusCode, String responseContent) {
        super(determineMessage(statusCode, responseContent));
        this.statusCode = statusCode;
        this.responseContent = responseContent;
    }

    private static String determineMessage(final int statusCode, final String responseContent) {
        String error = getJsonErrorMessageIfPossible(responseContent);
        if (error != null) {
            return String.format("status code %s: %s", statusCode, error);
        } else {
            // most likely an html response
            return String.format("status code %s: %n%s", statusCode, Ascii.truncate(responseContent, 200, "…"));
        }
    }

    private static String getJsonErrorMessageIfPossible(final String responseContent) {
        Matcher matcher = JSON_ERROR_PATTERN.matcher(responseContent);
        return matcher.matches() ? matcher.group("errorMessage") : null;
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
