package org.ccci.gto.globalreg;

import org.joda.time.Interval;

public class Measurement {
    private MeasurementType type;
    private String typeId;
    private String id;
    private Interval period;
    private Double value;
    private String relatedEntityId;

    public MeasurementType getType() {
        return this.type;
    }

    public void setType(final MeasurementType type) {
        this.typeId = null;
        this.type = type;
    }

    public String getTypeId() {
        if (this.typeId != null) {
            return this.typeId;
        } else if (this.type != null) {
            return this.type.getId();
        } else {
            return null;
        }
    }

    public void setTypeId(final String type) {
        this.type = null;
        this.typeId = type;
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
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

    public String getRelatedEntityId() {
        return this.relatedEntityId;
    }

    public void setRelatedEntityId(final String relatedEntityId) {
        this.relatedEntityId = relatedEntityId;
    }
}
