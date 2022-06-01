package com.shxdee.seventhtask;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HouseDeserializer extends StdDeserializer<House> {
    protected HouseDeserializer(Class<?> vc) {
        super(vc);
    }

    public HouseDeserializer() {this(null);}

    @Override
    public House deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String number = "";
        String address = "";
        Person person = null;
        List<Flat> flats = new ArrayList<>();
        while(jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = jsonParser.getCurrentName();
            if ("number".equals(fieldName)) {
                jsonParser.nextToken();
                number = jsonParser.getText();
            }
            else if ("address".equals(fieldName)) {
                jsonParser.nextToken();
                address = jsonParser.getText();
            }
            else if ("senior".equals(fieldName)) {
                jsonParser.nextToken();
                person = PersonDeserializer.deserializePerson(jsonParser, deserializationContext);
            }
            else if ("flats".equals(fieldName)) {
                jsonParser.nextToken();
                while(jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    flats.add(FlatDeserializer.deserializeFlat(jsonParser, deserializationContext));
                }
            }
        }
        jsonParser.close();
        return new House(number, address, person, flats);
    }
}
