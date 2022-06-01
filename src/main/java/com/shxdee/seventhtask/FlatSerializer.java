package com.shxdee.seventhtask;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class FlatSerializer extends StdSerializer<Flat> {

    protected FlatSerializer(Class<Flat> t) {
        super(t);
    }

    public FlatSerializer() {this(null);}

    @Override
    public void serialize(Flat flat, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        serializeFlat(flat, jsonGenerator, serializerProvider);
        jsonGenerator.close();
    }

    public static void serializeFlat(Flat flat, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("number", flat.getNumber());
        jsonGenerator.writeNumberField("square", flat.getSquare());
        jsonGenerator.writeFieldName("persons");
        jsonGenerator.writeStartArray();
        for (Person person: flat.getPeople()) {
            PersonSerializer.serializePerson(person, jsonGenerator, serializerProvider);
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
