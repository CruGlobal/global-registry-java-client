package org.ccci.gto.globalreg.serializer.base;

import org.ccci.gto.globalreg.serializer.SerializerException;

public class UnparsableJsonException extends SerializerException {
    public UnparsableJsonException() {
        super();
    }

    public UnparsableJsonException(final Throwable cause) {
        super(cause);
    }
}
