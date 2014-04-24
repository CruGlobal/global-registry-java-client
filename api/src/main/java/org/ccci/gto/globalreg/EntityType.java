package org.ccci.gto.globalreg;

import com.google.common.base.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public final class EntityType {
    private static final Logger LOG = LoggerFactory.getLogger(EntityType.class);

    public enum FieldType {
        ENTITY("entity"), BOOLEAN("boolean"), INTEGER("integer"), DECIMAL("decimal"), STRING("string"), DATE("date"),
        DATETIME("datetime"), ENUM("enum"), ENUM_VALUES("enum_values"), EMAIL("email"), TEXT("text"), UUID("uuid"),
        UNKNOWN(""), NONE("");
        private final String raw;

        private FieldType(final String raw) {
            this.raw = raw;
        }

        public static FieldType fromString(final String type) {
            //XXX: I would like to use a String switch for this, but need to support Java 1.6
            if ("entity".equals(type)) {
                return ENTITY;
            } else if ("boolean".equals(type)) {
                return BOOLEAN;
            } else if ("integer".equals(type)) {
                return INTEGER;
            } else if ("decimal".equals(type)) {
                return DECIMAL;
            } else if ("string".equals(type)) {
                return STRING;
            } else if ("date".equals(type)) {
                return DATE;
            } else if ("datetime".equals(type)) {
                return DATETIME;
            } else if ("enum".equals(type)) {
                return ENUM;
            } else if ("enum_values".equals(type)) {
                return ENUM_VALUES;
            } else if ("email".equals(type)) {
                return EMAIL;
            } else if ("text".equals(type)) {
                return TEXT;
            } else if ("uuid".equals(type)) {
                return UUID;
            } else {
                LOG.error("unrecognized field_type: {}", type);
            }

            return UNKNOWN;
        }

        @Override
        public String toString() {
            return this.raw;
        }
    }

    private String id;
    private EntityType parent;
    private String parentId;
    private String name;
    private FieldType fieldType = FieldType.NONE;
    private String description;
    private final List<String> enumValues = new ArrayList<String>();

    private final List<EntityType> fields = new ArrayList<EntityType>();
    private final List<RelationshipType> relationships = new ArrayList<RelationshipType>();

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public boolean hasParent() {
        return this.parent != null || this.parentId != null;
    }

    public EntityType getParent() {
        return this.parent;
    }

    public void setParent(final EntityType parent) {
        this.parentId = null;
        this.parent = parent;
    }

    public String getParentId() {
        if (this.parent != null) {
            final String id = this.parent.getId();
            if (id == null) {
                throw new IllegalStateException("Parent EntityType does not have a valid id");
            }
            return id;
        } else {
            return this.parentId;
        }
    }

    public void setParentId(final String parent) {
        this.parent = null;
        this.parentId = parent;
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

    public FieldType getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(final FieldType fieldType) {
        this.fieldType = fieldType != null ? fieldType : FieldType.ENTITY;
    }

    public void setFieldType(final String type) {
        this.fieldType = type != null ? FieldType.fromString(type) : FieldType.ENTITY;
    }

    public List<String> getEnumValues() {
        return Collections.unmodifiableList(this.enumValues);
    }

    public void setEnumValues(final Collection<String> values) {
        this.enumValues.clear();
        if (values != null) {
            this.enumValues.addAll(values);
        }
    }

    public void addEnumValue(final String value) {
        this.enumValues.add(value);
    }

    public EntityType getField(final String name) {
        for (final EntityType field : this.fields) {
            if (Objects.equal(name, field.getName())) {
                return field;
            }
        }

        return null;
    }

    public List<EntityType> getFields() {
        return Collections.unmodifiableList(this.fields);
    }

    public void setFields(final Collection<EntityType> fields) {
        this.fields.clear();
        if (fields != null) {
            this.fields.addAll(fields);
        }
    }

    public void addField(final EntityType field) {
        this.fields.add(field);
    }

    public List<RelationshipType> getRelationshipTypes() {
        return Collections.unmodifiableList(this.relationships);
    }

    public void setRelationshipTypes(final Collection<RelationshipType> relationships) {
        this.relationships.clear();
        if (relationships != null) {
            this.relationships.addAll(relationships);
        }
    }

    public void addRelationshipType(final RelationshipType relationship) {
        this.relationships.add(relationship);
    }

    public static class RelationshipType {
        private final EntityType entityType;

        private String id;
        private String enumEntityTypeId;
        private Relationship local;
        private Relationship target;

        public RelationshipType(final EntityType entityType) {
            this.entityType = entityType;
        }

        public String getId() {
            return this.id;
        }

        public void setId(final String id) {
            this.id = id;
        }

        public String getEnumEntityTypeId() {
            return this.enumEntityTypeId;
        }

        public void setEnumEntityTypeId(final String enumEntityTypeId) {
            this.enumEntityTypeId = enumEntityTypeId;
        }

        public void setRelationships(final Relationship relationship1, final Relationship relationship2) {
            if (relationship1 == null || relationship2 == null) {
                throw new NullPointerException("neither relationship1 nor relationship2 can be null");
            }

            // determine which relationship is local, prefer relationship1 if we can't tell
            if (this.entityType == null || Objects.equal(this.entityType.getName(), relationship1.getEntityType())) {
                this.local = relationship1;
                this.target = relationship2;
            } else {
                this.local = relationship2;
                this.target = relationship1;
            }
        }

        public boolean isReflexive() {
            return this.local != null && this.target != null && Objects.equal(this.local.getEntityType(),
                    this.target.getEntityType());
        }

        public Relationship getLocalRelationship() {
            return this.local;
        }

        public Relationship getTargetRelationship() {
            return this.target;
        }

        public static class Relationship {
            private final String entityType;
            private final String name;

            public Relationship(final String entityType, final String name) {
                this.entityType = entityType;
                this.name = name;
            }

            public String getEntityType() {
                return this.entityType;
            }

            public String getName() {
                return this.name;
            }
        }
    }
}
