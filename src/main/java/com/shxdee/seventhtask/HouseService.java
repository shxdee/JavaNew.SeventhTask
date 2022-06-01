package com.shxdee.seventhtask;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HouseService implements AutoCloseable{
    // 6
    public static void serialize(OutputStream out, House house) throws IOException {
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(house);
        os.close();
    }

    public static House deserialize(InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream is = new ObjectInputStream(in);
        House house = (House) is.readObject();
        return house;
    }
    // 7*
    public static void csv(House house) {
        try(Writer writer = new FileWriter("house_" + house.getNumber() + ".csv")) {
            writer.write("Данные о доме\n");
            writer.write("Кадастровый номер: " + house.getNumber() + "\n");
            writer.write("Адрес: " + house.getAddress() + "\n");
            writer.write("Старший по дому: " + house.getSenior().toString() + "\n");
            writer.write("Данные о квартирах\n");
            writer.write("№; Площадь, кв. м; Жильцы\n");
            for(Flat f : house.getFlats()) {
                writer.write(f.getNumber() + ";" +  f.getSquare() + ";");
                for(Person p : f.getPeople()) {
                    writer.write(p.getFirstName() + " " + p.getMiddleName() + " " + p.getLastName().charAt(0) + ".\r");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 8
    public static void serializationJackson(String file, House house) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new FileOutputStream(file), house);
    }

    public static House deserializationJackson(String file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new FileInputStream(file), House.class);
    }

    // 9*
    public static boolean isEqualsJson(String json1, String json2) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(json1).equals(mapper.readTree(json2));
    }

    @Override
    public void close() throws Exception {

    }
}
