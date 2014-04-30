package org.ccci.gto.globalreg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MeasurementType {
    private static final Logger LOG = LoggerFactory.getLogger(MeasurementType.class);

    public enum Category {
        FINANCE("Finance"), LMI("LMI"), MPD("MPD"), HR("HR"), UNKNOWN(null), NONE(null);
        private final String raw;

        private Category(final String raw) {
            this.raw = raw;
        }

        public static Category fromString(final String category) {
            if (category != null) {
                switch (category) {
                    case "Finance":
                        return FINANCE;
                    case "LMI":
                        return LMI;
                    case "MPD":
                        return MPD;
                    case "HR":
                        return HR;
                    default:
                        LOG.error("Unrecognized category: {}", category);
                }
            }

            return UNKNOWN;
        }

        @Override
        public String toString() {
            return this.raw;
        }
    }

    public enum Frequency {
        MONTHLY("monthly"), UNKNOWN(null), NONE(null);

        private final String raw;

        public static Frequency fromString(final String frequency) {
            if (frequency != null) {
                switch (frequency) {
                    case "monthly":
                        return MONTHLY;
                    default:
                        LOG.error("Unrecognized frequency: {}", frequency);
                }
            }

            return UNKNOWN;
        }

        private Frequency(final String raw) {
            this.raw = raw;
        }

        @Override
        public String toString() {
            return this.raw;
        }
    }

    private Long id;
    private String name;
    private String description;
    private Category category = Category.NONE;
    private Frequency frequency = Frequency.NONE;
    private String unit;
    private Long relatedEntityType;

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(final Category category) {
        this.category = category != null ? category : Category.NONE;
    }

    public void setCategory(final String category) {
        this.setCategory(category != null ? Category.fromString(category) : null);
    }

    public Frequency getFrequency() {
        return this.frequency;
    }

    public void setFrequency(final Frequency frequency) {
        this.frequency = frequency != null ? frequency : Frequency.NONE;
    }

    public void setFrequency(final String frequency) {
        this.setFrequency(frequency != null ? Frequency.fromString(frequency) : null);
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(final String unit) {
        this.unit = unit;
    }

    public Long getRelatedEntityType() {
        return this.relatedEntityType;
    }

    public void setRelatedEntityType(final Long entityType) {
        this.relatedEntityType = entityType;
    }
}
