package org.ccci.gto.globalreg.serializer;

/**
 * A {@code SerializerException} that indicates a String could not be deserialized as valid json.
 */
public class UnparsableJsonException extends SerializerException {
    private static final long serialVersionUID = -3474782748768543895L;

    public UnparsableJsonException() {
        super();
    }

    public UnparsableJsonException(final Throwable cause) {
        super(cause);
    }
}
