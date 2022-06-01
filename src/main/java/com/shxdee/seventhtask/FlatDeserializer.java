package com.shxdee.seventhtask;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlatDeserializer extends StdDeserializer<Flat> {

    protected FlatDeserializer(Class<?> vc) {
        super(vc);
    }

    public FlatDeserializer() {this(null);}

    @Override
    public Flat deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Flat flat =  deserializeFlat(jsonParser, deserializationContext);
        jsonParser.close();
        return flat;
    }

    public static Flat deserializeFlat(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        int number = 0;
        double square = 0;
        List<Person> personList = new ArrayList<>();
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = jsonParser.getCurrentName();
            if ("number".equals(fieldName)) {
                jsonParser.nextToken();
                number = jsonParser.getIntValue();
            }
            else if ("square".equals(fieldName)) {
                jsonParser.nextToken();
                square = jsonParser.getDoubleValue();
            }
            else if ("persons".equals(fieldName)) {
                jsonParser.nextToken();
                while(jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    personList.add(PersonDeserializer.deserializePerson(jsonParser, deserializationContext));
                }
            }
        }
        return new Flat(number, square, personList);
    }
}
