package org.ccci.gto.globalreg;

import com.google.common.base.Ascii;
import com.google.common.base.Preconditions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A {@code GlobalRegistryException} that indicates Global Registry responded with
 * a non-successful http status code.
 * The response status code is not in the 2xx range.
 */
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

    private final int statusCode;
    private final String responseContent;

    public HttpErrorException(int statusCode, String responseContent) {
        super(determineMessage(checkStatus(statusCode), responseContent));
        this.statusCode = statusCode;
        this.responseContent = responseContent;
    }

    private static int checkStatus(final int statusCode) {
        Preconditions.checkArgument(statusCode / 100 != 2);
        return statusCode;
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

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseContent() {
        return responseContent;
    }
}
