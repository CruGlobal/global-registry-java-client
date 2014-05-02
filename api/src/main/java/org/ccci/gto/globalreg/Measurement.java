package org.ccci.gto.globalreg;

import org.joda.time.Interval;

public class Measurement {
    private MeasurementType type;
    private Long typeId;
    private Long id;
    private Interval period;
    private Double value;
    private Long relatedEntityId;

    public MeasurementType getType() {
        return this.type;
    }

    public void setType(final MeasurementType type) {
        this.typeId = null;
        this.type = type;
    }

    public Long getTypeId() {
        if (this.typeId != null) {
            return this.typeId;
        } else if (this.type != null) {
            return this.type.getId();
        } else {
            return null;
        }
    }

    public void setTypeId(final Long type) {
        this.type = null;
        this.typeId = type;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Interval getPeriod() {
        return this.period;
    }

    public void setPeriod(final Interval period) {
        this.period = period;
    }

    public Double getValue() {
        return this.value;
    }

    public void setValue(final Double value) {
        this.value = value;
    }

    public Long getRelatedEntityId() {
        return this.relatedEntityId;
    }

    public void setRelatedEntityId(final Long relatedEntityId) {
        this.relatedEntityId = relatedEntityId;
    }
}
