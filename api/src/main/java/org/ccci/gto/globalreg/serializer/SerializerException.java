package org.ccci.gto.globalreg.serializer;

import org.ccci.gto.globalreg.GlobalRegistryException;

public class SerializerException extends GlobalRegistryException {
    private static final long serialVersionUID = 1448236625691548638L;

    public SerializerException() {
        super();
    }

    public SerializerException(final Throwable cause) {
        super(cause);
    }
}
