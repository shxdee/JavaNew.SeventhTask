package com.shxdee.seventhtask;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDate;

public class PersonSerializer extends StdSerializer<Person> {
    protected PersonSerializer(Class<Person> t) {
        super(t);
    }

    public PersonSerializer() {this(null);}

    @Override
    public void serialize(Person person, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        serializePerson(person, jsonGenerator, serializerProvider);
        jsonGenerator.close();
    }

    public static void serializePerson(Person person, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("fullName", person.getFirstName() + ' ' +
                person.getMiddleName() + ' ' + person.getLastName());
        LocalDate date = person.getBirthDay();
        jsonGenerator.writeStringField("birthDate", date.getDayOfMonth() + "." + date.getMonthValue() + '.' +
                date.getYear());
        jsonGenerator.writeEndObject();
    }
}
