package org.ccci.gto.globalreg;

import org.ccci.gto.globalreg.serializer.SerializerException;
import org.ccci.gto.globalreg.util.ArrayUtil;
import org.joda.time.ReadableInstant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractGlobalRegistryClient implements GlobalRegistryClient {
    public static final int DEFAULT_PAGE = 1;

    @Override
    public final <T> T getEntity(final Type<T> type, final String id, final String ownedBy) throws
            SerializerException, UnauthorizedException {
        return this.getEntity(type, id, Filter.OWNED_BY.value(ownedBy));
    }

    @Override
    public final <T> ResponseList<T> getEntities(final Type<T> type, final String ownedBy,
                                                 final Filter... filters) throws UnauthorizedException,
            SerializerException {
        return this.getEntities(type, ownedBy, DEFAULT_PAGE, filters);
    }

    @Override
    public final <T> ResponseList<T> getEntities(final Type<T> type, final String ownedBy, final int page,
                                                 final Filter... filters) throws UnauthorizedException,
            SerializerException {
        return this.getEntities(type, page, ArrayUtil.merge(filters, Filter.OWNED_BY.value(ownedBy)));
    }

    @Override
    public final <T> ResponseList<T> getEntities(final Type<T> type, final Filter... filters) throws
            UnauthorizedException, SerializerException {
        return this.getEntities(type, DEFAULT_PAGE, filters);
    }

    @Override
    public final ResponseList<EntityType> getEntityTypes(final Filter... filters) throws UnauthorizedException,
            SerializerException {
        return this.getEntityTypes(1, filters);
    }

    @Override
    public final ResponseList<MeasurementType> getMeasurementTypes(final Filter... filters) throws
            UnauthorizedException, SerializerException {
        return this.getMeasurementTypes(1, filters);
    }

    @Override
    public List<Measurement> getMeasurements(final MeasurementType type, final ReadableInstant from,
                                             final ReadableInstant to, final Filter... filters) throws
            UnauthorizedException, SerializerException {
        return this.getMeasurements(type.getId(), from, to, filters);
    }

    @Override
    public List<Measurement> getMeasurements(final String type, final ReadableInstant from, final ReadableInstant to,
                                             final Filter... filters) throws UnauthorizedException, SerializerException {
        final ArrayList<Filter> tmp = new ArrayList<>(Arrays.asList(filters));
        if (from != null) {
            tmp.add(Filter.PERIOD_FROM.value(MeasurementType.Frequency.MONTHLY.getFormatter().print(from)));
        }
        if (to != null) {
            tmp.add(Filter.PERIOD_TO.value(MeasurementType.Frequency.MONTHLY.getFormatter().print(to)));
        }

        return this.getMeasurements(type, tmp.toArray(new Filter[tmp.size()]));
    }

    @Override
    public final List<Measurement> getMeasurements(final MeasurementType type, final Filter... filters) throws
            UnauthorizedException, SerializerException {
        return this.getMeasurements(type.getId(), filters);
    }

    @Override
    public final List<Measurement> getMeasurements(final String type, final Filter... filters) throws
            UnauthorizedException, SerializerException {
        return this.getMeasurementType(type, filters).getMeasurements();
    }
}
