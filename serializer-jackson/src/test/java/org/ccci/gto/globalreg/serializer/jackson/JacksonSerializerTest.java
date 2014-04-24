package org.ccci.gto.globalreg.serializer.jackson;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.ccci.gto.globalreg.ResponseList;
import org.ccci.gto.globalreg.Type;
import org.ccci.gto.globalreg.serializer.AbstractSerializerTest;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;

public class JacksonSerializerTest extends AbstractSerializerTest {
    private static final Collection<Class<? extends Person>> CLASSES = new HashSet<>();

    static {
        CLASSES.add(PersonField.class);
        CLASSES.add(PersonMethod.class);
    }

    public JacksonSerializerTest() {
        super(new JacksonSerializer());
    }

    @Test
    public void testDeserializeEntity() throws Exception {
        for (final Class<? extends Person> clazz : CLASSES) {
            final Person person = this.testDeserializeEntity(new Type<>(clazz, "person"));
            assertEquals(5, (int) person.getId());
            assertEquals("John", person.getFirstName());
            assertEquals("Doe", person.getLastName());
            assertEquals("Ohio University", person.getCampus());
        }
    }

    @Test
    public void testDeserializeEntities() throws Exception {
        for (final Class<? extends Person> clazz : CLASSES) {
            final ResponseList<Person> entities = this.testDeserializeEntities(new Type<>(clazz, "person"));

            // all records should have a last_name of Vellacott
            for (final Person entity : entities) {
                assertEquals("Vellacott", entity.getLastName());
            }
        }
    }

    private abstract static class Person {
        protected abstract Integer getId();

        protected abstract void setId(final Integer id);

        protected abstract String getFirstName();

        protected abstract void setFirstName(final String name);

        protected abstract String getLastName();

        protected abstract void setLastName(final String name);

        protected abstract String getCampus();

        protected abstract void setCampus(final String campus);
    }

    public static class PersonField extends Person {
        public Integer id;
        @JsonProperty("first_name")
        public String firstName;
        @JsonProperty("last_name")
        public String lastName;
        public String campus;

        protected Integer getId() {
            return this.id;
        }

        protected void setId(final Integer id) {
            this.id = id;
        }

        protected String getFirstName() {
            return firstName;
        }

        protected void setFirstName(final String name) {
            this.firstName = name;
        }

        protected String getLastName() {
            return this.lastName;
        }

        protected void setLastName(final String name) {
            this.lastName = name;
        }

        protected String getCampus() {
            return campus;
        }

        protected void setCampus(final String campus) {
            this.campus = campus;
        }
    }

    public static class PersonMethod extends Person {
        private Integer id;
        private String firstName;
        private String lastName;
        private String campus;

        public Integer getId() {
            return this.id;
        }

        public void setId(final Integer id) {
            this.id = id;
        }

        @JsonProperty("first_name")
        public String getFirstName() {
            return this.firstName;
        }

        public void setFirstName(final String name) {
            this.firstName = name;
        }

        public String getLastName() {
            return this.lastName;
        }

        @JsonProperty("last_name")
        public void setLastName(final String name) {
            this.lastName = name;
        }

        public String getCampus() {
            return this.campus;
        }

        public void setCampus(final String campus) {
            this.campus = campus;
        }
    }
}
