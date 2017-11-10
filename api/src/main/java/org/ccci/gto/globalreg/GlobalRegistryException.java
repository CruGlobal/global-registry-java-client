package org.ccci.gto.globalreg;

public class GlobalRegistryException extends RuntimeException {
    private static final long serialVersionUID = 1747755230724452819L;

    public GlobalRegistryException() {
        super();
    }

    public GlobalRegistryException(final Throwable cause) {
        super(cause);
    }

    public GlobalRegistryException(final String message) {
        super(message);
    }
}
