package com.shxdee.seventhtask;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@JsonSerialize(using = HouseSerializer.class)
@JsonDeserialize(using = HouseDeserializer.class)
public class House implements Serializable {
    private String number;
    private String address;
    private Person senior;
    private List<Flat> flats;

    public String getNumber() {
        return number;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        House house = (House) o;
        return Objects.equals(number, house.number) && Objects.equals(address, house.address) && Objects.equals(senior, house.senior) && Objects.equals(flats, house.flats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, address, senior, flats);
    }

    public Person getSenior() {
        return senior;
    }

    public List<Flat> getFlats() {
        return flats;
    }

    public House(String number, String address, Person senior, List<Flat> flats) {
        this.number = number;
        this.address = address;
        this.senior = senior;
        this.flats = flats;
    }
}
