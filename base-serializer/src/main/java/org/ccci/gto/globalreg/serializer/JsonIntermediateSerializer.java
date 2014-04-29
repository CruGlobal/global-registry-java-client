package org.ccci.gto.globalreg.serializer;

public abstract class JsonIntermediateSerializer<J> extends AbstractSerializer {
    protected abstract J wrap(final J json, final String name);

    protected abstract J path(final J json, final String name);
}
