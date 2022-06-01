package com.shxdee.seventhtask;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;

public class PersonDeserializer extends StdDeserializer<Person> {
    protected PersonDeserializer(Class<?> vc) {
        super(vc);
    }

    public PersonDeserializer() {this(null);}

    @Override
    public Person deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Person person = deserializePerson(jsonParser, deserializationContext);
        jsonParser.close();
        return person;
    }

    public static Person deserializePerson(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        LocalDate date = LocalDate.now();
        String firstName = null,
                middleName = null ,
                lastName = null;
        while(jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = jsonParser.getCurrentName();
            if ("fullName".equals(fieldName)) {
                jsonParser.nextToken();
                String[] fields = jsonParser.getText().split("\\s+");
                firstName = fields[0];
                middleName = fields[1];
                lastName = fields[2];
            }
            else if ("birthDate".equals(fieldName)) {
                jsonParser.nextToken();
                String[] dt = jsonParser.getText().split("\\.");
                date = LocalDate.of(Integer.parseInt(dt[2]), Integer.parseInt(dt[1]), Integer.parseInt(dt[0]));
            }
        }
        return new Person(firstName, middleName, lastName, date);
    }
}
