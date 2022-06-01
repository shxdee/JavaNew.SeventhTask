package com.shxdee.seventhtask;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class HouseSerializer extends StdSerializer<House>{
    protected HouseSerializer(Class<House> t) {
        super(t);
    }

    public HouseSerializer() {this(null);}

    @Override
    public void serialize(House house, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("number", house.getNumber());
        jsonGenerator.writeStringField("address", house.getAddress());
        jsonGenerator.writeFieldName("senior");
        PersonSerializer.serializePerson(house.getSenior(), jsonGenerator, serializerProvider);
        jsonGenerator.writeFieldName("flats");
        jsonGenerator.writeStartArray();
        for (Flat flat: house.getFlats()) {
            FlatSerializer.serializeFlat(flat, jsonGenerator, serializerProvider);
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.close();
    }
}
