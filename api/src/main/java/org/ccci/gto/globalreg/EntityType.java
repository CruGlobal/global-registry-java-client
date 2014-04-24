package org.ccci.gto.globalreg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class EntityType {
    public enum FieldType {
        BOOLEAN("boolean"), INTEGER("integer"), DECIMAL("decimal"), STRING("string"), DATE("date"),
        DATETIME("datetime"), ENUM("enum"), ENUM_VALUES("enum_values"), EMAIL("email"), TEXT("text");
        private final String raw;

        private FieldType(final String raw) {
            this.raw = raw;
        }

        private static FieldType fromRaw(final String raw) {
            if (raw != null) {
                switch (raw) {
                    case "boolean":
                        return BOOLEAN;
                    case "integer":
                        return INTEGER;
                    case "decimal":
                        return DECIMAL;
                    case "string":
                        return STRING;
                    case "date":
                        return DATE;
                    case "datetime":
                        return DATETIME;
                    case "enum":
                        return ENUM;
                    case "enum_values":
                        return ENUM_VALUES;
                    case "email":
                        return EMAIL;
                    case "text":
                        return TEXT;
                }
            }

            throw new IllegalArgumentException("Unrecognized field_type: " + raw);
        }
    }

    private int id;
    private String name;
    private String description;
    private FieldType fieldType;

    private final List<EntityType> fields = new ArrayList<>();

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
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

    public FieldType getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(final FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public void setFieldType(final String type) {
        this.fieldType = type != null ? FieldType.fromRaw(type) : null;
    }

    public EntityType getField(final String name) {
        for (final EntityType field : this.fields) {
            if (Objects.equals(name, field.getName())) {
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
        this.fields.addAll(fields);
    }

    public void addField(final EntityType field) {
        this.fields.add(field);
    }
}
