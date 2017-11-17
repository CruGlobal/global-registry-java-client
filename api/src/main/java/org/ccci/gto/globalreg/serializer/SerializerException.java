package org.ccci.gto.globalreg.serializer;

import org.ccci.gto.globalreg.GlobalRegistryException;

/**
 * An {@code GlobalRegistryException} indicating a problem in either the response entity from Global Registry,
 * or the request entity being sent to Global Registry.
 */
public class SerializerException extends GlobalRegistryException {
    private static final long serialVersionUID = 1448236625691548638L;

    public SerializerException() {
        super();
    }

    public SerializerException(final Throwable cause) {
        super(cause);
    }
}
