package org.ccci.gto.globalreg.serializer;

public class UnparsableJsonException extends SerializerException {
    private static final long serialVersionUID = -3474782748768543895L;

    public UnparsableJsonException() {
        super();
    }

    public UnparsableJsonException(final Throwable cause) {
        super(cause);
    }
}
