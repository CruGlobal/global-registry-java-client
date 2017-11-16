package org.ccci.gto.globalreg;

import org.ccci.gto.globalreg.util.ArrayUtil;
import org.joda.time.ReadableInstant;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public abstract class AbstractGlobalRegistryClient implements GlobalRegistryClient {
    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_PER_PAGE_ENTITIES = 1000;

    @Override
    public final <T> T getEntity(final Type<T> type, final String id, final String ownedBy,
                                 final Filter... filters) throws GlobalRegistryException {
        return this.getEntity(type, id, ArrayUtil.merge(filters, Filter.OWNED_BY.values(ownedBy)));
    }

    @Override
    public final <T> T getEntity(final Type<T> type, final String id, final String ownedBy, final Set<String> fields,
                                 final Filter... filters) throws GlobalRegistryException {
        return this.getEntity(type, id, fields, ArrayUtil.merge(filters, Filter.OWNED_BY.values(ownedBy)));
    }

    @Override
    public final <T> T getEntity(final Type<T> type, final String id, final Filter... filters) throws GlobalRegistryException {
        return this.getEntity(type, id, (Set<String>) null, filters);
    }


    @Override
    public final <T> ResponseList<T> getEntities(final Type<T> type, final String ownedBy,
                                                 final Filter... filters) throws GlobalRegistryException {
        return this.getEntities(type, ownedBy, DEFAULT_PAGE, filters);
    }

    @Override
    public final <T> ResponseList<T> getEntities(final Type<T> type, final String ownedBy, final Set<String> fields,
                                                 final Filter... filters) throws GlobalRegistryException {
        return this.getEntities(type, DEFAULT_PAGE, DEFAULT_PER_PAGE_ENTITIES, fields, ArrayUtil
                .merge(filters, Filter.OWNED_BY.values(ownedBy)));
    }

    @Override
    public final <T> ResponseList<T> getEntities(final Type<T> type, final String ownedBy, final int page,
                                                 final Filter... filters) throws GlobalRegistryException {
        return this.getEntities(type, ownedBy, page, DEFAULT_PER_PAGE_ENTITIES, filters);
    }

    @Override
    public final <T> ResponseList<T> getEntities(final Type<T> type, final String ownedBy, final int page,
                                                 final int perPage, final Filter... filters) throws
            GlobalRegistryException {
        return this.getEntities(type, page, perPage, ArrayUtil.merge(filters, Filter.OWNED_BY.values(ownedBy)));
    }

    @Override
    public final <T> ResponseList<T> getEntities(final Type<T> type, final Filter... filters) throws
            GlobalRegistryException {
        return this.getEntities(type, DEFAULT_PAGE, filters);
    }

    @Override
    public final <T> ResponseList<T> getEntities(final Type<T> type, final int page,
                                                 final Filter... filters) throws GlobalRegistryException {
        return this.getEntities(type, page, DEFAULT_PER_PAGE_ENTITIES, filters);
    }

    @Override
    public final <T> ResponseList<T> getEntities(final Type<T> type, final int page, final int perPage,
                                                 final Filter... filters) throws GlobalRegistryException {
        return this.getEntities(type, page, DEFAULT_PER_PAGE_ENTITIES, null, filters);
    }

    @Override
    @Deprecated
    public final <T> void deleteEntity(final Type<T> type, @Nonnull final String id) throws GlobalRegistryException {
        this.deleteEntity(id);
    }

    @Override
    public final <T> T addEntity(@Nonnull final Type<T> type, @Nonnull final T entity) throws GlobalRegistryException {
        return this.addEntity(type, entity, null);
    }

    @Override
    public final <T> T addEntity(@Nonnull final Type<T> type, @Nonnull final T entity, final Set<String> fields)
            throws GlobalRegistryException {
        return this.addEntity(type, entity, fields, false);
    }

    @Override
    public final <T> T updateEntity(@Nonnull final Type<T> type, @Nonnull final String id, @Nonnull final T entity)
            throws GlobalRegistryException {
        return this.updateEntity(type, id, entity, null);
    }

    @Override
    public final <T> T updateEntity(@Nonnull final Type<T> type, @Nonnull final String id, @Nonnull final T entity,
                                    final Set<String> fields) throws GlobalRegistryException {
        return this.updateEntity(type, id, entity, fields, false);
    }

    @Override
    public final ResponseList<EntityType> getEntityTypes(final Filter... filters) throws GlobalRegistryException {
        return this.getEntityTypes(1, filters);
    }

    @Override
    public final ResponseList<MeasurementType> getMeasurementTypes(final Filter... filters) throws
            GlobalRegistryException {
        return this.getMeasurementTypes(1, filters);
    }

    @Override
    public List<Measurement> getMeasurements(final MeasurementType type, final ReadableInstant from,
                                             final ReadableInstant to, final Filter... filters) throws
            GlobalRegistryException {
        return this.getMeasurements(type.getId(), from, to, filters);
    }

    @Override
    public List<Measurement> getMeasurements(final String type, final ReadableInstant from, final ReadableInstant to,
                                             final Filter... filters) throws GlobalRegistryException {
        final ArrayList<Filter> tmp = new ArrayList<>(Arrays.asList(filters));
        if (from != null) {
            tmp.add(Filter.PERIOD_FROM.values(MeasurementType.Frequency.MONTHLY.getFormatter().print(from)));
        }
        if (to != null) {
            tmp.add(Filter.PERIOD_TO.values(MeasurementType.Frequency.MONTHLY.getFormatter().print(to)));
        }

        return this.getMeasurements(type, tmp.toArray(new Filter[tmp.size()]));
    }

    @Override
    public final List<Measurement> getMeasurements(final MeasurementType type, final Filter... filters) throws GlobalRegistryException {
        return this.getMeasurements(type.getId(), filters);
    }

    @Override
    public final List<Measurement> getMeasurements(final String type, final Filter... filters) throws GlobalRegistryException {
        return this.getMeasurementType(type, filters).getMeasurements();
    }
}
