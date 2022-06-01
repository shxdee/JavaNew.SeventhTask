package com.shxdee.seventhtask;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.FileSerializer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@JsonSerialize(using = FileSerializer.class)
@JsonDeserialize(using = FlatDeserializer.class)
public class Flat implements Serializable {
    private int number;
    private double square;
    private List<Person> people;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flat flat = (Flat) o;
        return number == flat.number && Double.compare(flat.square, square) == 0 && Objects.equals(people, flat.people);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, square, people);
    }

    public int getNumber() {
        return number;
    }

    public double getSquare() {
        return square;
    }
    public List<Person> getPeople() {
        return people;
    }

    public Flat(int number, double square, List<Person> people) {
        this.number = number;
        this.square = square;
        this.people = new ArrayList<>(people);
    }
}
